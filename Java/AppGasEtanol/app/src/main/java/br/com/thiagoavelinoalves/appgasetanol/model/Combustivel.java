package br.com.thiagoavelinoalves.appgasetanol.model;

public class Combustivel {

    private double precoCombustivel;
    private String nomeCombustivel;
    private String recomendacao;

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

}
