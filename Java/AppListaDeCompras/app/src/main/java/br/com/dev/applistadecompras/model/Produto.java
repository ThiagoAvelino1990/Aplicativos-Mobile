package br.com.dev.applistadecompras.model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Produto extends RealmObject {

    @PrimaryKey
    private int id;

    @Required
    private Date dataDeInclusao;

    @Required
    private String descricao;

    @Required
    private String un;

    private double quantidade;
    private double preco;
    private String codigoDeBarra;
    private byte[] imagem; //Bitmap(JPEG, PNG)

    @Ignore
    private List<Produto> produtos;

    private String dataAltecao;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataDeInclusao() {
        return dataDeInclusao;
    }

    public void setDataDeInclusao(Date dataDeInclusao) {
        this.dataDeInclusao = dataDeInclusao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUn() {
        return un;
    }

    public void setUn(String un) {
        this.un = un;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getCodigoDeBarra() {
        return codigoDeBarra;
    }

    public void setCodigoDeBarra(String codigoDeBarra) {
        this.codigoDeBarra = codigoDeBarra;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public String getDataAltecao(){
        return this.dataAltecao;
    }

    public void setDataAltecao(String dataAltecao){
        this.dataAltecao = dataAltecao;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", dataDeInclusao=" + dataDeInclusao +
                ", descricao='" + descricao + '\'' +
                ", un='" + un + '\'' +
                ", quantidade=" + quantidade +
                ", preco=" + preco +
                ", codigoDeBarra='" + codigoDeBarra + '\'' +
                ", imagem=" + Arrays.toString(imagem) +
                ", produtos=" + produtos +
                ", dataAltecao='" + dataAltecao + '\'' +
                '}';
    }
}
