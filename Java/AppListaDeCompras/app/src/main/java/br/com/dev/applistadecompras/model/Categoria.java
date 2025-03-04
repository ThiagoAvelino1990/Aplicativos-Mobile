package br.com.dev.applistadecompras.model;

import java.util.Arrays;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Categoria extends RealmObject {

    @PrimaryKey
    private int id;

    @Required
    private String nome;

    private int totalDeProdutos;
    private byte[] imagem;

    @Ignore
    private List<String> tiutoCategoriaSpinner;
    @Ignore
    private List<Categoria> categoria;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getTotalDeProdutos() {
        return totalDeProdutos;
    }

    public void setTotalDeProdutos(int totalDeProdutos) {
        this.totalDeProdutos = totalDeProdutos;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public List<String> getTiutoCategoriaSpinner() {
        return tiutoCategoriaSpinner;
    }

    public void setTiutoCategoriaSpinner(List<String> tiutoCategoriaSpinner) {
        this.tiutoCategoriaSpinner = tiutoCategoriaSpinner;
    }

    public List<Categoria> getCategoria() {
        return categoria;
    }

    public void setCategoria(List<Categoria> categoria) {
        this.categoria = categoria;
    }


    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", totalDeProdutos=" + totalDeProdutos +
                ", imagem=" + Arrays.toString(imagem) +
                ", tiutoCategoriaSpinner=" + tiutoCategoriaSpinner +
                ", categoria=" + categoria +
                '}';
    }
}
