package fr.univ.orleans.toolbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this);
        context = getApplicationContext();
        AdView ad = (AdView)findViewById(R.id.admain);
        AdRequest adRequest = new AdRequest.Builder().build();
        ad.loadAd(adRequest);
    }

    public static Context getAppContext() {
        return context;
    }
    /**
     * @author Romain
     */
    public void openFlashLight(View view) {
        startActivity(new Intent(this, Flashlight.class));
    }
    /**
     * @author Samir
     */
    public void openCalculatrice(View view) {
        startActivity(new Intent(this, CalculatriceActivity.class));
    }
    /**
     * @author Enzo
     */
    public void openNotepad(View view) {
        startActivity(new Intent(this, Notepad.class));
    }
    /**
     * @author  Romain
     */
    public void openCurrency(View view) { startActivity(new Intent(this, Currency.class));}
    /**
     * @author Samir
     */
    public void openCoronavirus(View view) { startActivity(new Intent(this, CoronavirusActivity.class));}
    /**
     * @author Enzo
     */
    public void openClock(View view) { startActivity(new Intent(this,ClockActivity.class));}
}