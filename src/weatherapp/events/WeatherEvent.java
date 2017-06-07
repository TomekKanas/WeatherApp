package weatherapp.events;

public class WeatherEvent extends DataEvent{

    private float temperature, pressure, windforce, cloudness, winddirection;

    public WeatherEvent(float temperature, float pressure, float windforce, float cloudness, float winddirection)
    {
        this.temperature = temperature;
        this.pressure = pressure;
        this.windforce = windforce;
        this.cloudness = cloudness;
        this.winddirection = winddirection;
    }

    public float getTemperature() {
        return temperature;
    }

    public float getPressure() {
        return pressure;
    }

    public float getWinddirection() {
        return winddirection;
    }

    public float getWindforce() {
        return windforce;
    }

    public float getCloudness(){
        return cloudness;
    }

    @Override
    public String toString()
    {
        return "WeatherEvent(temperature=" + temperature + ", pressure=" + pressure + ", cloudness=" + cloudness + ", wind direction=" + winddirection + ", wind force=" + windforce + ")";
    }
}
