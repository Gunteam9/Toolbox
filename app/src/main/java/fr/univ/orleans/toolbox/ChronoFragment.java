package fr.univ.orleans.toolbox;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ChronoFragment extends Fragment {

    private Chronometer chronometer;
    private boolean active;
    private long wait;
    private Button start;
    private Button pause;
    private Button restart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chrono,container,false);
        chronometer = view.findViewById(R.id.chrono);
        chronometer.setFormat("Chrono : %s");
        chronometer.setBase(SystemClock.elapsedRealtime());
        start = (Button)view.findViewById(R.id.buttonstart);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startChrono(view);
            }
        });
        pause = (Button)view.findViewById(R.id.buttonpause);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseChrono(view);
            }
        });
        restart = (Button)view.findViewById(R.id.buttonrestart);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartChrono(view);
            }
        });
        return view;
    }

    public void startChrono(View view){
        if (!active){
            chronometer.setBase(SystemClock.elapsedRealtime() - wait);
            chronometer.start();
            active = true;
        }
    }

    public void pauseChrono(View view){
        if(active){
            chronometer.stop();
            wait = SystemClock.elapsedRealtime() - chronometer.getBase();
            active = false;
        }
    }

    public void restartChrono(View view){
        chronometer.stop();
        chronometer.setBase(SystemClock.elapsedRealtime());
        active = false;
        wait = 0;
    }


}
