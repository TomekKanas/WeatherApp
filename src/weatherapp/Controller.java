package weatherapp;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.util.Duration;
import weatherapp.data.AirConditionData;
import weatherapp.data.DataClass;
import weatherapp.data.DataProvider;
import weatherapp.data.WeatherData;
import weatherapp.network.SourceType;

import java.time.format.DateTimeFormatter;

public class Controller {

    private static final int POLL_INTERVAL = 60;

    @FXML
    private Label PressureLabel;
    @FXML
    private Label CloudnessLabel;
    @FXML
    private Label TemperatureLabel;
    @FXML
    private Label WindDirectionLabel;
    @FXML
    private Label WindForceLabel;
    @FXML
    private Label DustLevel25Label;
    @FXML
    private Label DustLevel10Label;
    @FXML
    private Label HumidityLabel;
    @FXML
    private Label LastActualizationLabel;
    @FXML
    private Button ActualizeButton;
    @FXML
    private ToggleButton ChangeSourceButton;

    private final DataProvider dataProvider = new DataProvider();

    /**
     * Formatuje liczbę rzeczywistą do wyświetlenia.
     * @param f liczba
     * @return tekst do wyświetlenia
     */
    private static String formatData(Float f)
    {
        if(f == null) return "-";
        else return String.format("%.2f", f);
    }

    /**
     * Formatuje kierunek wiatru.
     * @param f Kierunek wiatru (w stopniach).
     * @return tekst
     */
    private static String formatWindDirection(Float f)
    {
        if(f == null) return "-";
        String dir;
        if(f < 22.5 || f > 337.5) dir = "N";
        else if(f < 67.5) dir = "NE";
        else if(f < 112.5) dir = "E";
        else if(f < 157.5) dir = "SE";
        else if(f < 202.5) dir = "S";
        else if(f < 247.5) dir = "SW";
        else if(f < 292.5) dir = "W";
        else dir = "NW";
        return dir + "(" + f + "°)";
    }

    /**
     * Aktualizuje dane pogodowe w GUI.
     * @param data dane pogodowe
     */
    private void updateWeather(WeatherData data)
    {
        PressureLabel.setText(formatData(data.getPressure()) + "B");
        CloudnessLabel.setText(formatData(data.getCloudness()) + "%");
        TemperatureLabel.setText(formatData(data.getTemperature()) + "°C");
        WindDirectionLabel.setText(formatWindDirection(data.getWinddirection()));
        WindForceLabel.setText(formatData(data.getWindforce()) + "m/s");
        HumidityLabel.setText(formatData(data.getHumidity()) + "%");
    }

    /**
     * Aktualizuje dane o jakości powietrza w GUI.
     * @param data dane o jakości powietrza.
     */
    private void updateAirCondition(AirConditionData data)
    {
        DustLevel10Label.setText(formatData(data.getPM10()) + "µg");
        DustLevel25Label.setText(formatData(data.getPM25()) + "µg");
    }

    /**
     * Aktualizuje wyświetlany czas ostatniej aktualizacji.
     * @param data aktualny pakiet danych
     */
    private void updateLastActualization(DataClass data)
    {
        LastActualizationLabel.setText(String.format("%.8s", data.getTimestamp().format(DateTimeFormatter.ISO_LOCAL_TIME)));
    }

    /**
     * Aktualizuje dane w GUI.
     * @param weatherdata dane pogodowe
     * @param airdata dane o jakości powietrza
     */
    private void updateData(WeatherData weatherdata, AirConditionData airdata)
    {
        updateLastActualization(weatherdata);
        updateWeather(weatherdata);
        updateAirCondition(airdata);
    }

    /**
     * Pokazuje ostrzeżenie o wystąpieniu błędu.
     * @param title tytuł
     * @param header nagłówek
     * @param content zawartość
     */
    private void ShowErrorAlert(String title, String header, String content)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();
    }

    /**
     * Odświerza dane aplikacji (i aktualizuje je w GUI / wyświetla informacje o wystąpieniu błędu)
     */
    private void refreshData()
    {
        dataProvider.requestData();
        if(!dataProvider.isError()) updateData(dataProvider.getWeatherData(), dataProvider.getAirConditionData());
        else
        {
            ShowErrorAlert("Błąd", "Wystąpił błąd. Sprawdź czy masz połączenie z internetem", dataProvider.getErrorLog().getCause().toString());
        }
    }

    @FXML
    private void initialize()
    {
        ActualizeButton.setOnAction(ignore -> refreshData());
        ChangeSourceButton.setOnAction(ignore ->
        {
            dataProvider.switchWeatherDataSource();
            if(dataProvider.getWeatherDataSourceType() == SourceType.METEO) ChangeSourceButton.setText("meteo.waw.pl");
            else ChangeSourceButton.setText("openweathermap.org");
            refreshData();
        });

        Timeline refreshTimeline = new Timeline(new KeyFrame(Duration.seconds(POLL_INTERVAL),
                ignore -> refreshData()));
        refreshTimeline.setCycleCount(Timeline.INDEFINITE);
        refreshTimeline.play();

        refreshData();
    }
}
