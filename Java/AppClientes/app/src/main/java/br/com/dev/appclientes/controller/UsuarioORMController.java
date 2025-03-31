package br.com.dev.appclientes.controller;

import android.util.Log;

import br.com.dev.appclientes.api.AppUtil;
import br.com.dev.appclientes.model.UsuarioORM;
import io.realm.Realm;
import io.realm.exceptions.RealmException;

public class UsuarioORMController {

    public void insertORM(UsuarioORM obj){

        Realm realm = null;
        Number primaryKey;
        final int autoIncrementPK;

        try{
            realm = Realm.getDefaultInstance();

            primaryKey = realm.where(UsuarioORM.class).max("id");

            autoIncrementPK = (primaryKey == null ) ? 1 : primaryKey.intValue() + 1;

            obj.setId(autoIncrementPK);

            realm.beginTransaction();
            realm.copyToRealm(obj);

        }catch(RealmException err){
            Log.e(AppUtil.TAG,"[ERRO PROGRAMADO] insertORM: "+err.getMessage());
        }catch(Exception err){
            Log.e(AppUtil.TAG,"[ERRO GENÉRICO] insertORM: "+err.getMessage());
        }finally {
            realm.commitTransaction();
            realm.close();
        }

    }


    public void updateORM(UsuarioORM obj){
        Realm realm = null;

        UsuarioORM usuarioORM = new UsuarioORM();

        try{

            realm = Realm.getDefaultInstance();

            usuarioORM = realm.where(UsuarioORM.class).equalTo("id",obj.getId()).findFirst();
            realm.beginTransaction();

            usuarioORM.setNome(obj.getNome());
            usuarioORM.setCpfCnpj(obj.getCpfCnpj());
            usuarioORM.setLogradouro(obj.getLogradouro());
            usuarioORM.setComplemento(obj.getComplemento());
            usuarioORM.setEmail(obj.getEmail());
            usuarioORM.setSenha(obj.getSenha());
            usuarioORM.setChkLembrarSenha(obj.isChkLembrarSenha());
            usuarioORM.setPessoaFisica(obj.isPessoaFisica());


        }catch(RealmException err){
            Log.e(AppUtil.TAG,"[ERRO PROGRAMADO] updateORM: "+err.getMessage());
        }catch(Exception err){
            Log.e(AppUtil.TAG,"[ERRO GENÉRICO] updateORM: "+err.getMessage());
        }finally {
            realm.commitTransaction();
            realm.close();
        }
    }

}
