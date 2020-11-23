package fr.univ.orleans.toolbox;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbOpenHelper extends SQLiteOpenHelper {
    public static final String TABLE_NOTE="note";
    public static final String TABLE_OPERATIONS="operations";
    //rajouter les string pour la calculatrice
    public static final String COLUMN_OPERATION="operation";
    public static final String COLUMN_ID="id";
    public static final String COLUMN_TITLE="title";
    public static final String COLUMN_CONTENT="content";
    private static final String TAG = "CalculatriceActivity";
    public static DbOpenHelper instance = new DbOpenHelper(MainActivity.getAppContext(),"db.db",null,2);

    private DbOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String dbNote =
               "create table " + TABLE_NOTE+" ( "+ COLUMN_ID + " integer primary key autoincrement, " + COLUMN_TITLE + " text not null ," + COLUMN_CONTENT + " text not null " + ") ;";

        String dbCalculatrice =
                "create table " + TABLE_OPERATIONS+ " ( "+ COLUMN_ID + " integer primary key autoincrement, " + COLUMN_OPERATION + " text not null " + ") ;";

        sqLiteDatabase.execSQL(dbNote);
        try{
            sqLiteDatabase.execSQL(dbCalculatrice);
        }catch(SQLException e){
            System.out.println("Erreur lors de la création de la table");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
