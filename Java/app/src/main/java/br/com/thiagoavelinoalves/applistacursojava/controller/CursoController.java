package br.com.thiagoavelinoalves.applistacursojava.controller;

import java.util.ArrayList;
import java.util.List;

import br.com.thiagoavelinoalves.applistacursojava.model.Curso;

public class CursoController {

    private List listaDeCursos;

    public List getListaDeCursos() {

        this.listaDeCursos = new ArrayList<Curso>();

        listaDeCursos.add(new Curso("Java"));
        listaDeCursos.add(new Curso("Kotlin"));
        listaDeCursos.add(new Curso("PL/SQL"));
        listaDeCursos.add(new Curso("SQL SERVER"));
        listaDeCursos.add(new Curso("GO Lang"));
        listaDeCursos.add(new Curso("C#"));
        listaDeCursos.add(new Curso("Front End"));

        return this.listaDeCursos;
    }
}
