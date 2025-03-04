package br.com.dev.applistadecompras.controller;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.dev.applistadecompras.model.Produto;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmException;

public class ProdutoController implements ICRUD<Produto>{
    @Override
    public boolean insert(Produto obj) {
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
            Log.e("db_log","Erro ao incluir Produto - [ProdutoController.insert(Produto obj)] "+err.getMessage());
            return false;
        }catch(Exception err){
            Log.e("db_log","Erro genérico - [ProdutoController.insert(Produto obj)] "+err.getMessage());
            return false;
        } finally{
            realm.close();
            return true;
        }
    }

    @Override
    public List<Produto> read() {
        Realm realm = null;
        RealmResults<Produto> results= null;

        List<Produto> listaProduto = new ArrayList<>();

        try{
            realm = Realm.getDefaultInstance();

            results = realm.where(Produto.class).findAll();

            listaProduto = realm.copyFromRealm(results);

            realm.commitTransaction();

        }catch(RealmException err){
            Log.e("db_log","Erro ao deletar Produto - [ProdutoController.delete(Produto obj)] "+err.getMessage());
            return null;
        }catch(Exception err){
            Log.e("db_log","Erro genérico - [ProdutoController.delete(Produto obj)] "+err.getMessage());
            return null;
        } finally{
            realm.close();
            return listaProduto;
        }
    }

    @Override
    public boolean update(Produto obj) {
        Realm realm = null;
        Produto produto = null;

        try{
            realm = Realm.getDefaultInstance();

            produto = realm.where(Produto.class).equalTo("id", obj.getId()).findFirst();

            if(produto!= null){
                realm.beginTransaction();
                produto.setDescricao(obj.getDescricao());
                produto.setDataDeInclusao(obj.getDataDeInclusao());
                produto.setCodigoDeBarra(obj.getCodigoDeBarra());
                produto.setPreco(obj.getPreco());
                produto.setQuantidade(obj.getQuantidade());
                produto.setUn(obj.getUn());
                produto.setImagem(obj.getImagem());
                realm.commitTransaction();
            }


        }catch(RealmException err){
            Log.e("db_log","Erro ao atualizar Produto - [ProdutoController.update(Produto obj)] "+err.getMessage());
            return false;
        }catch(Exception err){
            Log.e("db_log","Erro genérico - [ProdutoController.update(Produto obj)] "+err.getMessage());
            return false;
        } finally{
            realm.close();
            return true;
        }
    }

    @Override
    public boolean delete(Produto obj) {
        Realm realm = null;
        RealmResults<Produto> results= null;

        try{
            realm = Realm.getDefaultInstance();

            results = realm.where(Produto.class).equalTo("id",obj.getId()).findAll();

            results.deleteAllFromRealm();

            realm.commitTransaction();

        }catch(RealmException err){
            Log.e("db_log","Erro ao deletar Produto - [ProdutoController.delete(Produto obj)] "+err.getMessage());
            return false;
        }catch(Exception err){
            Log.e("db_log","Erro genérico - [ProdutoController.delete(Produto obj)] "+err.getMessage());
            return false;
        } finally{
            realm.close();
            return true;
        }
    }

    @Override
    public boolean deleteByID(int id) {
        Realm realm = null;
        RealmResults<Produto> results= null;

        try{
            realm = Realm.getDefaultInstance();

            results = realm.where(Produto.class).equalTo("id",id).findAll();

            results.deleteAllFromRealm();

            realm.commitTransaction();

        }catch(RealmException err){
            Log.e("db_log","Erro ao deletar Produto por id - [ProdutoController.deleteByID(int id)] "+err.getMessage());
            return false;
        }catch(Exception err){
            Log.e("db_log","Erro genérico - [ProdutoController.deleteByID(int id)] "+err.getMessage());
            return false;
        } finally{
            realm.close();
            return true;
        }
    }
}
