package br.com.dev.appclientes.controller;

import android.content.ContentValues;
import android.content.Context;

import java.util.Collections;
import java.util.List;

import br.com.dev.appclientes.api.AppUtil;
import br.com.dev.appclientes.datamodel.UsuarioDataModel;
import br.com.dev.appclientes.datasource.AppDataBase;
import br.com.dev.appclientes.model.Usuario;

public class UsuarioController extends AppDataBase implements ICRUD<Usuario> {

    ContentValues values;

    public UsuarioController(Context context) {
        super(context);
    }

    @Override
    public boolean insertObject(Usuario obj) {

        values = new ContentValues();

        if(obj.isPessoaFisica()){
            values.put(UsuarioDataModel.IDTIPOPESSOA, "PJ");
        }else{
            values.put(UsuarioDataModel.IDTIPOPESSOA, "PF");
        }
        values.put(UsuarioDataModel.NOME,obj.getNome());
        values.put(UsuarioDataModel.CPFCNPJ, obj.getCpfCnpj());
        values.put(UsuarioDataModel.LOGRADOURO, obj.getLogradouro());
        values.put(UsuarioDataModel.COMPLEMENTO, obj.getComplemento());
        values.put(UsuarioDataModel.EMAIL, obj.getEmail());
        values.put(UsuarioDataModel.SENHA, obj.getSenha());

        if(obj.isChkLembrarSenha()) {
            values.put(UsuarioDataModel.LEMBRARSENHA, 1);
        }else{
            values.put(UsuarioDataModel.LEMBRARSENHA, 0);
        }


        return insertDados(UsuarioDataModel.TABELA, values);
    }

    @Override
    public boolean deleteObject(Usuario obj) {

        return deleteDados(UsuarioDataModel.TABELA, obj.getId());
    }

    @Override
    public boolean updateObject(Usuario obj) {

        values = new ContentValues();

        values.put(UsuarioDataModel.SENHA, obj.getSenha());
        values.put(UsuarioDataModel.LEMBRARSENHA, obj.isChkLembrarSenha());

        return updateDados(UsuarioDataModel.TABELA, values);
    }

    @Override
    public List<Usuario> readObject() {
        return getAllUsuarios(UsuarioDataModel.TABELA);
    }

    @Override
    public List<Usuario> readObjectById(int id) {
        return getUsuarioByID(UsuarioDataModel.TABELA, id);
    }

    public List<Usuario> readObjectByEmail(String nomeTabela, String email){

        return getUsuarioByEmail(nomeTabela, email);
    }

}
