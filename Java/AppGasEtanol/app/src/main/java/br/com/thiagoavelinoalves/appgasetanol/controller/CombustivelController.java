package br.com.thiagoavelinoalves.appgasetanol.controller;

import android.content.SharedPreferences;

import br.com.thiagoavelinoalves.appgasetanol.model.Combustivel;
import br.com.thiagoavelinoalves.appgasetanol.view.GasEtanolActivity;

public class CombustivelController {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor combustivelRecomendado;
    public static final String NOME_PREFERENCES = "pref_combustivel";

    public CombustivelController(GasEtanolActivity gasEtanolActivity){
        sharedPreferences = gasEtanolActivity.getSharedPreferences(NOME_PREFERENCES,0);
        combustivelRecomendado = sharedPreferences.edit();
    }

    public void salvar(Combustivel combustivel){
        combustivelRecomendado.putString("NomeCombustivel", combustivel.getNomeCombustivel());
        combustivelRecomendado.putString("PrecoCombustivel", String.valueOf(combustivel.getPrecoCombustivel()));
        combustivelRecomendado.putString("Recomendacao",combustivel.getRecomendacao());

        combustivelRecomendado.apply();
    }

    public void limpar(){
        combustivelRecomendado.clear();
        combustivelRecomendado.apply();
    }

}
