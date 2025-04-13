package br.com.dev.appclientes.model;

public class Usuario {

    private int id;
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private boolean chkLembrarSenha;
    private String logradouro;
    private String complemento;
    private String cpfCnpj;
    private boolean isPessoaFisica;
    private String dataInclusao;
    private String dataAlteracao;

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

    public String getTelefone(){
        return this.telefone;
    }

    public void setTelefone(String telefone){
        this.telefone = telefone;
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

    public String getDataInclusao() {
        return dataInclusao;
    }

    public void setDataInclusao(String dataInclusao) {
        this.dataInclusao = dataInclusao;
    }

    public String getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(String dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", telefone='" + telefone + '\'' +
                ", chkLembrarSenha=" + chkLembrarSenha +
                ", logradouro='" + logradouro + '\'' +
                ", complemento='" + complemento + '\'' +
                ", cpfCnpj='" + cpfCnpj + '\'' +
                ", isPessoaFisica=" + isPessoaFisica +
                ", dataInclusao='" + dataInclusao + '\'' +
                ", dataAlteracao='" + dataAlteracao + '\'' +
                '}';
    }
}
