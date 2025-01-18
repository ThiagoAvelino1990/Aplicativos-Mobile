package br.com.thiagoavelinoalves.appgasetanol.controller;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Collections;
import java.util.List;

import br.com.thiagoavelinoalves.appgasetanol.DataBase.GasEtanolDB;
import br.com.thiagoavelinoalves.appgasetanol.model.Combustivel;
import br.com.thiagoavelinoalves.appgasetanol.util.UtilAppGasEtanol;
import br.com.thiagoavelinoalves.appgasetanol.view.GasEtanolActivity;

public class CombustivelController extends GasEtanolDB implements ICRUD<Combustivel>{

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor combustivelRecomendado;
    public static final String NOME_PREFERENCES = "pref_combustivel";

    ContentValues dadosCombustivel;

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

    public List<Combustivel> getGerarDados(){

        return gerarDados();
    }


    @Override
    public boolean createObject(Combustivel obj) {

        dadosCombustivel = new ContentValues();

        dadosCombustivel.put("PRECO", obj.getPrecoCombustivel());
        dadosCombustivel.put("NOME", obj.getNomeCombustivel());
        dadosCombustivel.put("RECOMENDACAO", obj.getRecomendacao());

        return insertDados(UtilAppGasEtanol.TABELA_COMBUSTIVEL, dadosCombustivel);
    }

    @Override
    public List<Combustivel> readObject(int id) {
        return listaDados(UtilAppGasEtanol.TABELA_COMBUSTIVEL, id);
    }

    @Override
    public boolean updateObject(Combustivel obj) {

        dadosCombustivel = new ContentValues();

        dadosCombustivel.put("ID",obj.getId());
        dadosCombustivel.put("PRECO", obj.getPrecoCombustivel());
        dadosCombustivel.put("NOME", obj.getNomeCombustivel());
        dadosCombustivel.put("RECOMENDACAO", obj.getRecomendacao());

        return alterarDados(UtilAppGasEtanol.TABELA_COMBUSTIVEL, dadosCombustivel);

    }

    @Override
    public boolean deleteObject(Combustivel obj) {

        return deleteDados(UtilAppGasEtanol.TABELA_COMBUSTIVEL,obj.getId());
    }
}
