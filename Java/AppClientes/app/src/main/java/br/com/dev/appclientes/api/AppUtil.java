package br.com.dev.appclientes.api;

import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;

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

        String cpfCnpjRevertido = "";

        //Verificação primária
        for(int i = (cpfCnpj.length() - 1); i >= 0 ; i--){
            cpfCnpjRevertido = cpfCnpjRevertido + cpfCnpj.charAt(i);
        }

        if(cpfCnpj.equals(cpfCnpjRevertido)){
            return false;
        }
        try{
            int validador = 0;
            int soma = 0;
            int digVerifUm = 0, digVerifDois = 0;

            if (cpfCnpj.length() == 14){
                //Cálculo para validação do CNPJ
                // Calcular o Dígito verificador 1
                int peso = 2;

                for (int i = 11; i >= 0; i--){
                    validador = (Character.getNumericValue(cpfCnpj.charAt(i)) * peso);
                    peso ++;

                    if(peso > 9){
                        peso = 2;
                    }
                    soma = soma + validador;
                }

                if(soma % 11 == 0 || soma % 11 == 1){
                    digVerifUm = 0;
                }else{
                    digVerifUm = 11 - (soma % 11);
                }

                validador = 0;
                peso = 2;
                soma = 0;

                //Calculo do segundo dígito verificador
                for(int i = 12; i >= 0; i--){
                    validador = (Character.getNumericValue(cpfCnpj.charAt(i)) * peso);
                    peso ++;

                    if(peso > 9){
                        peso = 2;
                    }
                    soma = soma + validador;
                }

                if(soma % 11 == 0 || soma % 11 == 1){
                    digVerifDois = 0;
                }else{
                    digVerifDois = 11 - (soma % 11);
                }

                if((digVerifUm == Character.getNumericValue(cpfCnpj.charAt(12))) && (digVerifDois == Character.getNumericValue(cpfCnpj.charAt(13)))){
                    return true;
                }

            } else if (cpfCnpj.length() == 11){
                int peso = 10;

                //Verificando o digito verificardor Um
                for(int i = 0; i <= 8; i++){
                    validador = (Character.getNumericValue(cpfCnpj.charAt(i)) * peso);

                    if(peso > 2){
                        peso--;
                    }

                    soma = soma + validador;
                }

                digVerifUm = 11 - (soma % 11);

                if(digVerifUm == 10 || digVerifUm == 11){
                    digVerifUm = 0;
                }

                //Verificando o digito verificardor Dois
                peso = 11;
                validador = 0;
                soma =0;
                for(int i = 0; i <= 9; i++){
                    validador = (Character.getNumericValue(cpfCnpj.charAt(i)) * peso);

                    if(peso > 2){
                        peso--;
                    }

                    soma = soma + validador;

                }

                digVerifDois = 11 - (soma % 11);

                if(digVerifDois == 11 || digVerifDois == 10){
                    digVerifDois =0;
                }

                //Validando os dados
                if((digVerifUm == Character.getNumericValue(cpfCnpj.charAt(9))) && (digVerifDois == Character.getNumericValue(cpfCnpj.charAt(10)))){
                    return true;
                }
            }
        }catch(InputMismatchException err){
            Log.e(TAG,"AppUtil.validaCnpjCpf" +err.getMessage());
        }catch(Exception err){
            Log.e(TAG,"AppUtil.validaCnpjCpf" +err.getMessage());
        }

        return false;
    }

    public static String formataDocumento(String documento, int tamanho){

        try{
            if(tamanho == 14){
                documento = documento.substring(0,2)+"."+documento.substring(2,5)+"."+
                        documento.substring(5,8)+"."+documento.substring(8,12)+"-"+
                        documento.substring(12,14);

                return documento;
            } else if (tamanho == 11) {
                documento = documento.substring(0,3)+"."+documento.substring(3,6)+"."+
                        documento.substring(6,9)+"-"+documento.substring(9,11);

                return documento;
            }
        }catch(Exception err){
            Log.e(TAG,"AppUtil.formataDocumento" +err.getMessage());
        }

        return "ERRO - Documento inválido";
    }
}
