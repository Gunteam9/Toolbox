package fr.univ.orleans.toolbox;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Locale;

public class CountdownFragment extends Fragment {
    private long durationmili = 60000;
    private boolean countactive;
    private Button countstart;
    private Button countpause;
    private Button countrestart;
    private Button countset;
    private TextView text;
    private EditText input;
    private CountDownTimer countdown;
    private String time;
    private long left = durationmili;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_countdown,container,false);
        text = view.findViewById(R.id.countdown);
        input = view.findViewById(R.id.settime);
        countstart = view.findViewById(R.id.buttoncountstart);
        countstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCount(view);
            }
        });
        countpause = view.findViewById(R.id.buttoncountpause);
        countpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseCount(view);
            }
        });
        countrestart = view.findViewById(R.id.buttoncountrestart);
        countrestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartCount(view);
            }
        });
        countset = view.findViewById(R.id.buttonset);
        countset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String duration = input.getText().toString();
                if (duration.length() != 0){
                    long value = Long.parseLong(duration) * 60000;
                    setduration(view,value);
                }
            }
        });
        countupdate(view);
        return view;
    }

    /**
     * Méthode qui démarre le minuteur
     */
    public void startCount(View view){
        if (!countactive){
            countdown = new CountDownTimer(left,1) {
                @Override
                public void onTick(long l) {
                    left = l;
                    countupdate(view);
                }
                @Override
                public void onFinish() {
                    countactive = false;
                }
            }.start();
            countactive = true;
        }
    }

    /**
     * Méthode qui suspend le temps du minuteur
     */
    public void pauseCount(View view){
        if (countdown == null){
            startCount(view);
            pauseCount(view);
        }
        if(countactive){
            countdown.cancel();
            countactive = false;
        }
    }

    /**
     * Méthode qui réinitialise le minuteur, uniquement s'il est en pause
     */
    public void restartCount(View view){
        if(!countactive) {
            if (countdown == null) {
                startCount(view);
                pauseCount(view);
            }
            left = durationmili;
            countupdate(view);
        }
    }

    /**
     * Méthode qui actualise l'affichage du minuteur
     */
    public void countupdate(View view){
        long min = (long) left / 1000 /60;
        long sec = (long) left / 1000 % 60;
        time = String.format(Locale.getDefault(),"Minutes : %02d:%02d",min,sec);
        text.setText(time);
    }

    /**
     * Méthode qui permet de modifier la durée du minuteur
     * @param durationmili la durée choisie par l'utilisateur dans l'edit text correspondant
     */
    public void setduration(View view, long durationmili){
        if(!countactive){
            this.durationmili = durationmili;
            if (countdown == null){
                if (countdown == null){
                    startCount(view);
                    pauseCount(view);
                }
            }
            restartCount(view);
        }
    }
}
