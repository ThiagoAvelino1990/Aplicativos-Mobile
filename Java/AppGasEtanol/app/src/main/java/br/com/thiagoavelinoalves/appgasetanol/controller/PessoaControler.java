package br.com.thiagoavelinoalves.appgasetanol.controller;

import android.content.SharedPreferences;

import br.com.thiagoavelinoalves.appgasetanol.model.Pessoa;
import br.com.thiagoavelinoalves.appgasetanol.view.MainActivity;

public class PessoaControler {


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor listavip;
    public static final String NOME_PREFERENCES = "pref_listavip";

    public PessoaControler(MainActivity mainActivity){
        sharedPreferences = mainActivity.getSharedPreferences(NOME_PREFERENCES,0);
        listavip = sharedPreferences.edit();
    }

    public Pessoa buscar(Pessoa pessoa){

        pessoa.setNome(sharedPreferences.getString("PrimeiroNome",""));
        pessoa.setSobrenome(sharedPreferences.getString("Sobrenome",""));
        pessoa.setTelefone(sharedPreferences.getString("Telefone",""));

        return pessoa;
    }

    public void salvar(Pessoa pessoa){

        listavip.putString("PrimeiroNome",pessoa.getNome());
        listavip.putString("Sobrenome",pessoa.getSobrenome());
        listavip.putString("Telefone",pessoa.getTelefone());
        listavip.apply();

    }

    public void limpar(){

        listavip.clear();
        listavip.apply();
    }

}
