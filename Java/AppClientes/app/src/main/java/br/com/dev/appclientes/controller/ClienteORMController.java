package br.com.dev.appclientes.controller;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.dev.appclientes.api.AppUtil;
import br.com.dev.appclientes.model.ClienteORM;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmException;

public class ClienteORMController {

    public void insertORM(ClienteORM obj){

        Realm realm = null;
        Number primaryKey;
        final int autoIncrementPK;

        try{
            realm = Realm.getDefaultInstance();

            primaryKey = realm.where(ClienteORM.class).max("id");

            autoIncrementPK = (primaryKey == null ) ? 1 : primaryKey.intValue() + 1;

            obj.setId(autoIncrementPK);

            realm.beginTransaction();
            realm.copyToRealm(obj);

        }catch(RealmException err){
            Log.e(AppUtil.TAG,"insertORM: "+err.getMessage());
        }finally {
            realm.commitTransaction();
            realm.close();
        }

    }

    public void updateORM(ClienteORM obj){
        Realm realm = null;
        ClienteORM clienteORM;
        try{
            realm = Realm.getDefaultInstance();

            clienteORM = realm.where(ClienteORM.class).equalTo("id", obj.getId()).findFirst();

            realm.beginTransaction();

            clienteORM.setNome(obj.getNome());
            clienteORM.setTelefone(obj.getTelefone());
            clienteORM.setEmail(obj.getEmail());
            clienteORM.setCep(obj.getCep());
            clienteORM.setLogradouro(obj.getLogradouro());
            clienteORM.setComplemento(obj.getComplemento());
            clienteORM.setNumero(obj.getNumero());
            clienteORM.setBairro(obj.getBairro());
            clienteORM.setCidade(obj.getCidade());
            clienteORM.setEstado(obj.getEstado());
            clienteORM.setPais(obj.getPais());
            clienteORM.setTermosDeUso(obj.isTermosDeUso());
            clienteORM.setDataDeAtualizacao(obj.getDataDeAtualizacao());

        }catch(RealmException err){
            Log.e(AppUtil.TAG,"updateORM: "+err.getMessage());
        }finally {
            realm.commitTransaction();
            realm.close();
        }
    }


    public void deleteORM(ClienteORM obj){

        Realm realm = null;
        try{

            realm = Realm.getDefaultInstance();

            realm.beginTransaction();

            RealmResults<ClienteORM> results = realm.where(ClienteORM.class).equalTo("id",obj.getId()).findAll();

            results.deleteAllFromRealm();

        }catch(RealmException err){
            Log.e(AppUtil.TAG,"deleteORM: "+err.getMessage());
        }finally {
            realm.commitTransaction();
            realm.close();
        }
    }

    public void deleteORMByID(int id){

        Realm realm = null;

        RealmResults<ClienteORM> results = realm.where(ClienteORM.class).equalTo("id",id).findAll();

        try{
            realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            results.deleteAllFromRealm();
        }catch(RealmException err){
            Log.e(AppUtil.TAG,"deleteORMByID: "+err.getMessage());
        }finally {
            realm.commitTransaction();
            realm.close();
        }
    }


    public List<ClienteORM> listarORM(){

        Realm realm = null;

        RealmResults<ClienteORM> results = null;

        List<ClienteORM> ListORM = new ArrayList<>();

        try{
            realm = Realm.getDefaultInstance();

            results = realm.where(ClienteORM.class).findAll();

            ListORM = realm.copyFromRealm(results);

        }catch(RealmException err){
            Log.e(AppUtil.TAG,"listarORM: "+err.getMessage());
        }finally {
            realm.close();
        }

        return ListORM;
    }

    public ClienteORM listarORMByID(int id){

        Realm realm = null;

        ClienteORM obj = null;

        try{
            realm = Realm.getDefaultInstance();

            obj = realm.copyFromRealm(Objects.requireNonNull(realm.where(ClienteORM.class)).equalTo("id",id).findFirst());

        }catch(Exception err){
            Log.e(AppUtil.TAG,"listarORMByID: "+err.getMessage());
        }finally {
            realm.close();
        }

        return obj;
    }


}
