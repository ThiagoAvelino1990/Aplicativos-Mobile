package br.com.thiagoavelinoalves.applistacursojava.controller;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import br.com.thiagoavelinoalves.applistacursojava.database.ListaCursoDB;
import br.com.thiagoavelinoalves.applistacursojava.model.Curso;

public class CursoController extends ListaCursoDB implements CRUDInterface{

    private List listaDeCursos;

    public CursoController(Context context){
        super(context);
    }

    public List getListaDeCursos() {

        this.listaDeCursos = new ArrayList<Curso>();

       /* listaDeCursos.add(new Curso(""));
        listaDeCursos.add(new Curso("Java"));
        listaDeCursos.add(new Curso("Kotlin"));
        listaDeCursos.add(new Curso("PL/SQL"));
        listaDeCursos.add(new Curso("SQL SERVER"));
        listaDeCursos.add(new Curso("GO Lang"));
        listaDeCursos.add(new Curso("C#"));
        listaDeCursos.add(new Curso("Front End"));*/

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

    @Override
    public void createObject(Object obj) {
        
    }

    @Override
    public void deleteObject(Object obj) {

    }

    @Override
    public void updateObject(Object obj) {

    }

    @Override
    public void readObject(Object obj) {

    }
}
