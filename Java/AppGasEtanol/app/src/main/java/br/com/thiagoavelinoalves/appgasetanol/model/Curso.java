package br.com.thiagoavelinoalves.appgasetanol.model;

public class Curso {

    private String nomeCursoDesejado;

    public Curso(String nomeCursoDesejado){
        this.nomeCursoDesejado = nomeCursoDesejado;
    }

    public String getNomeCursoDesejado(){
        return this.nomeCursoDesejado;
    }

    public void setNomeCursoDesejado(String nomeCursoDesejado){
        this.nomeCursoDesejado = nomeCursoDesejado;
    }

}
