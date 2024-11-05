package br.com.thiagoavelinoalves.appgasetanol.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import br.com.thiagoavelinoalves.appgasetanol.model.Combustivel;

public class GasEtanolDB extends SQLiteOpenHelper {

    private static final String DB_NAME = "gasetanol.db";
    private static final int DB_VERSION = 1;

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

    public void salvarDados(String nomeTabela,
                            ContentValues dadosTabela){

        db.insert(nomeTabela, null, dadosTabela);

    }

    public List<Combustivel> gerarDados(){

        List<Combustivel> dadosCombustivel = new ArrayList<>();

        String querySQL = "SELECT * FROM COMBUSTIVEL";

        cursor = db.rawQuery(querySQL, null);

        if(cursor.moveToFirst()){
            do{
                Combustivel dados = new Combustivel();

                dados.setId(cursor.getInt(cursor.getColumnIndexOrThrow("ID")));
                dados.setPrecoCombustivel(cursor.getDouble(cursor.getColumnIndexOrThrow("PRECO")));
                dados.setNomeCombustivel(cursor.getString(cursor.getColumnIndexOrThrow("NOME")));
                dados.setRecomendacao(cursor.getString(cursor.getColumnIndexOrThrow("RECOMENDACAO")));

                dadosCombustivel.add(dados);

            }while(cursor.moveToNext());
        }

        return dadosCombustivel;
    }

    public void alterarDados(String nomeTabela,
                             ContentValues dadosTabela){

        int id = dadosTabela.getAsInteger("ID");
        db.update(nomeTabela, dadosTabela, "ID=?", new String[]{Integer.toString(id)});

    }

    public void deletarDados(String nomeTabela,
                             int id){

        db.delete(nomeTabela,"ID=?",new String[]{Integer.toString(id)});
    }

}
