package br.com.dev.appclientes.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class UsuarioORM extends RealmObject {

    @PrimaryKey
    private int id;
    @Required
    private String nome;
    @Required
    private String email;
    @Required
    private String senha;
    private boolean chkLembrarSenha;
    @Required
    private String logradouro;
    private String complemento;
    @Required
    private String cpfCnpj;
    @Required
    private boolean isPessoaFisica;


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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isChkLembrarSenha() {
        return chkLembrarSenha;
    }

    public void setChkLembrarSenha(boolean chkLembrarSenha) {
        this.chkLembrarSenha = chkLembrarSenha;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public boolean isPessoaFisica() {
        return isPessoaFisica;
    }

    public void setPessoaFisica(boolean pessoaFisica) {
        isPessoaFisica = pessoaFisica;
    }


    @Override
    public String toString() {
        return "UsuarioORM{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", chkLembrarSenha=" + chkLembrarSenha +
                ", logradouro='" + logradouro + '\'' +
                ", complemento='" + complemento + '\'' +
                ", cpfCnpj='" + cpfCnpj + '\'' +
                ", isPessoaFisica=" + isPessoaFisica +
                '}';
    }
}
