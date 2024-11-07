package br.com.thiagoavelinoalves.applistacursojava.model;

public class Curso {

    private int idCurso;
    private String nomeCursoDesejado;

    public Curso(String nomeCursoDesejado){
        this.nomeCursoDesejado = nomeCursoDesejado;
    }

    public int getIdCurso(){
        return this.idCurso;
    }
    public void setIdCurso(int idCurso){
        this.idCurso = idCurso;
    }

    public String getNomeCursoDesejado(){
        return this.nomeCursoDesejado;
    }

    public void setNomeCursoDesejado(String nomeCursoDesejado){
        this.nomeCursoDesejado = nomeCursoDesejado;
    }

}
