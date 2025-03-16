package br.com.dev.appclientes.controller;

import android.content.ContentValues;
import android.content.Context;

import java.util.Collections;
import java.util.List;

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

        values.put(UsuarioDataModel.NOME,obj.getNome());
        values.put(UsuarioDataModel.EMAIL, obj.getEmail());
        values.put(UsuarioDataModel.SENHA, obj.getSenha());

        return insertDados(UsuarioDataModel.TABELA, values);
    }

    @Override
    public boolean deleteObject(Usuario obj) {
        return false;
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
        return Collections.emptyList();
    }
}
