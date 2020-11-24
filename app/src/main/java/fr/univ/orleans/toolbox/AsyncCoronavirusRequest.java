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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AsyncCoronavirusRequest extends AsyncTask<URL, String, CoronavirusData> {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected CoronavirusData doInBackground(URL... urls) {
        HttpURLConnection connection = null;

        try{
            connection = (HttpURLConnection) urls[0].openConnection();
            int response = connection.getResponseCode();

            if (response == HttpURLConnection.HTTP_OK) {
                StringBuilder builder = new StringBuilder();

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;

                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                }
                catch (IOException e) {
                    //Like runOnUiThread
                    new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(MainActivity.getAppContext(), R.string.read_error, Toast.LENGTH_LONG).show());
                    e.printStackTrace();
                }
                return transformData(new JSONObject(builder.toString()));
            }
            else {
                new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(MainActivity.getAppContext(), R.string.connect_error, Toast.LENGTH_LONG).show());
            }
        }
        catch (Exception e) {
            new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(MainActivity.getAppContext(), R.string.read_error, Toast.LENGTH_LONG).show());
            e.printStackTrace();
        }
        finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }


    /**
     * Découpe le JSSONObject en ne prenant pas les données non essentielles
     * @param jsonObject
     * @return
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
