package br.com.thiagoavelinoalves.applistacursojava.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.com.thiagoavelinoalves.applistacursojava.model.Curso;
import br.com.thiagoavelinoalves.applistacursojava.model.Pessoa;
import br.com.thiagoavelinoalves.applistacursojava.util.FormataDadosUtil;

public class ListaCursoDB extends SQLiteOpenHelper {

    public static final String DB_NAME = "listacurso.db";
    public static final int DB_VERSION = 1;

    Cursor cursor;

    SQLiteDatabase db;

    public ListaCursoDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sqlTabelaCurso = "CREATE TABLE CURSO(ID_CURSO INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "NOME_CURSO TEXT)";

        String sqlTabelaPessoa = "CREATE TABLE PESSOA(CPF INTEGER PRIMARY KEY, " +
                "NOME TEXT, " +
                "SOBRENOME TEXT, " +
                "TELEFONE TEXT, " +
                "EMAIL TEXT, " +
                "ID_CURSO_PESSOA INTEGER, " +
                "FOREIGN KEY(ID_CURSO_PESSOA) REFERENCES CURSO(ID_CURSO) )";

        db.execSQL(sqlTabelaCurso);

        db.execSQL(sqlTabelaPessoa);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public List<Curso> buscaDadosCurso(){

        List<Curso> dadosCurso = new ArrayList<>();

        String sqlDadosCurso = "SELECT * FROM CURSO ";

        cursor = db.rawQuery(sqlDadosCurso, null);

        if(cursor.moveToFirst()){
            do{
                Curso dados = new Curso();

                dados.setIdCurso(cursor.getInt(cursor.getColumnIndexOrThrow("ID_CURSO")));
                dados.setNomeCursoDesejado(cursor.getString(cursor.getColumnIndexOrThrow("NOME_CURSO")));

                dadosCurso.add(dados);

            }while(cursor.moveToNext());
        }
        cursor.close();
        return dadosCurso;


    }

    public List<Pessoa> buscarDadosPessoa(Long cpfPessoa){
        List<Pessoa> dadosPessoa = new ArrayList<>();

        String sqlDadosPessoa = "SELECT * FROM PESSOA WHERE CPF = " + cpfPessoa;

       cursor = db.rawQuery(sqlDadosPessoa, null);

       if(cursor.moveToFirst()){
           do{
               Pessoa dados = new Pessoa();

               dados.setCpf(cursor.getLong(cursor.getColumnIndexOrThrow("CPF")));
               dados.setNome(cursor.getString(cursor.getColumnIndexOrThrow("NOME")));
               dados.setSobrenome(cursor.getString(cursor.getColumnIndexOrThrow("SOBRENOME")));
               dados.setTelefone(cursor.getString(cursor.getColumnIndexOrThrow("TELEFONE")));
               dados.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("EMAIL")));
               dados.setIdCursoPessoa(cursor.getInt(cursor.getColumnIndexOrThrow("ID_CURSO_PESSOA")));

               dadosPessoa.add(dados);

           }while(cursor.moveToNext());
       }

        cursor.close();

        return dadosPessoa;
    }

    public void salvarDadosPessoa(String nomeTabela,
                                  ContentValues dadosTabela){

        db.insert(nomeTabela, null, dadosTabela);
    }

    public void deletarDadosPessoa(String nomeTabela,
                                   int cpfPessoa){

        db.delete(nomeTabela,"CPF=?",new String[]{Integer.toString(cpfPessoa)});
    }


    public boolean insert(String nomeTabela, ContentValues objeto){

        db = getWritableDatabase();
        boolean retorno = false;

        try{
            retorno = db.insert(nomeTabela, null, objeto) > 0;
        }catch(Exception err){
            Log.d(FormataDadosUtil.TAG, "Erro ao inserir os dados " + err.getMessage());
        }
        return retorno;
    }

    public boolean deleteById(String nomeTabela, Long id){

        db = getWritableDatabase();

        boolean retorno = false;

        if(nomeTabela == "PESSOA"){
            try{
                retorno = db.delete(nomeTabela, "CPF = ? ", new String[]{String.valueOf(id)}) > 0;
            }catch (Exception err){
                Log.e(FormataDadosUtil.TAG, "Erro ao deletar os dados da tabela "+ nomeTabela + err.getMessage());
            }
        }else{
            try{
                retorno = db.delete(nomeTabela, "ID_CURSO = ? ", new String[]{String.valueOf(id)}) > 0;
            }catch (Exception err){
                Log.e(FormataDadosUtil.TAG, "Erro ao deletar os dados da tabela "+ nomeTabela + err.getMessage());
            }
        }

        return retorno;
    }

    public boolean update(String nomeTabela, ContentValues dadosObjeto){

        db = getWritableDatabase();

        boolean retorno = false;

        if(nomeTabela == "PESSOA"){
            try{
                retorno = db.update(nomeTabela,dadosObjeto,"CPF = ?", new String[]{String.valueOf(dadosObjeto.get("CPF"))}) > 0;
            }catch (Exception err){
                Log.e(FormataDadosUtil.TAG, "Erro ao deletar os dados da tabela "+ nomeTabela + err.getMessage());
            }
        }else{
            try{
                retorno = db.update(nomeTabela, dadosObjeto,"ID_CURSO = ?", new String[]{String.valueOf(dadosObjeto.get("ID_CURSO"))}) > 0;
            }catch (Exception err){
                Log.e(FormataDadosUtil.TAG, "Erro ao deletar os dados da tabela "+ nomeTabela + err.getMessage());
            }
        }

        return retorno;
    }

    public List<Curso> readCurso(String nomeTabela){

        db = getWritableDatabase();

        Cursor cursor;

        List<Curso> cursos = new ArrayList<>();

        cursor = db.rawQuery("SELECT * FROM "+ nomeTabela, null);
        if(cursor.moveToFirst()){
            do{
                Curso curso = new Curso();

                curso.setIdCurso(cursor.getInt(cursor.getColumnIndexOrThrow("ID_CURSO")));
                curso.setNomeCursoDesejado(cursor.getString(cursor.getColumnIndexOrThrow("NOME_CURSO")));

                cursos.add(curso);

            }while(cursor.moveToNext());
        }


    return cursos;

    }

}
