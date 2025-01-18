package br.com.thiagoavelinoalves.appgasetanol.model;

import androidx.annotation.NonNull;

public class Combustivel {

    private int id;
    private double precoCombustivel;
    private String nomeCombustivel;
    private String recomendacao;

    public int getId(){
        return this.id;
    }
    public void setId (int id){
        this.id = id;
    }

    public double getPrecoCombustivel(){
        return this.precoCombustivel;
    }
    public void setPrecoCombustivel(double precoCombustivel){
        this.precoCombustivel = precoCombustivel;
    }

    public String getNomeCombustivel(){
        return this.nomeCombustivel;
    }
    public void setNomeCombustivel(String nomeCombustivel){
        this.nomeCombustivel = nomeCombustivel;
    }

    public String getRecomendacao(){
        return this.recomendacao;
    }
    public void setRecomendacao(String recomendacao){
        this.recomendacao = recomendacao;
    }

    @Override
    public String toString() {
        return "Combustivel{" +
                "id=" + id +
                ", precoCombustivel=" + precoCombustivel +
                ", nomeCombustivel='" + nomeCombustivel + '\'' +
                ", recomendacao='" + recomendacao + '\'' +
                '}';
    }
}
