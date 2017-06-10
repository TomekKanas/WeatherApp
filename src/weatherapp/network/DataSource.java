package weatherapp.network;

import weatherapp.data.DataClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Abstrakcyjna klasa do pobierania danych.
 */
public abstract class DataSource{

    /**
     * Pobiera dane z internetu
     * @return dane
     */
    public abstract DataClass makeRequest() throws IOException;

    /**
     * Pobiera stronę jako tekst
     * @param url : adres strony
     * @return zawartość strony
     */
    protected String downloadPage(String url) throws IOException {
        InputStreamReader is = null;
        BufferedReader br;
        String line;
        StringBuilder buf = new StringBuilder();
        try
        {
            is = new InputStreamReader(new URL(url).openStream());
            br = new BufferedReader(is);
            while ((line = br.readLine()) != null) buf.append(line);
        }
        finally
        {
            try
            {
                if (is != null) is.close();
            }
            catch (IOException ioe) {}
        }
        return buf.toString();
    }
}
