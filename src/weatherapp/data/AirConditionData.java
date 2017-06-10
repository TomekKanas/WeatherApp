package weatherapp.data;

/**
 * Standardowa klasa na dane.
 * Przechowuje dane jakości powietrza.
 */
public class AirConditionData extends DataClass {
    private final Float pm25, pm10;

    public AirConditionData(Float pm25, Float pm10)
    {
        super();
        this.pm25 = pm25;
        this.pm10 = pm10;
    }

    public Float getPM25() {
        return pm25;
    }

    public Float getPM10() {
        return pm10;
    }

    @Override
    public String toString()
    {
        return "AirConditionData(PM2.5=" + pm25 + ", PM10=" + pm10 + ")";
    }
}
