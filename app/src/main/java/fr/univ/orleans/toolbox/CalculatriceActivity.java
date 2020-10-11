package fr.univ.orleans.toolbox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.Queue;

public class CalculatriceActivity extends AppCompatActivity {


    TextView affichage;
    double number;
    double res;
    char ope;
    Button btn;

    Queue<Integer> listDigit = new LinkedList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculatrice);
    }

    private void calcul()
    {
        if(ope == '*')
    }

    private void onButtonPressed(View view)
    {
        switch(view.getId())
        {
            case R.id.clear_button:
                this.number = 0;
                this.listDigit = null;
                break;
            case R.id.delete_button:
                this.listDigit.remove();
                break;
            case R.id.percent_button:
                this.number/=100;
                break;
            case R.id.div_button:
                this.ope = '/';
                break;
            case R.id.seven_button:
                this.listDigit.add(7);
                break;
            case R.id.eight_button:
                this.listDigit.add(8);
                break;
            case R.id.nine_button:
                this.listDigit.add(9);
                break;
            case R.id.mult_button:
                this.ope = '*';
            case R.id.four_button:
                this.listDigit.add(4);
                break;
            case R.id.five_button:
                this.listDigit.add(5);
                break;
            case R.id.six_button:
                this.listDigit.add(6);
                break;
            case R.id.sub_button:
                this.ope = '-';
            case R.id.two_button:
                this.listDigit.add(2);
                break;
            case R.id.three_button:
                this.listDigit.add(3);
                break;
            case R.id.sum_button:
                this.ope = '+';
            case R.id.zero_button:
                this.listDigit.add(0);
                break;
            case R.id.dot_button:
                this.ope = '.';
            case R.id.res_button:
                this.calcul();


        }
    }
}