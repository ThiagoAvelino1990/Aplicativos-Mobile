package br.com.thiagoavelinoalves.appgasetanol.controller;

import android.content.ContentValues;
import android.content.SharedPreferences;

import br.com.thiagoavelinoalves.appgasetanol.DataBase.GasEtanolDB;
import br.com.thiagoavelinoalves.appgasetanol.model.Combustivel;
import br.com.thiagoavelinoalves.appgasetanol.view.GasEtanolActivity;

public class CombustivelController extends GasEtanolDB {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor combustivelRecomendado;
    public static final String NOME_PREFERENCES = "pref_combustivel";

    public CombustivelController(GasEtanolActivity gasEtanolActivity){
        super(gasEtanolActivity);
        sharedPreferences = gasEtanolActivity.getSharedPreferences(NOME_PREFERENCES,0);
        combustivelRecomendado = sharedPreferences.edit();
    }

    public void salvar(Combustivel combustivel){

        ContentValues contentValues = new ContentValues();

        combustivelRecomendado.putString("NomeCombustivel", combustivel.getNomeCombustivel());
        combustivelRecomendado.putString("PrecoCombustivel", String.valueOf(combustivel.getPrecoCombustivel()));
        combustivelRecomendado.putString("Recomendacao",combustivel.getRecomendacao());
        combustivelRecomendado.apply();

        contentValues.put("PRECO", combustivel.getPrecoCombustivel());
        contentValues.put("NOME", combustivel.getNomeCombustivel());
        contentValues.put("RECOMENDACAO", combustivel.getRecomendacao());


        salvarDados("COMBUSTIVEL", contentValues);

    }

    public void limpar(){
        combustivelRecomendado.clear();
        combustivelRecomendado.apply();
    }

}
