package fr.univ.orleans.toolbox;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.net.URL;
import java.net.URLEncoder;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutionException;

public class Currency extends AppCompatActivity {

    private TextView date;
    private Spinner spinnerCurrency1;
    private EditText currencyInput;
    private Spinner spinnerCurrency2;
    private TextView currencyValue;
    private TextView currencyResult;

    private String currency1;
    private String currency2;
    private float quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);

        date = findViewById(R.id.currencyDate);
        spinnerCurrency1 = findViewById(R.id.currencySelector1);
        currencyInput = findViewById(R.id.currencyInput);
        spinnerCurrency2 = findViewById(R.id.currencySelector2);
        currencyValue = findViewById(R.id.currencyValue);
        currencyResult = findViewById(R.id.currencyResult);

        TypedArray imgsInt = getResources().obtainTypedArray(R.array.currenciesImg);
        final String[] texts = getResources().getStringArray(R.array.currenciesName);

        Drawable[] imgs = new Drawable[imgsInt.length()];
        for (int i = 0; i < imgsInt.length(); i++)
            imgs[i] = imgsInt.getDrawable(i);

        imgsInt.recycle();

        spinnerCurrency1.setAdapter(new SpinnerAdapter(this, imgs, texts));
        spinnerCurrency2.setAdapter(new SpinnerAdapter(this, imgs, texts));

        spinnerCurrency1.setSelection(1);
        spinnerCurrency2.setSelection(0);

        spinnerCurrency1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currency1 = texts[position];
                apiRequest();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerCurrency2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currency2 = texts[position];
                apiRequest();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        currencyInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                quantity = Float.parseFloat(currencyInput.getText().toString());
                apiRequest();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void swapCurrency(View view) {
        int index0 = spinnerCurrency1.getSelectedItemPosition();
        spinnerCurrency1.setSelection(spinnerCurrency2.getSelectedItemPosition());
        spinnerCurrency2.setSelection(index0);

        apiRequest();
    }

    public void back(View view) {
        finish();
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void apiRequest() {
        if (currencyInput.getText().toString().isEmpty())
            return;

        final URL url = createURL();


        this.runOnUiThread(() -> {
            AsyncTask<URL, String, CurrencyData> asyncTask = new AsyncCurrencyRequest().execute(url);

            try {
                CurrencyData data = asyncTask.get();

                date.setText("Le " + data.getDate().format(DateTimeFormatter.ofPattern("dd/mm/yyyy")) + " à " + data.getDate().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                currencyResult.setText(String.valueOf(data.getAmount()));

            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private URL createURL() {
        String apiKey = "80eb01ac9dmsh14d4a229d5e843dp1d5546jsn6db9fded890b";
        String url1 = "http://api.wahrungsrechner.org/v1/quotes/";
        String url2 = "/json?qty=";
        String url3 = "&key=";
        String slash = "/";

        try {
            String urlString = url1 + URLEncoder.encode(currency1, "UTF-8") + slash + URLEncoder.encode(currency2, "UTF-8") +
                    url2 + URLEncoder.encode(String.valueOf(quantity), "UTF-8") + url3 + apiKey;
            return new URL(urlString);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null; // URL mal formée
    }
}