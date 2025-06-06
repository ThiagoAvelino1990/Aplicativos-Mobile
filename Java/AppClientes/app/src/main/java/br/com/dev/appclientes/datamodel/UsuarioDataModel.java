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
    public static final String TELEFONE = "TELEFONE";
    public static final String LEMBRARSENHA = "LEMBRAR_SENHA";
    public static final String ATUALIZARSENHA = "ATUALIZAR_SENHA";
    public static final String DATACINLUSAO = "DATA_DE_INCLUSAO";
    public static final String DATAALTERACAO = "DATA_DE_ALTERACAO";


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
                TELEFONE + " TEXT, " +
                LEMBRARSENHA + " INTEGER, " +
                ATUALIZARSENHA + " TEXT, " +
                DATACINLUSAO + " TEXT, "+
                DATAALTERACAO + " TEXT)";

        /**
         * Exemplo de com criar FOREIGN KEY
         * FOREIGN KEY(fk_coluna) REFERENCES tabela(coluna)
         */
    }

}
