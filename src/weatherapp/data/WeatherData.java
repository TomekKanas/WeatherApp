package weatherapp.data;


/**
 * Standardowa klasa na dane.
 * Przechowuje dane pogodowe.
 */
public class WeatherData extends DataClass {

    private final Float temperature, pressure, windforce, cloudness, winddirection, humidity;

    public WeatherData(Float temperature, Float pressure, Float windforce, Float cloudness, Float winddirection, Float humidity)
    {
        super();
        this.temperature = temperature;
        this.pressure = pressure;
        this.windforce = windforce;
        this.cloudness = cloudness;
        this.winddirection = winddirection;
        this.humidity = humidity;
    }

    public Float getTemperature() {
        return temperature;
    }

    public Float getPressure() {
        return pressure;
    }

    public Float getWinddirection() {
        return winddirection;
    }

    public Float getWindforce() {
        return windforce;
    }

    public Float getCloudness(){
        return cloudness;
    }

    public Float getHumidity()
    {
        return humidity;
    }

    @Override
    public String toString()
    {
        return "WeatherData(temperature=" + temperature + ", pressure=" + pressure + ", cloudness=" + cloudness + ", wind direction=" + winddirection + ", wind force=" + windforce + ", humidity=" + humidity + ")";
    }
}
