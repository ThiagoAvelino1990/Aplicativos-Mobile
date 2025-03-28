package br.com.dev.appclientes.api;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AppUtil {

    public static final String TAG = "LOG_APPCLIENTES";
    public static final int TIME_SPLASH = 4 * 1000;
    public static final int TIME_CADASTRO = 1 * 1000;
    public static final String VERSION = "v1.0.0";
    public static final String PREF_APP = "app_clientes_pref";

    public static final String TERMOS_DE_USO = "Última atualização: [data]\n" +
            "\n" +
            "Ao usar o aplicativo [Nome do Aplicativo], você concorda com os seguintes termos:\n" +
            "\n" +
            "Aceitação: Ao acessar o app, você aceita estes termos. Se não concordar, não use.\n" +
            "Alterações: Podemos atualizar os termos a qualquer momento. Verifique periodicamente.\n" +
            "Uso: Use o app de maneira legal. Não modifique ou distribua seu conteúdo sem permissão.\n" +
            "Conta: Você é responsável por sua conta. Notifique-nos sobre uso não autorizado.\n" +
            "Privacidade: O uso de dados está descrito na [Política de Privacidade].\n" +
            "Responsabilidade: Não nos responsabilizamos por danos ou falhas, conforme permitido por lei.\n" +
            "Rescisão: Podemos suspender sua conta por violação. Você pode encerrar a conta a qualquer momento.\n" +
            "Legislação: Este termo é regido pelas leis de [país/região].\n" +
            "Contato: Dúvidas? Entre em contato em [e-mail de contato].";

    /**
     *
     * @return Retorna a data no formato "dd/MM/yyyy 00:00:00"
     */
    public static String getDataFormat(){

        String dataAtualFormat;

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        dataAtualFormat = dateFormat.format(date);

        return dataAtualFormat;

    }

    public static boolean validaCnpjCpf(String cpfCnpj){

        cpfCnpj = "14572457000185";

        int peso = 2;
        int soma = 0;
        int somaTotal = 0;
        int digVerifUm, digVerifDois;
        String cnpj = cpfCnpj;
        int tam = cpfCnpj.length() - 2;

        //Cálculo para validação do CNPJ
        // Calcular o Dígito verificador 1
        for (int i = tam; i > 0; i--){
            soma = ((int)cnpj.charAt(i) * peso);
            peso ++;

            if(peso > 9){
                peso = 2;
            }
            somaTotal = somaTotal + soma;
        }

        if(soma % 11 == 0 || soma % 11 == 1){
            digVerifUm = 0;
        }else{
            digVerifUm = 11 - (soma % 11);
        }

        soma = 0;
        peso = 0;
        cnpj = digVerifUm+cnpj;
        tam  = cpfCnpj.length() - 1;

        for(int i = tam; i > 0; i--){
            soma = ((int)cnpj.charAt(i) * peso);
            peso ++;

            if(peso > 9){
                peso = 2;
            }
            somaTotal = somaTotal + soma;
        }

        if(soma % 11 == 0 || soma % 11 == 1){
            digVerifDois = 0;
        }else{
            digVerifDois = 11 - (soma % 11);
        }

        if((digVerifUm == cpfCnpj.charAt(12)) && (digVerifDois == cpfCnpj.charAt(13))){
            return true;
        }

        return false;

    }






}
