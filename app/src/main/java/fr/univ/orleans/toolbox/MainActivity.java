package fr.univ.orleans.toolbox;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    private static Context context;
    private Intent flashLightIntent;
    private int flashLightBlinkingSpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

        flashLightIntent = new Intent(this, Flashlight.class);

        MobileAds.initialize(this);
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
        startActivityForResult(flashLightIntent, Flashlight.REQUEST_CODE_INTENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (requestCode == Flashlight.REQUEST_CODE_INTENT)
                flashLightIntent = data;
        }
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