package fr.univ.orleans.toolbox;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONException;

import java.net.MalformedURLException;
import java.net.URL;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CoronavirusActivity extends AppCompatActivity {

    BarChart barChart;
    private static final String CAS_CONFIRMES = "casConfirmes";
    private static final String DECES = "deces";
    private static final String DECES_EHPAD = "decesEhpad";
    private static final String HOSPITALISES = "hospitalises";
    private static final String REANIMATION = "reanimation";
    private static final String GUERIS = "gueris";
    private static final String NOUVELLES_HOSPITALISATIONS = "nouvellesHospitalisations";
    private static final String NOUVELLES_REANIMATIONS = "nouvellesReanimations";
    private static final String URL_NAME = "https://coronavirusapi-france.now.sh/FranceLiveGlobalData";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coronavirus);
        barChart = findViewById(R.id.coronavirusBarChart);
        apiRequest();
    }


    /**
     * Fonction qui transforme la chaîne de caractères contenant l'URL en URL
     * @return l'URL créée
     */
    private URL createURL()  {
        try{
            return new URL(URL_NAME);
        }catch(MalformedURLException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Fonction qui gère la partie requête
     */
    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void apiRequest() {

        LocalDate hier = LocalDate.now().minusDays(1);
        final URL url = createURL();
        this.runOnUiThread(() -> {
            AsyncTask<URL, String, CoronavirusData> asyncTask = new AsyncCoronavirusRequest().execute(url);

            try {
                CoronavirusData data = asyncTask.get();
                if(data!=null)
                {
                    List<BarEntry> dataDuJour = fillDatas(data);
                    LocalDate aujourdhui = data.getDate();

                    BarDataSet set = new BarDataSet(dataDuJour, aujourdhui.toString());
                    set.setColors(ColorTemplate.MATERIAL_COLORS); //remplit chaque barre d'une couleur différente
                    set.setValueTextColor(Color.BLACK); // met le texte en noir
                    set.setValueTextSize(8f); //taille du texte

                    BarData barData = new BarData(set);
                    float barWidth = 0.6f; //la taille de chaque barre
                    barData.setBarWidth(barWidth);

                    barChart.setData(barData);
                    barChartConfiguration();
                }
            } catch (ExecutionException | InterruptedException | JSONException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Remplis la liste des chiffres essentiels collectés dans le JSON
     * @param data la data
     * @return la liste remplie
     * @throws JSONException si le JSONObject n'est pas valide
     */

    private List<BarEntry> fillDatas(CoronavirusData data) throws JSONException {

        List<BarEntry> dataDuJour = new ArrayList<>();
        dataDuJour.add(new BarEntry(0f, (float) data.getData().getInt(CAS_CONFIRMES)));
        dataDuJour.add(new BarEntry(1f, (float) data.getData().getInt(GUERIS)));
        dataDuJour.add(new BarEntry(2f, (float) data.getData().getInt(DECES)));
        dataDuJour.add(new BarEntry(3f, (float) data.getData().getInt(DECES_EHPAD)));
        dataDuJour.add(new BarEntry(4f, (float) data.getData().getInt(HOSPITALISES)));
        dataDuJour.add(new BarEntry(5f, (float) data.getData().getInt(NOUVELLES_HOSPITALISATIONS)));
        dataDuJour.add(new BarEntry(6f, (float) data.getData().getInt(REANIMATION)));
        dataDuJour.add(new BarEntry(7f, (float) data.getData().getInt(NOUVELLES_REANIMATIONS)));

        return dataDuJour;
    }

    /**
     * Fonction qui donne un nom à chaque barre correspondante
     * @return la liste remplie
     */

    private ArrayList<String> fillLabels()
    {
        ArrayList<String> labels = new ArrayList<>();
        labels.add(getString(R.string.cas_confirmes));
        labels.add(getString(R.string.deces));
        labels.add(getString(R.string.deces_ehpad));
        labels.add(getString(R.string.total_hospitalises));
        labels.add(getString(R.string.nouvelles_hospitalisations));
        labels.add(getString(R.string.total_reanimation));
        labels.add(getString(R.string.nouvelles_reanimations));
        labels.add(getString(R.string.gueris));

        return labels;
    }

    /**
     * Fonction dédiée à la configuration visuelle du graphique
     */

    public void barChartConfiguration()
    {
        barChart.setFitBars(true);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return fillLabels().get((int) value);
            }
        });

        xAxis.setLabelRotationAngle(-90);
        barChart.getDescription().setText("Quelques données sur le coronavirus en France");
        barChart.animateX(1000);
        barChart.animateY(2000);
        barChart.invalidate();
    }
}