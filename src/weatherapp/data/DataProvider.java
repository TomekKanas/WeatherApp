package weatherapp.data;

import weatherapp.network.AirConditionDataSource;
import weatherapp.network.SourceType;
import weatherapp.network.WeatherDataSource;

/**
 * Klasa odpowiadająca za komunikację między GUI, i resztą aplikacji
 */
public class DataProvider {
    private final AirConditionDataSource airConditionDataSource = new AirConditionDataSource();
    private final WeatherDataSource weatherDataSource = new WeatherDataSource();
    private AirConditionData airConditionData = null;
    private WeatherData weatherData = null;
    private ErrorLog errorLog = null;

    /**
     * Prosi o pobranie danych (zwracanie danych jest gdzie indziej).
     */
    public void requestData()
    {
        try
        {
            airConditionData = airConditionDataSource.makeRequest();
            weatherData = weatherDataSource.makeRequest();
            errorLog = null;
        }
        catch(Throwable t)
        {
            airConditionData = null;
            weatherData = null;
            errorLog = new ErrorLog(t);
        }
    }

    /**
     * Zmienia źródło z którego pobierane są dane pogodowe.
     */
    public void switchWeatherDataSource()
    {
        weatherDataSource.switchSource();
    }

    /**
     * Sprawdza czy przy ostatnej akcji wystąpił błąd (został podniesiony wyjątek).
     * @return czy wystąpił błąd
     */
    public Boolean isError()
    {
        return errorLog != null;
    }

    /** geter */
    public ErrorLog getErrorLog()
    {
        return errorLog;
    }

    /** geter */
    public WeatherData getWeatherData()
    {
        return weatherData;
    }

    /** geter */
    public AirConditionData getAirConditionData()
    {
        return airConditionData;
    }

    /** geter */
    public SourceType getWeatherDataSourceType()
    {
        return weatherDataSource.getSourceType();
    }
}
