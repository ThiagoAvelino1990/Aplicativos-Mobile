package br.com.dev.appclientes.datamodel;

public class ClienteDataModel {

    public static final String TABELA = "CLIENTE";

    public static final String ID = "ID";
    public static final String NOME = "NOME";
    public static final String TELEFONE = "TELEFONE";
    public static final String EMAIL = "EMAIL";
    public static final String CEP = "CEP";
    public static final String LOGRADOURO = "LOGRADOURO";
    public static final String COMPLEMENTO = "COMPLEMENTO";
    public static final String NUMERO = "NUMERO";
    public static final String BAIRRO = "BAIRRO";
    public static final String CIDADE = "CIDADE";
    public static final String ESTADO = "ESTADO";
    public static final String PAIS = "PAIS";
    public static final String DOCUMENTO = "DOCUMENTO";
    public static final String IDTIPODOCUMENTO = "ID_TIPO_DOCUMENTO";
    public static final String IDTIPOPESSOA = "ID_TIPO_PESSOA";
    public static final String TERMOSDEUSO = "TERMOS_DE_USO";
    public static final String DATAINLCUSAO = "DATA_INCLUSAO";
    public static final String DATAALTERACAO = "DATA_ALTERACAO";
    public static final String IDUSUARIO = "ID_USUARIO";

    public static String criarTabela(){

        return "CREATE TABLE " + TABELA +
                "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                 NOME + " TEXT, " +
                TELEFONE + " TEXT, " +
                EMAIL + " TEXT, " +
                CEP + " TEXT, " +
                LOGRADOURO + " TEXT, " +
                COMPLEMENTO + " TEXT, " +
                NUMERO + " TEXT, " +
                BAIRRO + " TEXT, " +
                CIDADE + " TEXT, " +
                ESTADO + " TEXT, " +
                PAIS + " TEXT, " +
                DOCUMENTO + "TEXT, "+
                IDTIPODOCUMENTO + "TEXT, "+
                IDTIPOPESSOA + "TEXT, "+
                TERMOSDEUSO + " INTEGER, "+
                DATAINLCUSAO + " TEXT, "+
                DATAALTERACAO + "TEXT, "+
                IDUSUARIO + "INTEGER, "+
                "FOREIGN KEY("+IDUSUARIO+") REFERENCES USUARIO(ID)";
        /**
         * Exemplo de com criar FOREIGN KEY
         * FOREIGN KEY(fk_coluna) REFERENCES tabela(coluna)
         */
    }

}
