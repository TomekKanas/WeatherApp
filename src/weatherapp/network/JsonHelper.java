package weatherapp.network;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


/**
 * Pomocnicza klasa do parsowania JSONa.
 */
public class JsonHelper {

    /**
     * Wyciąga wewnętrzny obiekt w formacie JSON
     * @param o obiekt JSON
     * @param key nazwa wewnętrznego obiektu
     * @return obiekt JSON
     */
    public static JsonObject innerJsonObject(JsonObject o, String key)
    {
        if(o == null) return null;
        JsonElement je = o.get(key);
        if(je == null) return null;
        return o.get(key).getAsJsonObject();
    }

    /**
     * Wyciąga pole typu float z obiektu typu JSON
     * @param o obiekt JSON
     * @param key nazwa wewnętrznego pola
     * @return liczba rzeczywista
     */
    public static Float innerFloat(JsonObject o, String key)
    {
        if(o == null) return null;
        JsonElement je = o.get(key);
        if(je == null) return null;
        return o.get(key).getAsFloat();
    }

    /**
     * Parsuje tekst jako obiekt w formacie JSON
     * @param s tekst
     * @return zparsowany obiekt
     */
    public static JsonObject asJsonObject(String s) {
        return parse(s).getAsJsonObject();
    }

    /**
     * Parsuje tekst jako tablicę obiektów w formacie JSON
     * @param s tekst
     * @return tablica zparsowanych obiektów
     */
    public static JsonArray asJsonArray(String s) {
        return parse(s).getAsJsonArray();
    }

    /**
     *  Parsuje tekst w formacie JSON
     * @param s tekst
     * @return obiekt zparsowany
     */
    private static JsonElement parse(String s) {
        return new JsonParser().parse(s);
    }
}
