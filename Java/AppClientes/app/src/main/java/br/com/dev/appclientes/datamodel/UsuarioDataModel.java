package br.com.dev.appclientes.datamodel;

public class UsuarioDataModel {


    public static final String TABELA = "USUARIO";

    public static final String ID = "ID";
    public static final String NOME = "NOME";
    public static final String EMAIL = "EMAIL";
    public static final String SENHA = "SENHA";
    public static final String LEMBRARSENHA = "LEMBRAR_SENHA";

    public static String criarTabela(){

        return "CREATE TABLE " + TABELA +
                "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NOME + " TEXT, " +
                EMAIL + " TEXT, " +
                SENHA + " TEXT, " +
                LEMBRARSENHA + " INTEGER)";

        /**
         * Exemplo de com criar FOREIGN KEY
         * FOREIGN KEY(fk_coluna) REFERENCES tabela(coluna)
         */
    }

}
