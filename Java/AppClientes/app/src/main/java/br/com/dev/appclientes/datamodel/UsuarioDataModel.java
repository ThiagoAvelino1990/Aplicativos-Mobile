package br.com.dev.appclientes.datamodel;

public class UsuarioDataModel {


    public static final String TABELA = "USUARIO";

    public static final String ID = "ID";
    public static final String IDTIPOPESSOA = "ID_TIPO_PESSOA";
    public static final String NOME = "NOME";
    public static final String CPFCNPJ = "CPF_CNPJ";
    public static final String LOGRADOURO = "LOGRAODURO";
    public static final String COMPLEMENTO = "COMPLEMENTO";
    public static final String EMAIL = "EMAIL";
    public static final String SENHA = "SENHA";
    public static final String LEMBRARSENHA = "LEMBRAR_SENHA";


    public static String criarTabela(){

        return "CREATE TABLE " + TABELA +
                "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                IDTIPOPESSOA + " TEXT, "+
                NOME + " TEXT, " +
                CPFCNPJ + " TEXT, "+
                LOGRADOURO + " TEXT, "+
                COMPLEMENTO + " TEXT, "+
                EMAIL + " TEXT, " +
                SENHA + " TEXT, " +
                LEMBRARSENHA + " INTEGER)";

        /**
         * Exemplo de com criar FOREIGN KEY
         * FOREIGN KEY(fk_coluna) REFERENCES tabela(coluna)
         */
    }

}
