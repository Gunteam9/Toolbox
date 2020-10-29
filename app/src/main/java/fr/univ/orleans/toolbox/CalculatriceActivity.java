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
    StringBuilder number = new StringBuilder();
    boolean hasDot = false;
    double value1, value2, res;
    char ope;
    Button equal, clear, del, sum, sub, multi, div, zero, one, two, three, four, five, six, seven, eight, nine, dot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculatrice);

        equal = (Button) findViewById(R.id.res_button);
        clear = (Button) findViewById(R.id.clear_button);
        sum = (Button) findViewById(R.id.sum_button);
        sub = (Button) findViewById(R.id.sub_button);
        multi = (Button) findViewById(R.id.mult_button);
        div = (Button) findViewById(R.id.div_button);
        zero = (Button) findViewById(R.id.zero_button);
        one = (Button) findViewById(R.id.one_button);
        two = (Button) findViewById(R.id.two_button);
        three = (Button) findViewById(R.id.three_button);
        four = (Button) findViewById(R.id.four_button);
        five = (Button) findViewById(R.id.five_button);
        six = (Button) findViewById(R.id.six_button);
        seven = (Button) findViewById(R.id.seven_button);
        eight = (Button) findViewById(R.id.eight_button);
        nine = (Button) findViewById(R.id.nine_button);
        dot = (Button) findViewById(R.id.dot_button);
        del = (Button) findViewById(R.id.delete_button);
        affichage = (TextView) findViewById(R.id.affichage);

        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number.append(0);
                affichage.setText(number.toString());
            }
        });

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number.append(1);
                affichage.setText(number.toString());
            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number.append(2);
                affichage.setText(number.toString());
            }
        });

        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number.append(3);
                affichage.setText(number.toString());
            }
        });

        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number.append(4);
                affichage.setText(number.toString());
            }
        });

        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number.append(5);
                affichage.setText(number.toString());
            }
        });

        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number.append(6);
                affichage.setText(number.toString());
            }
        });

        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number.append(7);
                affichage.setText(number.toString());
            }
        });

        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number.append(8);
                affichage.setText(number.toString());
            }
        });

        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number.append(9);
                affichage.setText(number.toString());
            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(number.length() > 0)
                {
                    number.deleteCharAt(number.length()-1);
                    affichage.setText(number.toString());
                }
                else
                    affichage.setText("0");

            }
        });

        sum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value1 = Double.parseDouble(number.toString());
                number.setLength(0);
                ope = '+';
                affichage.setText("0");
            }
        });

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value1 = Double.parseDouble(number.toString());
                number.setLength(0);
                ope = '-';
                affichage.setText("0");
            }
        });

        multi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value1 = Double.parseDouble(number.toString());
                number.setLength(0);
                ope = '*';
                affichage.setText("0");
            }
        });

        div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value1 = Double.parseDouble(number.toString());
                number.setLength(0);
                ope = '/';
                affichage.setText("0");
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number.setLength(0);
                affichage.setText("0");
            }
        });

        dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!hasDot)
                {
                    number.append('.');
                    hasDot = true;
                    affichage.setText(number.toString());
                }
            }
        });

        equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value2 = Double.parseDouble(number.toString());
                res = calcul();
                number.setLength(0);
                affichage.clearComposingText();
                affichage.setText(String.valueOf(res));
            }
        });
    }

    private double calcul()
    {
        if(ope == '+')
        {
            this.res = this.value1 + this.value2;
            return res;
        }

        if(ope == '-')
        {
            this.res = this.value1 - this.value2;
            return res;
        }

        if(ope == '*')
        {
            this.res = this.value1 * this.value2;
            return res;
        }

        if(ope == '/')
        {
            this.res = this.value1 / this.value2;
            return res;
        }
        return 0;
    }
}