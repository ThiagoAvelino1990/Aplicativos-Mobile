package br.com.dev.appclientes.api;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class AppUtilSharedPreferences {

    public static final String PREF_APP = "app_clientes_pref";
    public static SharedPreferences pref;

    public static int getIdUserByPref(Context contexto){
        int idUsuario;
        pref = contexto.getSharedPreferences(PREF_APP, MODE_PRIVATE);

        try{
            idUsuario = Integer.parseInt(pref.getString("idUsuario", String.valueOf(-1)));
            return idUsuario;
        }catch (ClassCastException err){
            Log.e(AppUtil.TAG,"Erro ClassCast - [AppUtilSharedPreferences - getIdUserByPref] "+err.getMessage());
        }catch (NullPointerException err){
            Log.e(AppUtil.TAG,"Erro NullPonter - [AppUtilSharedPreferences - getIdUserByPref] "+err.getMessage());
        }catch(Exception err){
            Log.e(AppUtil.TAG,"Erro geral - [AppUtilSharedPreferences - getIdUserByPref] "+err.getMessage());
        }

        return -1;

    }

}
