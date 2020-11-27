package fr.univ.orleans.toolbox;

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
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AsyncCoronavirusRequest extends AsyncTask<URL, String, CoronavirusData> {
    //If there are errors during the request
    private boolean errorInReading;
    private boolean connectionError;
    private boolean otherError;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected CoronavirusData doInBackground(URL... urls) {
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
        } catch (Exception e) {
            e.printStackTrace();
            otherError = true;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return null;
    }

    //Print error with priority
    @Override
    protected void onPostExecute(CoronavirusData currencyData) {
        if (connectionError) {
            Toast.makeText(MainActivity.getAppContext(), R.string.connect_error, Toast.LENGTH_LONG).show();
            return;
        }
        if (errorInReading) {
            Toast.makeText(MainActivity.getAppContext(), R.string.read_error, Toast.LENGTH_LONG).show();
            return;
        }

        if (otherError) {
            Toast.makeText(MainActivity.getAppContext(), R.string.unknownError, Toast.LENGTH_LONG).show();
        }
    }


    /**
     * Découpe le JSSONObject en ne prenant pas les données non essentielles
     * @param jsonObject l'objet
     * @return le fractionnement des données importantes (date et le reste des données qui seront utilisées)
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private CoronavirusData transformData(JSONObject jsonObject) {

        try {
            JSONArray franceGlobalLiveData = jsonObject.getJSONArray("FranceGlobalLiveData");
            LocalDate date = LocalDate.parse(franceGlobalLiveData.getJSONObject(0).getString("date"));
            JSONObject targets = franceGlobalLiveData.getJSONObject(0);

            return new CoronavirusData(date, targets);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
