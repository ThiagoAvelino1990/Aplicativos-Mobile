package br.com.thiagoavelinoalves.applistacursojava.controller;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.List;

import br.com.thiagoavelinoalves.applistacursojava.database.ListaCursoDB;
import br.com.thiagoavelinoalves.applistacursojava.model.Pessoa;
import br.com.thiagoavelinoalves.applistacursojava.view.MainActivity;

public class PessoaControler extends ListaCursoDB{

    public static final String tabelaPessoa = "PESSOA";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor listavip;
    public static final String NOME_PREFERENCES = "pref_listavip";

    public PessoaControler(Context context){
        super(context);
        sharedPreferences = context.getSharedPreferences(NOME_PREFERENCES,0);
        listavip = sharedPreferences.edit();
    }

    public List<Pessoa> buscar(Pessoa pessoa){

        pessoa.setNome(sharedPreferences.getString("PrimeiroNome",""));
        pessoa.setSobrenome(sharedPreferences.getString("Sobrenome",""));
        pessoa.setTelefone(sharedPreferences.getString("Telefone",""));

        return buscarDadosPessoa(pessoa.getCpf());
    }

    public void salvar(Pessoa pessoa, Context context){

        ContentValues contentValuesPessoa = new ContentValues();

        listavip.putString("CPF", Integer.toString(pessoa.getCpf()));
        listavip.putString("PrimeiroNome",pessoa.getNome());
        listavip.putString("Sobrenome",pessoa.getSobrenome());
        listavip.putString("Telefone",pessoa.getTelefone());
        listavip.putString("idCurso", Integer.toString(pessoa.getIdCursoPessoa()));
        listavip.apply();

        contentValuesPessoa.put("CPF", pessoa.getCpf());
        contentValuesPessoa.put("NOME", pessoa.getNome());
        contentValuesPessoa.put("SOBRENOME", pessoa.getSobrenome());
        contentValuesPessoa.put("TELEFONE", pessoa.getTelefone());
        contentValuesPessoa.put("ID_CURSO_PESSOA", pessoa.getIdCursoPessoa());

        salvarDadosPessoa(tabelaPessoa, contentValuesPessoa);

        Toast.makeText(context,"Dados Salvos Com sucesso",Toast.LENGTH_LONG).show();

    }

    public void limpar(Context context){


        listavip.clear();
        listavip.apply();

        Toast.makeText(context,"Dados apagados com sucesso",Toast.LENGTH_LONG).show();

    }

}
