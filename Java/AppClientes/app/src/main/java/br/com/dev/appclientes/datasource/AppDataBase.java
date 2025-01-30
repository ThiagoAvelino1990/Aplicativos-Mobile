package br.com.dev.appclientes.datasource;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.dev.appclientes.api.AppUtil;
import br.com.dev.appclientes.datamodel.ClienteDataModel;
import br.com.dev.appclientes.model.Cliente;

public class AppDataBase extends SQLiteOpenHelper {

    public static final String DB_NAME="CLIENTES.SQL";
    public static final int DB_VERSION = 1;

    SQLiteDatabase db;


    public AppDataBase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ClienteDataModel.criarTabela());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insertDados(String nometabela, ContentValues objeto){

        db = getWritableDatabase();

        boolean retorno = false;

        try{
            retorno = db.insert(nometabela, null, objeto) > 0;
            Log.i(AppUtil.TAG,"Dados Inseridos com sucesso: \n"+objeto.toString());
        }catch(Exception e){
            Log.e(AppUtil.TAG,"Erro ao incluir dados na tabela "+nometabela+" -"+e.getMessage());
        }


        return retorno;
    }


    public boolean deleteDados(String nometabela, int id){

        db = getWritableDatabase();

        boolean retorno = false;

        try{
            retorno = db.delete(nometabela,"ID=?",new String[]{String.valueOf(id)}) > 0;
            Log.i(AppUtil.TAG,"Dados Excluídos com sucesso");
        }catch(Exception e){
            Log.e(AppUtil.TAG,"Erro ao deletar dados na tabela "+nometabela+" -"+e.getMessage());
        }

        return retorno;

    }


    public boolean updateDados(String nometabela, ContentValues objeto){
        db=  getWritableDatabase();

        boolean retorno = false;

        try{
            retorno = db.update(nometabela,objeto,"ID=?",new String[]{String.valueOf(objeto.get("ID"))}) > 0;
            Log.i(AppUtil.TAG,"Dados Atualizados com sucesso: \n"+ objeto);
        }catch(Exception e){
            Log.e(AppUtil.TAG,"Erro ao atualizar dados na tabela "+nometabela+" -"+e.getMessage());
        }

        return retorno;
    }


    public List<Cliente> getAllClientes(String nomeTabela){

        db = getWritableDatabase();

        Cursor cursor;

        Cliente cliente;

        List<Cliente> dadosTabela = new ArrayList<>();

        String sql = "SELECT * FROM " + nomeTabela;

        cursor = db.rawQuery(sql,null);

        if(cursor.moveToFirst()){

            do{

                cliente = new Cliente();

                cliente.setId(cursor.getInt(cursor.getColumnIndexOrThrow("ID")));
                cliente.setNome(cursor.getString(cursor.getColumnIndexOrThrow("NOME")));
                cliente.setTelefone(cursor.getString(cursor.getColumnIndexOrThrow("TELEFONE")));
                cliente.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("EMAIL")));
                cliente.setCep(cursor.getInt(cursor.getColumnIndexOrThrow("CEP")));
                cliente.setLogradouro(cursor.getString(cursor.getColumnIndexOrThrow("LOGRADOURO")));
                cliente.setComplemento(cursor.getString(cursor.getColumnIndexOrThrow("COMPLEMENTO")));
                cliente.setNumero(cursor.getString(cursor.getColumnIndexOrThrow("NUMERO")));
                cliente.setBairro(cursor.getString(cursor.getColumnIndexOrThrow("BAIRRO")));
                cliente.setCidade(cursor.getString(cursor.getColumnIndexOrThrow("CIDADE")));
                cliente.setEstado(cursor.getString(cursor.getColumnIndexOrThrow("ESTADO")));
                cliente.setPais(cursor.getString(cursor.getColumnIndexOrThrow("PAIS")));

                if(cursor.getColumnIndexOrThrow("TERMOS_DE_USO") > 0){
                    cliente.setTermosDeUso(false);
                }else{
                    cliente.setTermosDeUso(true);
                }


             dadosTabela.add(cliente);

            }while(cursor.moveToNext());


        }


        return dadosTabela;
    }


}
