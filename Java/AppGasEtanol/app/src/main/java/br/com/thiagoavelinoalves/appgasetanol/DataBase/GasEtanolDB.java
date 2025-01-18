package br.com.thiagoavelinoalves.appgasetanol.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.thiagoavelinoalves.appgasetanol.controller.ICRUD;
import br.com.thiagoavelinoalves.appgasetanol.model.Combustivel;
import br.com.thiagoavelinoalves.appgasetanol.util.UtilAppGasEtanol;

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

    public void salvarDados(String nomeTabela, ContentValues dadosTabela){

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

        }else{
            Log.i("ELSE", "gerarDados: Parou aqui");
        }

        return dadosCombustivel;
    }

    public boolean insertDados(String nomeTabela, ContentValues combustivel){

        db = getWritableDatabase();
        boolean retorno = false;

        try{
            retorno = db.insert(nomeTabela,null,combustivel) > 0;
            Log.i(UtilAppGasEtanol.TAG,"Dados salvos com sucesso");
        }catch(Exception e){
            Log.e(UtilAppGasEtanol.TAG,e.getMessage());
        }
        return retorno;
    }

    public boolean deleteDados(String nomeDaTabela, int id){

        db = getWritableDatabase();

        boolean retorno = false;

        try{
            retorno = db.delete(nomeDaTabela,"ID=?",new String[]{String.valueOf(id)}) > 0;
            Log.i(UtilAppGasEtanol.TAG,"Dados excluídos com sucesso");
        }catch(Exception e){
            Log.e(UtilAppGasEtanol.TAG,e.getMessage());
        }
        return retorno;
    }

    public boolean alterarDados(String nomeTabela, ContentValues contentValues){
        db = getWritableDatabase();

        boolean retorno = false;

        try{
            retorno = db.update(nomeTabela, contentValues,"ID= ?",new String[]{String.valueOf(contentValues.get("ID"))}) > 0;
            Log.i(UtilAppGasEtanol.TAG,"Dados atualizados");
        }catch(Exception e){
            Log.e(UtilAppGasEtanol.TAG,e.getMessage());
        }

        return retorno;

    }

    public List<Combustivel> listaDados(String nomeTabela, int id){

        db = getWritableDatabase();

        Cursor cursor;

        List<Combustivel> lista = new ArrayList<>();

        cursor = db.rawQuery("SELECT * FROM "+nomeTabela + " WHERE ID = ?", new String[]{String.valueOf(id)});

        if(cursor.moveToFirst()){

            Combustivel combustivel = new Combustivel();

            do{
                combustivel.setId(cursor.getInt(cursor.getColumnIndexOrThrow("ID")));
                combustivel.setPrecoCombustivel(cursor.getDouble(cursor.getColumnIndexOrThrow("PRECO")));
                combustivel.setNomeCombustivel(cursor.getString(cursor.getColumnIndexOrThrow("NOME")));
                combustivel.setRecomendacao(cursor.getString(cursor.getColumnIndexOrThrow("RECOMENDACAO")));

                lista.add(combustivel);

            }while(cursor.moveToNext());

        }

        return lista;
    }

}
