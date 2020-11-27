package fr.univ.orleans.toolbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.util.Stack;

public class CalculatriceActivity extends AppCompatActivity {

    TextView affHistorique;
    TextView affichage;
    StringBuilder number;
    DbOpenHelper db;
    boolean hasDot;
    boolean hasOperator;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculatrice);
        affichage = findViewById(R.id.affichage);
        affHistorique = findViewById(R.id.historique);
        number = new StringBuilder();
        number.append(0);
        hasDot = false;
        hasOperator = false;
        db = DbOpenHelper.instance;
    }

    /**
     * Fonction ajoutant un opérateur ou un point lorsqu'on appuie sur une de ces touches
     * @param view La view
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

    /**
     * Méthode ajoutant un chiffre lorsqu'on appuie sur un des boutons d'un chiffre
     * @param view La view
     */

    public void addDigit(View view)
    {
        Button button = (Button) view;
        if(hasOperator)
            hasOperator = false;
        number.append(button.getText().toString().charAt(0));
        affichage.setText(number.toString());
    }

    /**
     * Supprime le dernier chiffre (ou le symbole .) lorsqu'on appuie sur la touche DEL
     * @param view La view
     */

    public void deleteDigit(View view)
    {
        if(number.length() > 0 && !hasOperator)
        {
            number.deleteCharAt(number.length()-1);
            affichage.setText(number.toString());
            if(hasDot)
                hasDot = false;
        }
        else
            if(number.length() == 0)
                affichage.setText("0");
            else {
                if(checkOperator(number.length() - 1) && !hasOperator)
                    hasOperator = true;
            }
    }

    /**
     * Méthode qui supprime l'opération lorsqu'on appuie sur la touche CLEAR
     * @param view La view
     */

    public void deleteOperation(View view)
    {
        deleteDatabase();
        if(hasOperator)
            hasOperator = false;

        if(hasDot)
            hasDot = false;

        number.setLength(0);
        number.append(0);
        affichage.setText("0");
    }

    private void deleteDatabase()
    {
        SQLiteDatabase historique = db.getWritableDatabase();
        if(number.length() == 0)
            historique.delete(DbOpenHelper.TABLE_OPERATIONS,null, null);
        db.close();
        affHistorique.setText("");
    }

    /**
     * Méthode gérant
     * @param view La view
     */

    public void calculPourcentage(View view)
    {
        int index = 0;
        if(hasOperator)
            return;

        for(int i = number.length() - 1; i >=0; i--)
            if(checkOperator(i))
            {
                index = i+1;
                break;
            }


        double percentage = Double.parseDouble(number.subSequence(index, number.length()).toString())/100;
        System.out.println(Double.parseDouble(number.subSequence(index, number.length()).toString()));
        number.delete(index, number.length());
        number.append(percentage);
        affichage.setText(number.toString());

    }

    /**
     * Méthode calculant l'opération en transformant le StringBuilder en deux Stack et en prenant en compte l'ordre de priorité des opérations
     * @param view La view
     */

    public void resultat(View view)
    {
        double res = 0;
        Stack<Double> operandes = new Stack<>();
        Stack<Character> operators = new Stack<>();

       //Si la 1re touche appuyée est la touche "="
        if (number.length() == 0)
            return;

        //Supprime le point ou un opérateur dans le cas où ce serait le dernier symbole du String
        if(checkOperator(number.length()-1))
            number.deleteCharAt(number.length()-1);


        //S'il n'y a qu'un seule chiffre dans le String
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
            //Si l'opérande commence par un ".", ça devient "0."
            if(number.charAt(i) == '.')
                val.append(0);
            else
            {
                //Parsing en Double l'opérande
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
            //Parsing en char l'opérateur
            if(checkOperator(i))
            {
                if(!operators.empty() && ((number.charAt(i) != '*' && number.charAt(i) != '/') || (operators.peek() != '+' && operators.peek() != '-')))
                   operandes.push(calcul(operandes.pop(), operandes.pop(), operators.pop()));
                
                operators.push(number.charAt(i));
            }
        }

        while(!operators.isEmpty())
        {
            operandes.push(calcul(operandes.pop(), operandes.pop(), operators.pop()));
            System.out.println(operandes.toString());
            System.out.println(operators.toString());
        }


        res+=operandes.pop();
        if(res == -1)
        {
            number.setLength(0);
            number.append(0);
            affichage.setText(R.string.divzero);
            return;
        }
        number.append(" = ").append(res);
        this.addHistorique();
        number.setLength(0);
        number.append(0);
    }

    /**
     * Fonction qui ajoute l'opération calculée dans la base de données
     */

    private void addHistorique()
    {
        SQLiteDatabase actualdb = db.getWritableDatabase();
        ContentValues historique = new ContentValues();
        historique.put(DbOpenHelper.COLUMN_OPERATION, number.toString());
        actualdb.insert(DbOpenHelper.TABLE_OPERATIONS,null, historique);

        try (Cursor cursor = actualdb.query(DbOpenHelper.TABLE_OPERATIONS, new String[]{DbOpenHelper.COLUMN_OPERATION}, null, null, null, null, null)) {
            StringBuilder s = new StringBuilder();
            while (cursor.moveToNext())
                s.append(cursor.getString(0)).append("\n");
            affHistorique.setText(s.toString());

        }
        db.close();
    }

    /**
     * Fonction s'occupant d'effectuer une opération
     * @param value1 La première valeur
     * @param value2 La seconde valeur
     * @param ope L'opérateur
     * @return le résultat
     */

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

    /**
     * Méthode qui vérifie s'il y a un opérateur à l'index i
     * @param index l'index dans le stringbuilder
     * @return vrai s'il y en a un, faux sinon
     */

    private boolean checkOperator(int index)
    {
        return number.charAt(index) == '+' || number.charAt(index) == '-' || number.charAt(index) == '*' || number.charAt(index) == '/';
    }
}