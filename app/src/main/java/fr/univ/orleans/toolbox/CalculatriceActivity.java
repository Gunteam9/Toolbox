package fr.univ.orleans.toolbox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class CalculatriceActivity extends AppCompatActivity {

    TextView affichage;
    StringBuilder number;
    boolean hasDot;
    boolean hasOperator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculatrice);
        affichage = (TextView) findViewById(R.id.affichage);
        number = new StringBuilder();
        number.append(0);
        hasDot = false;
        hasOperator = false;
    }


    /**
     * Fonction ajoutant une opération ou une opérande à la liste de caractères
     * @param view
     */
    public void addOpe(View view)
    {
        Button button = (Button) view;
        if(button.getText().toString().charAt(0) == '.' && hasDot)
            return;

        if(hasDot)
            hasDot = false;

        if(number.length() > 0 && hasOperator)
            number.deleteCharAt(number.length()-1);

        if(button.getText().toString().charAt(0) == '.')
            hasDot = true;
        else
            hasOperator = true;

        number.append(button.getText().toString().charAt(0));
        affichage.setText(number.toString());
    }

    public void addDigit(View view)
    {
        Button button = (Button) view;
        if(hasOperator)
            hasOperator = false;
        number.append(button.getText().toString().charAt(0));
        affichage.setText(number.toString());
    }

    /**
     * Supprime le dernier chiffre (ou le symbole .)
     * @param view
     */

    public void deleteDigit(View view)
    {
        if(number.length() > 0 && !hasOperator)
        {
            number.deleteCharAt(number.length()-1);
            affichage.setText(number.toString());
        }
        else
            if(number.length() == 0)
                affichage.setText("0");
    }

    /**
     * Supprime l'opération
     * @param view
     */

    public void deleteOperation(View view)
    {
        number.setLength(0);
        number.append(0);
        affichage.setText("0");
    }

    public void calculPourcentage(View view)
    {
        int index = 0;
        if(hasOperator)
            return;

        for(int i = number.length() - 1; i >=0; i--)
        {
            if(number.charAt(i) == '+' ||
                    number.charAt(i) == '-' ||
                    number.charAt(i) == '*' ||
                    number.charAt(i) == '/')
                index = i+1;
        }

        double percentage = Double.parseDouble(number.subSequence(index, number.length()).toString())/100;
        System.out.println(Double.parseDouble(number.subSequence(index, number.length()).toString()));
        number.delete(index, number.length());
        number.append(percentage);
        affichage.setText(number.toString());

    }

    public void resultat(View view)
    {
        double res = 0;
        Stack<Double> operandes = new Stack<>();
        Stack<Character> operators = new Stack<>();

        if (number.length() == 0)
            return;

        //Supprime le point dans le cas où ce serait le dernier symbole du String
        if(number.charAt(number.length()-1) == '.' ||
                number.charAt(number.length()-1) == '+' ||
                number.charAt(number.length()-1) == '-' ||
                number.charAt(number.length()-1) == '*' ||
                number.charAt(number.length()-1) == '/'
        )
            number.deleteCharAt(number.length()-1);


        if(number.length() == 1)
        {
            res = Double.parseDouble(number.toString());
            affichage.setText(String.valueOf(res));
            number.setLength(0);
            return;
        }

        for(int i = 0; i < number.length(); i++)
        {
            StringBuilder val = new StringBuilder();
            if(number.charAt(i) == '.')
                val.append(0);
            else
            {
                if(Character.isDigit(number.charAt(i)))
                {

                    while(i < number.length() && (Character.isDigit(number.charAt(i)) || number.charAt(i) == '.'))
                    {
                        val.append(number.charAt(i));
                        i++;
                    }
                    operandes.push(Double.parseDouble(val.toString()));
                    i--;
                }
            }

            if(number.charAt(i) == '+' || number.charAt(i) == '-' || number.charAt(i) == '*' || number.charAt(i) == '/')
            {
                if(!operators.empty() && ((number.charAt(i) == '+' || number.charAt(i) == '-')
                                        && operators.peek() == '*' || operators.peek() == '/'))
                {
                    res+= calcul(operandes.pop(), operandes.pop(), operators.pop());
                }
                operators.push(number.charAt(i));
            }
        }

        while(!operandes.isEmpty())
        {
            res+= calcul(operandes.pop(), operandes.pop(), operators.pop());
            if(res == -1)
            {
                number.setLength(0);
                number.append(0);
                affichage.setText(R.string.divzero);
                return;
            }
        }
        affichage.setText(String.valueOf(res));
        number.setLength(0);
        number.append(0);
    }

    private double calcul(double value1, double value2, char ope)
    {
        double res = 0;
        switch(ope)
        {
            case '+':
                res = value2 + value1;
                break;
            case '-':
                res = value2 - value1;
                break;
            case '*':
                res = value2 * value1;
                break;
            case '/':
                if(value1 != 0)
                    res = value2 / value1;
                else
                {
                    return -1;
                }
                break;
        }
        return res;
    }
}