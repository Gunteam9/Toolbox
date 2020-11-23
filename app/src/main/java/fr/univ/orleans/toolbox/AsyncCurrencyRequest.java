package fr.univ.orleans.toolbox;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class AsyncCurrencyRequest extends AsyncTask<URL, String, CurrencyData> {


    //If there are errors during the request
    private boolean errorInReading;
    private boolean connectionError;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected CurrencyData doInBackground(URL... urls) {
        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection) urls[0].openConnection();
            int response = connection.getResponseCode();

            if (response == HttpURLConnection.HTTP_OK) {
                StringBuilder builder = new StringBuilder();

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;

                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    errorInReading = true;
                }

                return transformData(new JSONObject(builder.toString()));
            }
            else
                connectionError = true;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            connectionError = true;
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            errorInReading = true;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return null;
    }

    //Print error with priority
    @Override
    protected void onPostExecute(CurrencyData currencyData) {
        if (connectionError) {
            Toast.makeText(MainActivity.getAppContext(), R.string.connect_error, Toast.LENGTH_LONG).show();
            return;
        }
        if (errorInReading) {
            Toast.makeText(MainActivity.getAppContext(), R.string.read_error, Toast.LENGTH_LONG).show();
            return;
        }

    }

    /**
     * Transform data into a class for a better return
     * @param jsonObject
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private CurrencyData transformData(JSONObject jsonObject) {
        try {
            //If error
            if (!jsonObject.getString("result").equals("success")) {
                Looper.prepare();
                Toast.makeText(MainActivity.getAppContext(), R.string.api_error, Toast.LENGTH_LONG).show();
                return null;
            }

            LocalDateTime date = LocalDateTime.parse(jsonObject.getString("time_last_update_utc"), DateTimeFormatter.RFC_1123_DATE_TIME);
            String source = jsonObject.getString("base_code");
            JSONObject targets = (JSONObject) jsonObject.get("conversion_rates");

            System.out.println(date);

            return new CurrencyData(date, source, targets);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
