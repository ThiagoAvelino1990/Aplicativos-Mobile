package br.com.thiagoavelinoalves.applistacursojava.controller;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import br.com.thiagoavelinoalves.applistacursojava.database.ListaCursoDB;
import br.com.thiagoavelinoalves.applistacursojava.model.Pessoa;
import br.com.thiagoavelinoalves.applistacursojava.util.FormataDadosUtil;
import br.com.thiagoavelinoalves.applistacursojava.view.MainActivity;

public class PessoaControler extends ListaCursoDB implements CRUDInterface<Pessoa>{

    public static final String tabelaPessoa = "PESSOA";

    ContentValues dadosObjetoPessoa;

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
        pessoa.setEmail(sharedPreferences.getString("Email",""));
        pessoa.setCpf(Long.parseLong(sharedPreferences.getString("CPF",String.valueOf(0))));

        Log.d(FormataDadosUtil.TAG," PessoaControler - Método buscar()");
        return buscarDadosPessoa(pessoa.getCpf());
    }

    public void salvar(Pessoa pessoa, Context context){

        ContentValues contentValuesPessoa = new ContentValues();

        listavip.putString("CPF", String.valueOf(pessoa.getCpf()));
        listavip.putString("PrimeiroNome",pessoa.getNome());
        listavip.putString("Sobrenome",pessoa.getSobrenome());
        listavip.putString("Telefone",pessoa.getTelefone());
        listavip.putString("Email", pessoa.getEmail());
        listavip.putString("idCurso", Integer.toString(pessoa.getIdCursoPessoa()));
        listavip.apply();

        contentValuesPessoa.put("CPF", pessoa.getCpf());
        contentValuesPessoa.put("NOME", pessoa.getNome());
        contentValuesPessoa.put("SOBRENOME", pessoa.getSobrenome());
        contentValuesPessoa.put("TELEFONE", pessoa.getTelefone());
        contentValuesPessoa.put("EMAIL", pessoa.getEmail());
        contentValuesPessoa.put("ID_CURSO_PESSOA", pessoa.getIdCursoPessoa());

        salvarDadosPessoa(tabelaPessoa, contentValuesPessoa);

        Log.d(FormataDadosUtil.TAG," PessoaControler - Método salvar()");
        Toast.makeText(context,"Dados Salvos Com sucesso",Toast.LENGTH_LONG).show();

    }

    public void limpar(Context context){


        listavip.clear();
        listavip.apply();

        Log.d(FormataDadosUtil.TAG," PessoaControler - Método limpar()");
        Toast.makeText(context,"Dados apagados com sucesso",Toast.LENGTH_LONG).show();

    }


    @Override
    public boolean createObject(Pessoa pessoa) {

        dadosObjetoPessoa = new ContentValues();

        dadosObjetoPessoa.put("CPF", pessoa.getCpf());
        dadosObjetoPessoa.put("NOME", pessoa.getNome());
        dadosObjetoPessoa.put("SOBRENOME", pessoa.getSobrenome());
        dadosObjetoPessoa.put("TELEFONE", pessoa.getTelefone());
        dadosObjetoPessoa.put("EMAIL", pessoa.getEmail());
        dadosObjetoPessoa.put("IDCURSOPESSOA", pessoa.getIdCursoPessoa());

        return insert(tabelaPessoa, dadosObjetoPessoa);
    }

    @Override
    public boolean deleteObject(Pessoa pessoa) {

        dadosObjetoPessoa = new ContentValues();

        dadosObjetoPessoa.put("CPF", pessoa.getCpf());
        return false;
    }

    @Override
    public boolean updateObject(Pessoa pessoa) {

        dadosObjetoPessoa = new ContentValues();

        dadosObjetoPessoa.put("CPF", pessoa.getCpf());
        dadosObjetoPessoa.put("NOME", pessoa.getNome());
        dadosObjetoPessoa.put("SOBRENOME", pessoa.getSobrenome());
        dadosObjetoPessoa.put("TELEFONE", pessoa.getTelefone());
        dadosObjetoPessoa.put("EMAIL", pessoa.getEmail());
        dadosObjetoPessoa.put("IDCURSOPESSOA", pessoa.getIdCursoPessoa());

        return false;
    }

    @Override
    public List<Pessoa> readObject() {
        return null;
    }
}
