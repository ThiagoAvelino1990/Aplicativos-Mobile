package br.com.dev.applistadecompras.controller;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.dev.applistadecompras.model.Categoria;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmException;

public class CategoriaController implements ICRUD<Categoria>{
    @Override
    public boolean insert(Categoria obj) {

        Realm realm = null;
        Number primaryKey;
        final int autoIncrement;

        try{
            realm = Realm.getDefaultInstance();

            primaryKey = realm.where(Categoria.class).max("id");

            if(primaryKey == null) autoIncrement = 1; else autoIncrement = primaryKey.intValue() + 1;

            obj.setId(autoIncrement);

            realm.beginTransaction();
            realm.copyToRealm(obj);
            realm.commitTransaction();

        }catch(RealmException err){
            Log.e("db_log","Erro ao incluir Categoria - [CategoriaController.insert(Categoria obj)] "+err.getMessage());
            return false;
        }catch(Exception err){
            Log.e("db_log","Erro genérico - [CategoriaController.insert(Categoria obj)] "+err.getMessage());
            return false;
        } finally{
            realm.close();
        }
        return true;
    }

    @Override
    public List<Categoria> read() {
        Realm realm = null;
        RealmResults<Categoria> results= null;

        List<Categoria> listaCategoria = new ArrayList<>();

        try{
            realm = Realm.getDefaultInstance();
            realm.beginTransaction();

            results = realm.where(Categoria.class).findAll();

            listaCategoria = realm.copyFromRealm(results);

            realm.commitTransaction();

        }catch(RealmException err){
            Log.e("db_log","Erro ao deletar Categoria - [CategoriaController.delete(Categoria obj)] "+err.getMessage());
            return null;
        }catch(Exception err){
            Log.e("db_log","Erro genérico - [CategoriaController.delete(Categoria obj)] "+err.getMessage());
            return null;
        } finally{
            realm.close();
        }
        return listaCategoria;

    }

    @Override
    public boolean update(Categoria obj) {
        Realm realm = null;
        Categoria categoria = null;

        try{
            realm = Realm.getDefaultInstance();

            categoria = realm.where(Categoria.class).equalTo("id", obj.getId()).findFirst();

            if(categoria!= null){
                realm.beginTransaction();
                categoria.setNome(obj.getNome());
                categoria.setImagem(obj.getImagem());
                realm.commitTransaction();
            }


        }catch(RealmException err){
            Log.e("db_log","Erro ao atualizar Categoria - [CategoriaController.update(Categoria obj)] "+err.getMessage());
            return false;
        }catch(Exception err){
            Log.e("db_log","Erro genérico - [CategoriaController.update(Categoria obj)] "+err.getMessage());
            return false;
        } finally{
            realm.close();
        }
        return true;
    }

    @Override
    public boolean delete(Categoria obj) {
        Realm realm = null;
        RealmResults<Categoria> results= null;

        try{
            realm = Realm.getDefaultInstance();
            realm.beginTransaction();

            results = realm.where(Categoria.class).equalTo("id",obj.getId()).findAll();
            results.deleteAllFromRealm();

            realm.commitTransaction();

        }catch(RealmException err){
            Log.e("db_log","Erro ao deletar Categoria - [CategoriaController.delete(Categoria obj)] "+err.getMessage());
            return false;
        }catch(Exception err){
            Log.e("db_log","Erro genérico - [CategoriaController.delete(Categoria obj)] "+err.getMessage());
            return false;
        } finally{
            realm.close();
        }
        return true;
    }

    @Override
    public boolean deleteByID(int id) {
        Realm realm = null;
        RealmResults<Categoria> results= null;

        try{
            realm = Realm.getDefaultInstance();
            realm.beginTransaction();

            results = realm.where(Categoria.class).equalTo("id",id).findAll();
            results.deleteAllFromRealm();

            realm.commitTransaction();

        }catch(RealmException err){
            Log.e("db_log","Erro ao deletar Categoria por id - [CategoriaController.deleteByID(int id)] "+err.getMessage());
            return false;
        }catch(Exception err){
            Log.e("db_log","Erro genérico - [CategoriaController.deleteByID(int id)] "+err.getMessage());
            return false;
        } finally{
            realm.close();
        }
        return true;
    }

}
