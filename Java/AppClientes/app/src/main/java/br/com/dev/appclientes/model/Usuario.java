package br.com.dev.appclientes.model;

public class Usuario {

    private int id;
    private String nome;
    private String email;
    private String senha;
    private boolean chkLembrarSenha;

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

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", chkLembrarSenha=" + chkLembrarSenha +
                '}';
    }
}
