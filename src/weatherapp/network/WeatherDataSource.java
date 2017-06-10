package weatherapp.network;

import com.google.gson.JsonObject;
import weatherapp.data.WeatherData;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Klasa odpowiedzialna za pobieranie danych pogodowych.
 */
public class WeatherDataSource extends DataSource{

    private static final String JSON_URL = "http://api.openweathermap.org/data/2.5/weather?q=Warsaw,pl&appid=a204b2531a8bbe00f25565fbab11f4ef";
    private static final String MAIN_JSON_KEY = "main";
    private static final String TEMP_JSON_KEY = "temp";
    private static final String PRESSURE_JSON_KEY = "pressure";
    private static final String HUMIDITY_JSON_KEY = "humidity";
    private static final String WIND_JSON_KEY = "wind";
    private static final String WIND_SPEED_JSON_KEY = "speed";
    private static final String WIND_DEG_JSON_KEY = "deg";
    private static final String CLOUDS_JSON_KEY = "clouds";
    private static final String CLOUDS_ALL_JSON_KEY = "all";
    private SourceType source = SourceType.OPENWEATHERMAP;

    @Override
    public WeatherData makeRequest() throws IOException
    {
        if(source == SourceType.OPENWEATHERMAP) return makeOpenweathermapRequest();
        else return makeMeteoRequest();
    }

    /**
     * Zmienia źródło z którego podawane są dane (openweathermap lub meteo.waw).
     */
    public void switchSource()
    {
        if(source == SourceType.OPENWEATHERMAP) source = SourceType.METEO;
        else source = SourceType.OPENWEATHERMAP;
    }

    /**
     * Ustawia źródło z którego mają być pobierane dane.
     * Jeśli podany zostanie null zmienia źródło.
     * @param source źródło
     */
    public void switchSource(SourceType source)
    {
        if(source == null) switchSource();
        else this.source = source;
    }

    /**
     * Pobiera dane pogodowe z openweathermap
     * Pobrane dane są w formacie JSON i wyglądają podobnie do:
     * {"coord":{"lon":21.01,"lat":52.23},"weather":[{"id":800,"main":"Clear","description":"clear sky","icon":"01d"}],"base":"stations","main":{"temp":294.15,"pressure":1019,"humidity":43,"temp_min":294.15,"temp_max":294.15},"visibility":10000,"wind":{"speed":4.1,"deg":280},"clouds":{"all":0},"dt":1496923200,"sys":{"type":1,"id":5374,"message":0.0027,"country":"PL","sunrise":1496888152,"sunset":1496948091},"id":756135,"name":"Warsaw","cod":200}
     * i zostają przetworzone na:
     * WeatherData(temperature=294.15, pressure=1019, cloudness=0, wind direction=280, wind force=4.1, humidity=43)
     * @return dane pogodowe
     */
    private WeatherData makeOpenweathermapRequest() throws IOException
    {
        JsonObject jsonObject = JsonHelper.asJsonObject(downloadPage(JSON_URL));
        Float temp = JsonHelper.innerFloat(JsonHelper.innerJsonObject(jsonObject, MAIN_JSON_KEY), TEMP_JSON_KEY);
        if(temp != null) temp -= 273;       /// Konwersja na stopnie Celcjusza (z Kelwinów)
        Float pressure = JsonHelper.innerFloat(JsonHelper.innerJsonObject(jsonObject, MAIN_JSON_KEY), PRESSURE_JSON_KEY);
        Float humidity = JsonHelper.innerFloat(JsonHelper.innerJsonObject(jsonObject, MAIN_JSON_KEY), HUMIDITY_JSON_KEY);
        Float cloudness = JsonHelper.innerFloat(JsonHelper.innerJsonObject(jsonObject, CLOUDS_JSON_KEY), CLOUDS_ALL_JSON_KEY);
        Float winddir = JsonHelper.innerFloat(JsonHelper.innerJsonObject(jsonObject, WIND_JSON_KEY), WIND_DEG_JSON_KEY);
        Float windforce = JsonHelper.innerFloat(JsonHelper.innerJsonObject(jsonObject, WIND_JSON_KEY), WIND_SPEED_JSON_KEY);
        return new WeatherData(temp, pressure, windforce, cloudness, winddir, humidity);
    }

    private static final String PATTERN_PREF = "<span class=\"msr_short_text\">";
    private static final String PATTERN_SUF = "[^<]*<[^>]*>\\s*(\\d*),(\\d*)";

    private static final String HTML_URL = "http://www.meteo.waw.pl/";
    private static final String TEMP_HTML_KEY = "temperatura";
    private static final String PRESSURE_HTML_KEY = "ciśnienie";
    private static final String WIND_SPEED_HTML_KEY = "wiatr";
    private static final String HUMIDITY_HTML_KEY = "wilgotność";
    private static final Pattern WIND_DEG_HTML_PATTERN = Pattern.compile("<div class=\"plotbox_title\">kierunek[^<]*<[^>]*>\\s*(\\d*),(\\d*)",
            Pattern.CASE_INSENSITIVE);

    /**
     * Parsuje parametr z kodu html
     * @param param nazwa parametru
     * @return parametr
     */
    private static Float parseParam(String source, String param)
    {
        Matcher m = Pattern.compile(PATTERN_PREF + param + PATTERN_SUF, Pattern.CASE_INSENSITIVE).matcher(source);
        if(m.find()) return Float.parseFloat(m.group(1).trim() + "." + m.group(2).trim());
        return null;
    }

    /**
     * Pobiera dane pogodowe z meteo.waw.pl
     * Dane są pobierane jako kod HTML i parsowane przy użyciu wyrażeń regularnych.
     * @return dane pogodowe
     */
    private WeatherData makeMeteoRequest() throws IOException
    {
        String htmlSource = downloadPage(HTML_URL);
        Matcher m = WIND_DEG_HTML_PATTERN.matcher(htmlSource);
        Float winddir = null;
        if(m.find()) winddir = Float.parseFloat(m.group(1).trim() + "." + m.group(2).trim());
        return new WeatherData(parseParam(htmlSource, TEMP_HTML_KEY),
                parseParam(htmlSource, PRESSURE_HTML_KEY),
                parseParam(htmlSource, WIND_SPEED_HTML_KEY),
                null,
                winddir,
                parseParam(htmlSource, HUMIDITY_HTML_KEY));
    }

    /** geter */
    public SourceType getSourceType()
    {
        return source;
    }
}