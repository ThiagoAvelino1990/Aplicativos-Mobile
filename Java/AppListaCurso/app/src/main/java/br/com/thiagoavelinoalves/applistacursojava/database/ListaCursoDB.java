package br.com.thiagoavelinoalves.applistacursojava.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.thiagoavelinoalves.applistacursojava.model.Curso;
import br.com.thiagoavelinoalves.applistacursojava.model.Pessoa;

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

        /*INSERT INTO CURSO VALUES (0, '');
        INSERT INTO CURSO VALUES (1, 'Java');
        INSERT INTO CURSO VALUES (2, 'Kotlin');
        INSERT INTO CURSO VALUES (3, 'PL/SQL');
        INSERT INTO CURSO VALUES (4, 'SQL SERVER');
        INSERT INTO CURSO VALUES (5, 'GO Lang');
        INSERT INTO CURSO VALUES (6, 'C#');
        INSERT INTO CURSO VALUES (7, 'Front End');*/
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
}
