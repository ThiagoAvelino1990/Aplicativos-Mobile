package br.com.dev.appclientes.datasource;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import br.com.dev.appclientes.api.AppUtil;

public class AppDataBase extends SQLiteOpenHelper {

    public static final String DB_NAME="CLIENTES.SQL";
    public static final int DB_VERSION = 1;

    SQLiteDatabase db;


    public AppDataBase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insertDados(String nometabela, ContentValues objeto){

        db = getWritableDatabase();

        boolean retorno = false;

        try{
            retorno = db.insert(nometabela, null, objeto) > 0;
            Log.i(AppUtil.TAG,objeto.toString());
        }catch(Exception e){
            Log.e(AppUtil.TAG,e.getMessage());
        }


        return retorno;
    }
}
