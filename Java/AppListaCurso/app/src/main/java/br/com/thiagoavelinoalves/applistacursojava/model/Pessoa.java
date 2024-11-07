package br.com.thiagoavelinoalves.applistacursojava.model;

public class Pessoa {

    private int cpf;
    private String nome;
    private String sobrenome;
    private String telefone;
    private int idCursoPessoa;

    public Pessoa() {

    }

    public int getCpf(){
        return this.cpf;
    }
    public void setCpf(int cpf){
        this.cpf = cpf;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return this.sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getIdCursoPessoa(){
        return this.idCursoPessoa;
    }
    public void setIdCursoPessoa(int idCursoPessoa){
        this.idCursoPessoa = idCursoPessoa;
    }
}
