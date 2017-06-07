package weatherapp.events;

public class AirConditionEvent extends DataEvent{
    public float pm25, pm10;

    public AirConditionEvent(float pm25, float pm10)
    {
        this.pm25 = pm25;
        this.pm10 = pm10;
    }

    public float getPM25() {
        return pm25;
    }

    public float getPM10() {
        return pm10;
    }

    @Override
    public String toString()
    {
        return "AirConditionEvent(PM2.5=" + pm25 + ", PM10=" + pm10 + ")";
    }
}
