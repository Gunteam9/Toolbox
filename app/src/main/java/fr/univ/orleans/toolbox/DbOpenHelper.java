package fr.univ.orleans.toolbox;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DbOpenHelper extends SQLiteOpenHelper {
    public static final String TABLE_NOTE="note";
    //rajouter les string pour la calculatrice
    public static final String COLUMN_ID="id";
    public static final String COLUMN_TITLE="title";
    public static final String COLUMN_CONTENT="content";

    public static DbOpenHelper instance = new DbOpenHelper(MainActivity.getAppContext(),"db",null,1);

    private DbOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String db =
                "create table "+TABLE_NOTE+" ( "+ COLUMN_ID + " integer primary key , " + COLUMN_TITLE + " text not null ," + COLUMN_CONTENT + " text not null " + ") ;";
        sqLiteDatabase.execSQL(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
