package br.com.dev.applistadecompras.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AppUtil {

    public static final String TAG = "APP_LISTA_DE_COMPRAS";

    public static String getDataAtual(){

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date data = new Date();

        return dateFormat.format(data);
    }

    public static boolean validaEmail(String email){

        if(email.indexOf("@") > 0){
            return true;
        }

        return false;
    }


    public static final String TERMOS_DE_USO = "Última atualização: [24/03/2025]\n" +
            "\n" +
            "Ao usar o aplicativo [Lista de Compras], você concorda com os seguintes termos:\n" +
            "\n" +
            "Aceitação: Ao acessar o app, você aceita estes termos. Se não concordar, não use.\n" +
            "Alterações: Podemos atualizar os termos a qualquer momento. Verifique periodicamente.\n" +
            "Uso: Use o app de maneira legal. Não modifique ou distribua seu conteúdo sem permissão.\n" +
            "Conta: Você é responsável por sua conta. Notifique-nos sobre uso não autorizado.\n" +
            "Privacidade: O uso de dados está descrito na [Política de Privacidade].\n" +
            "Responsabilidade: Não nos responsabilizamos por danos ou falhas, conforme permitido por lei.\n" +
            "Rescisão: Podemos suspender sua conta por violação. Você pode encerrar a conta a qualquer momento.\n" +
            "Legislação: Este termo é regido pelas leis de [Brasil/São Paulo].\n" +
            "Contato: Dúvidas? Entre em contato em [e-mail de contato].";

}
