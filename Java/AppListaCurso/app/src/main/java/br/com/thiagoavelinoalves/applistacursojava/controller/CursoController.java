package br.com.thiagoavelinoalves.applistacursojava.controller;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.thiagoavelinoalves.applistacursojava.database.ListaCursoDB;
import br.com.thiagoavelinoalves.applistacursojava.model.Curso;
import br.com.thiagoavelinoalves.applistacursojava.util.FormataDadosUtil;

public class CursoController extends ListaCursoDB implements CRUDInterface<Curso>{

    private List listaDeCursos;

    ContentValues dadosCurso;

    public static final String TABELA_CURSO = "CURSO";

    public CursoController(Context context){
        super(context);
    }

    public List getListaDeCursos() {

        this.listaDeCursos = new ArrayList<Curso>();

        listaDeCursos = buscaDadosCurso();

        return this.listaDeCursos;
    }

    public List<String> retornarDadosSpinner(){

        ArrayList<String> dadosSpinner = new ArrayList<>();


        for(int i = 0; i< getListaDeCursos().size();i++){
            Curso dadosCurso = (Curso) getListaDeCursos().get(i);

            dadosSpinner.add(dadosCurso.getNomeCursoDesejado());
        }

        return dadosSpinner;
    }

    public void salvarDadosCurso(){
        Curso curso = new Curso();
        List<String> listaCursosCadastrados = new ArrayList<>();

        listaCursosCadastrados.add("");
        listaCursosCadastrados.add("Java");
        listaCursosCadastrados.add("Kotlin");
        listaCursosCadastrados.add("PL/SQL");
        listaCursosCadastrados.add("SQL SERVER");
        listaCursosCadastrados.add("GO Lang");
        listaCursosCadastrados.add("C#");
        listaCursosCadastrados.add("Front End");

        for (String listaCurso: listaCursosCadastrados) {
            curso.setNomeCursoDesejado(listaCurso);
            createObject(curso);
        }

    }

    @Override
    public boolean createObject(Curso curso) {
        dadosCurso = new ContentValues();

        dadosCurso.put("NOME_CURSO", curso.getNomeCursoDesejado());

        Log.d(FormataDadosUtil.TAG,"CursoController - createObject()");

        return insert(TABELA_CURSO, dadosCurso);
    }

    @Override
    public boolean deleteObject(Curso curso) {

        dadosCurso = new ContentValues();

        dadosCurso.put("ID_CURSO",curso.getIdCurso());


        return deleteById(TABELA_CURSO, Long.parseLong(String.valueOf(curso.getIdCurso())));

    }

    @Override
    public boolean updateObject(Curso curso) {

        dadosCurso = new ContentValues();

        dadosCurso.put("ID_CURSO", curso.getIdCurso());
        dadosCurso.put("NOME_CURSO", curso.getNomeCursoDesejado());

        return true;
    }

    @Override
    public List<Curso> readObject() {

        return readCurso(TABELA_CURSO);
    }
}
