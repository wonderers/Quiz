package une.yasuaki.quiz;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class QuizDataBaseHelper extends SQLiteOpenHelper {

    static final private String DBNAME1 ="quiz1.sqlite";
    static private int VERSION =1;

    public QuizDataBaseHelper (Context context) {
        super(context,DBNAME1,null,VERSION);
    }

    static final private String TABLE_CREATE=
            "CREATE TABLE" + TABLE_QUIZ + "("+
                    COLUMN_ID +"INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_QUESTION + "TEXT," +
                    COLUMN_ANSWER + "TEXT," +
                    COLUMN_OPTION1 + "TEXT," +
                    COLUMN_OPTION2 + "TEXT," +
                    COLUMN_OPTION3 + "TEXT);";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(db != null){
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_CREATE);
            onCreate(db);
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
}
