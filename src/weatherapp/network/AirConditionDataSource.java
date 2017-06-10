package weatherapp.network;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import weatherapp.data.AirConditionData;

import java.io.IOException;

/**
 * Klasa odpowiedzialna za pobieranie danych o jakości powietrza.
 */
public class AirConditionDataSource extends DataSource{

    private static final String URL = "http://powietrze.gios.gov.pl/pjp/current/getAQIDetailsList?param=AQI";
    private static final String VALUES_JSON_KEY = "values";
    private static final String PM10_JSON_KEY = "PM10";
    private static final String PM25_JSON_KEY = "PM2.5";
    private static final String STATION_ID_JSON_KEY = "stationId";
    private static final Integer WARSAW_JSON_ID = 544;

    /**
     * Zwraca dane o jakości powietrza.
     * Pobierane dane są podane w formacie JSON, jako tablica rekordów podobnych do:
     * {"stationId":544,"stationName":"Warszawa, ul. Marszałkowska 68","aqIndex":1,"values":{"PM10":33.58088,"NO2":24.14206,"PM2.5":13.52564,"CO":0.22938}}
     * i zostają przetworzone do:
     * AirConditionData(pm10 = 33.58088, pm25 = 13.52564)
     * @return dane o jakości powietrza
     */
    @Override
    public AirConditionData makeRequest() throws IOException {
        JsonArray jsonArray = JsonHelper.asJsonArray(downloadPage(URL));
        JsonObject o = null;
        for(JsonElement e : jsonArray)
            if(e.getAsJsonObject().get(STATION_ID_JSON_KEY).getAsInt() == WARSAW_JSON_ID)
            {
                o = e.getAsJsonObject();
                break;
            }
        o = JsonHelper.innerJsonObject(o, VALUES_JSON_KEY);
        return new AirConditionData(JsonHelper.innerFloat(o, PM25_JSON_KEY), JsonHelper.innerFloat(o, PM10_JSON_KEY));
    }
}
