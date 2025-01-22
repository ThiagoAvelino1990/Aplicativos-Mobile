package br.com.dev.appclientes.datamodel;

public class ClienteDataModel {

    public static final String TABELA = "cliente";

    public static final String ID = "id";
    public static final String NOME = "nome";
    public static final String TELEFONE = "telefone";
    public static final String EMAIL = "email";
    public static final String CEP = "cep";
    public static final String LOGRADOURO = "logradouro";
    public static final String COMPLEMENTO = "complemento";
    public static final String NUMERO = "numero";
    public static final String BAIRRO = "bairro";
    public static final String CIDADE = "cidade";
    public static final String ESTADO = "estado";
    public static final String PAIS = "pais";
    public static final String TERMOSDEUSO = "termos_de_uso";

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
                TERMOSDEUSO + " INTEGER)";
    }

}
