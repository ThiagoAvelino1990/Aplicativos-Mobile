package br.com.dev.applistadecompras.controller;

import android.util.Log;

import java.util.Collections;
import java.util.List;

import br.com.dev.applistadecompras.model.Produto;
import br.com.dev.applistadecompras.model.Usuario;
import br.com.dev.applistadecompras.util.AppUtil;
import io.realm.Realm;
import io.realm.exceptions.RealmException;

public class UsuarioController implements ICRUD<Usuario>{
    @Override
    public boolean insert(Usuario obj) {
        Realm realm = null;
        Number primaryKey;
        final int autoIncrement;

        try{
            realm = Realm.getDefaultInstance();

            primaryKey = realm.where(Produto.class).max("id");

            if(primaryKey == null) autoIncrement = 1; else autoIncrement = primaryKey.intValue() + 1;

            obj.setId(autoIncrement);

            realm.beginTransaction();
            realm.copyToRealm(obj);
            realm.commitTransaction();

        }catch(RealmException err){
            Log.e(AppUtil.TAG,"Erro ao incluir Usuario - [UsuarioController.insert(Usuario obj)] "+err.getMessage());
            return false;
        }catch(Exception err){
            Log.e(AppUtil.TAG,"Erro genérico - [UsuarioController.insert(Usuario obj)] "+err.getMessage());
            return false;
        } finally{
            realm.close();
        }
        return true;
    }

    @Override
    public List<Usuario> read() {
        return Collections.emptyList();
    }

    @Override
    public boolean update(Usuario obj) {
        return false;
    }

    @Override
    public boolean delete(Usuario obj) {
        return false;
    }

    @Override
    public boolean deleteByID(int id) {
        return false;
    }
}
