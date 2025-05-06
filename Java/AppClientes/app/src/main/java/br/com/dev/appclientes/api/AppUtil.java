package br.com.dev.appclientes.api;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
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


    public static String criptografarPass(String password) {

        String retorno = "";

        if(!password.isEmpty()) {

            try {
                // Create SHA Hash
                MessageDigest digest = MessageDigest.getInstance("SHA");
                digest.update(password.getBytes());
                byte messageDigest[] = digest.digest();

                StringBuffer SHAHash = new StringBuffer();
                for (int i = 0; i < messageDigest.length; i++) {
                    String h = Integer.toHexString(0xFF & messageDigest[i]);
                    while (h.length() < 2)
                        h = "0" + h;
                    SHAHash.append(h);
                }

                return SHAHash.toString();

            } catch (NoSuchAlgorithmException e) {
                retorno = "[ERRO] -CRIPTOGRAFAR SENHA: "+e.getMessage();
                Log.e(TAG,"[ERRO] -CRIPTOGRAFAR SENHA: "+e.getMessage());
            }catch(Exception err){
                retorno = "[ERRO GENÉRICO] -CRIPTOGRAFAR SENHA: "+err.getMessage();
                Log.e(TAG,"[ERRO GENÉRICO] -CRIPTOGRAFAR SENHA: "+err.getMessage());
            }

        }
        return retorno;
    }

    public static String radomPass(){

        String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVXWYZabcdefghijklmnopqrstuvxwyz!@#$%¨&*()-=+/";
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder newpass = new StringBuilder();

        for(int i = 0; i <= 3; i++){
            int indx = secureRandom.nextInt(characters.length());
            newpass.append(characters.charAt(indx));
        }

        return newpass.toString();

    }

    //TODO: Criar um validador de CEP
    public boolean validaCep(String cep){


        return true;
    }

    public static JsonObject getEndereco(String cep){

        String urlApi = "https://brasilapi.com.br/api/cep/v1/" + cep;

        //TODO: Colocar o validador de CEP aqui:- validaCep

        try{
            URL url = new URL(urlApi);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            String inputLine;

            while ((inputLine = bufferedReader.readLine())!=null){
                stringBuilder.append(inputLine);
            }
            bufferedReader.close();

            return JsonParser.parseString(bufferedReader.toString()).getAsJsonObject();

        }catch(JsonIOException err){
            Log.e(TAG,"Erro ao gerar o Json "+err.getMessage());
            return null;
        }catch(Exception err){
            Log.e(TAG,"Erro na execução do Json "+err.getMessage());
            return null;
        }
    }
}
