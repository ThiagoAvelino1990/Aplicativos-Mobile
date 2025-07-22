package br.com.dev.appclientes.datasource;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.com.dev.appclientes.api.AppUtil;
import br.com.dev.appclientes.datamodel.ClienteDataModel;
import br.com.dev.appclientes.datamodel.UsuarioDataModel;
import br.com.dev.appclientes.model.Cliente;
import br.com.dev.appclientes.model.Usuario;

/**
 * TODO:Revisar a classe do SQLLITE em relação aos retornos dos métodos e Log's
 */
public class AppDataBase extends SQLiteOpenHelper {

    public static final String DB_NAME="CLIENTES.SQL";
    public static final int DB_VERSION = 1;

    Context context;

    SQLiteDatabase db;

    SharedPreferences prefs;


    public AppDataBase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;

        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL(ClienteDataModel.criarTabela());
        }catch(SQLException sqlException){
            Log.e(AppUtil.TAG,"["+ClienteDataModel.TABELA+"] "+sqlException.getMessage());
        }

        try{
            db.execSQL(UsuarioDataModel.criarTabela());
        }catch(SQLException sqlException){
            Log.e(AppUtil.TAG,"["+UsuarioDataModel.TABELA+"] "+sqlException.getMessage());
        }

        try{
            db.execSQL("PRAGMA foreign_keys = ON;");
        }catch(SQLException err){
            Log.e(AppUtil.TAG,"[PRAGMA foreing_keys] "+err.getMessage());
        }

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
        }catch(SQLException e){
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
        }catch(SQLException e){
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
        }catch(SQLException e){
            Log.e(AppUtil.TAG,"Erro ao atualizar dados na tabela "+nometabela+" -"+e.getMessage());
        }

        return retorno;
    }

    /*--------------- Dados Cliente ---------------*/
    public List<Cliente> getAllClientes(String nomeTabela){

        db = getWritableDatabase();

        Cursor cursor;

        Cliente cliente;

        List<Cliente> dadosTabela = new ArrayList<>();

        String sql = "SELECT * FROM " + nomeTabela;

        try{
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

                Log.i(AppUtil.TAG,"Dados listados com sucesso [CLIENTE]");

            }
        }catch(SQLException e){
            Log.e(AppUtil.TAG,"Erro ao listar os dados. [CLIENTE] "+e.getMessage());
        }



        return dadosTabela;
    }


    public List<Cliente> getClienteById(String nomeTabela, int id){

        db = getWritableDatabase();

        Cursor cursor;

        Cliente cliente;

        List<Cliente> dadosTabela = new ArrayList<>();

        try{
            cursor = db.rawQuery("SELECT * FROM "+nomeTabela+"WHERE ID = ?",new String[]{String.valueOf(id)});

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
        }catch(SQLException e){
            Log.e(AppUtil.TAG,"Erro ao listar os dados por ID [CLIENTE] "+e.getMessage());
        }

        return dadosTabela;
    }

    /**
     *
     * @param nomeTabela = CLIENTE
     * @param id = IDUSUARIO
     * @return retorna a lista de clientes do usuário logado no app
     */
    public ArrayList<Cliente> getClientesByIdUsuario(String nomeTabela, int id){

        db = getWritableDatabase();

        Cursor cursor;

        Cliente cliente;

        ArrayList<Cliente> dadosCliente = new ArrayList<>();

        try{
            cursor = db.rawQuery("SELECT * FROM "+nomeTabela+"WHERE IDUSUARIO = ?",new String[]{String.valueOf(id)});

            if(cursor.moveToFirst()){

                do{

                    cliente = new Cliente();

                    cliente.setId(cursor.getInt(cursor.getColumnIndexOrThrow("ID")));
                    cliente.setNome(cursor.getString(cursor.getColumnIndexOrThrow("NOME")));

                    //TODO:AJUSTAR PARA BUSCAR MAIS QUANDO CORRIGIR O LISTVIEW
                    /*cliente.setTelefone(cursor.getString(cursor.getColumnIndexOrThrow("TELEFONE")));
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
                    */

                    dadosCliente.add(cliente);

                }while(cursor.moveToNext());

            }
        }catch(SQLException e){
            Log.e(AppUtil.TAG,"Erro ao listar os dados por IDUSUARIO [AppDataBase - getClientesByIdUsuario] "+e.getMessage());
        }

        return dadosCliente;
    }


    /*--------------- Dados Usuario ---------------*/

    public List<Usuario> getUsuarioByEmail(String nomeTabela, String email){
        db = getWritableDatabase();

        Cursor cursor;

        Usuario usuario;

        List<Usuario> usuarioList = new ArrayList<>();

        try{
            cursor = db.rawQuery("SELECT * FROM "+nomeTabela+" WHERE EMAIL = ?",new String[]{email});

            if(cursor.moveToFirst()){

                do{

                    usuario = new Usuario();

                    usuario.setId(cursor.getInt(cursor.getColumnIndexOrThrow("ID")));
                    usuario.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("EMAIL")));
                    usuario.setNome(cursor.getString(cursor.getColumnIndexOrThrow("NOME")));
                    usuario.setSenha(cursor.getString(cursor.getColumnIndexOrThrow("SENHA")));

                    if(cursor.getColumnIndexOrThrow("LEMBRAR_SENHA") > 0){
                        usuario.setChkLembrarSenha(false);
                    }else{
                        usuario.setChkLembrarSenha(true);
                    }


                    usuarioList.add(usuario);

                }while(cursor.moveToNext());

            }
            Log.i(UsuarioDataModel.TABELA,"Dados encontrados com sucesso [USUARIO]");
        }catch(SQLException e){
            Log.e(UsuarioDataModel.TABELA,"Erro ao listar usuario por email [USUARIO] "+e.getMessage());
        }



        return usuarioList;
    }

    public int getIdUsuarioByTelefone(String nomeTabela, String telefone){
        db = getWritableDatabase();

        Cursor cursor;

        int idUsuario = -1;

        try{
            cursor = db.rawQuery("SELECT ID FROM "+nomeTabela+" WHERE TELEFONE = ?",new String[]{telefone});

            if(cursor.moveToFirst()){

                do{
                    idUsuario = cursor.getInt(cursor.getColumnIndexOrThrow("ID"));
                }while(cursor.moveToNext());

            }
            Log.i(UsuarioDataModel.TABELA,"Dados encontrados com sucesso [getIdUsuarioByTelefone]");
        }catch(SQLException e){
            Log.e(UsuarioDataModel.TABELA,"Erro ao listar getIdUsuarioByTelefone [getIdUsuarioByTelefone] "+e.getMessage());
        }


        return idUsuario;
}

    public List<Usuario> getUsuarioByID(String nomeTabela, int id){
        db = getWritableDatabase();

        Cursor cursor;

        Usuario usuario;

        List<Usuario> usuarioList = new ArrayList<>();

        try{
            cursor = db.rawQuery("SELECT * FROM "+nomeTabela+" WHERE ID = ?",new String[]{String.valueOf(id)});

            if(cursor.moveToFirst()){

                do{

                    usuario = new Usuario();

                    usuario.setId(cursor.getInt(cursor.getColumnIndexOrThrow("ID")));
                    usuario.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("EMAIL")));
                    usuario.setNome(cursor.getString(cursor.getColumnIndexOrThrow("NOME")));
                    usuario.setSenha(cursor.getString(cursor.getColumnIndexOrThrow("SENHA")));

                    if(cursor.getColumnIndexOrThrow("LEMBRAR_SENHA") > 0){
                        usuario.setChkLembrarSenha(false);
                    }else{
                        usuario.setChkLembrarSenha(true);
                    }

                    usuario.setAtualizaSenha(cursor.getString(cursor.getColumnIndexOrThrow("ATUALIZAR_SENHA")));


                    usuarioList.add(usuario);

                }while(cursor.moveToNext());

                Log.i(UsuarioDataModel.TABELA,"Dados encontrados com sucesso [USUARIO]");
            }

        }catch(SQLException e){
            Log.e(UsuarioDataModel.TABELA,"Erro ao listar usuario por ID [USUARIO] "+e.getMessage());
        }



        return usuarioList;
    }

    public List<Usuario> getAllUsuarios(String nomeTabela){
        db = getWritableDatabase();

        Cursor cursor;

        Usuario usuario;

        List<Usuario> usuarioList = new ArrayList<>();

        try{
            cursor = db.rawQuery("SELECT * FROM " + nomeTabela,null);

            if(cursor.moveToFirst()){

                do{

                    usuario = new Usuario();

                    usuario.setId(cursor.getInt(cursor.getColumnIndexOrThrow("ID")));
                    usuario.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("EMAIL")));
                    usuario.setNome(cursor.getString(cursor.getColumnIndexOrThrow("NOME")));
                    usuario.setSenha(cursor.getString(cursor.getColumnIndexOrThrow("SENHA")));

                    if(cursor.getColumnIndexOrThrow("LEMBRAR_SENHA") > 0){
                        usuario.setChkLembrarSenha(false);
                    }else{
                        usuario.setChkLembrarSenha(true);
                    }


                    usuarioList.add(usuario);

                }while(cursor.moveToNext());

            }
            Log.i(UsuarioDataModel.TABELA,"Dados encontrados com sucesso [USUARIO]");
        }catch(SQLException e){
            Log.e(UsuarioDataModel.TABELA,"Erro ao listar getAllUsuarios [USUARIO] "+e.getMessage());
        }



        return usuarioList;
    }



    public int getIdUsuarioByEmail(String nomeTabela, String email){
        db = getWritableDatabase();

        Cursor cursor;

        int idUsuario = -1;

        try{
            cursor = db.rawQuery("SELECT ID FROM "+nomeTabela+" WHERE EMAIL = ?",new String[]{email});

            if(cursor.moveToFirst()){

                do{
                    idUsuario = cursor.getInt(cursor.getColumnIndexOrThrow("ID"));
                }while(cursor.moveToNext());

            }
            Log.i(UsuarioDataModel.TABELA,"Dados encontrados com sucesso [getIdUsuarioByEmail]");
        }catch(SQLException e){
            Log.e(UsuarioDataModel.TABELA,"Erro ao listar getIdUsuarioByEmail [getIdUsuarioByEmail] "+e.getMessage());
        }


        return idUsuario;
    }

    public void dropTable(String nomeTabela){
        try{
            db.execSQL("DROP TABLE IF EXISTS "+nomeTabela);
        }catch(SQLException err){
            Log.e(AppUtil.TAG,"Erro SQL ao dropar a tabela. "+err.getMessage());
        }catch(Exception err){
            Log.e(AppUtil.TAG,"Erro ao dropar a tabela. "+err.getMessage());
        }

    }

    public void createTabela(String ddlTabela){
        try{
            db.execSQL(ddlTabela);
        }catch(SQLException err){
            Log.e(AppUtil.TAG,"Erro SQL ao criar a tabela. "+err.getMessage());
        }catch(Exception err){
            Log.e(AppUtil.TAG,"Erro ao criar a tabela. "+err.getMessage());
        }
    }

}
