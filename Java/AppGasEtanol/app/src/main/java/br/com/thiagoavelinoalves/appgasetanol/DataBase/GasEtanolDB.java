package br.com.thiagoavelinoalves.appgasetanol.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class GasEtanolDB extends SQLiteOpenHelper {

    public static final String DB_NAME = "gasetanol.db";
    public static final int DB_VERSION = 1;

    Cursor cursor;

    SQLiteDatabase db;

    public GasEtanolDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlTabelaCombustivel = "CREATE TABLE COMBUSTIVEL (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "PRECO REAL, " +
                "NOME TEXT, " +
                "RECOMENDACAO TEXT)";

        db.execSQL(sqlTabelaCombustivel);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
