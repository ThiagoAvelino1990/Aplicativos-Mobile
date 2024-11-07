package br.com.thiagoavelinoalves.applistacursojava.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
                "TELEFONE TEXT," +
                "ID_CURSO_PESSOA INTEGER, " +
                "FOREIGN KEY(ID_CURSO_PESSOA) REFERENCES CURSO(ID_CURSO) )";

        db.execSQL(sqlTabelaCurso);

        db.execSQL(sqlTabelaPessoa);

        /*INSERT INTO CURSO VALUES (1, 'Java');
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
}
