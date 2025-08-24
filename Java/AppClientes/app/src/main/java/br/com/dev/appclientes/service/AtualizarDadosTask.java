package br.com.dev.appclientes.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import br.com.dev.appclientes.api.AppUtil;
import br.com.dev.appclientes.api.AppUtilWebService;

/**
 * Atualizar dados no servidor
 */
public class AtualizarDadosTask extends AsyncTask<String, String, String> {

    private ProgressDialog progressDialog;
    private Context context;
    private HttpURLConnection conn = null;
    private URL url = null;
    private Uri.Builder builder;


    public AtualizarDadosTask(Context context){
        this.context = context;
        this.builder = new Uri.Builder();
        this.progressDialog = new ProgressDialog(context);

        // builder.appendQueryParameter("app","Cliente");
    }


    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        progressDialog.setMessage("Processando dados..");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {

        String[] param = new String[strings.length];

        for(int i = 0; i < strings.length; i++){
            param[i] = strings[i];
        }

        //Montar URL com o endereço do script php
        try{
            if (param[0].equals("cliente")){

                builder.appendQueryParameter("id",param[1])
                        .appendQueryParameter("nome",param[2])
                        .appendQueryParameter("telefone",param[3])
                        .appendQueryParameter("email",param[4])
                        .appendQueryParameter("cep",param[5])
                        .appendQueryParameter("logradouro",param[6])
                        .appendQueryParameter("complemento",param[7])
                        .appendQueryParameter("numero",param[8])
                        .appendQueryParameter("bairro",param[9])
                        .appendQueryParameter("cidade",param[10])
                        .appendQueryParameter("estado",param[11])
                        .appendQueryParameter("pais",param[12])
                        .appendQueryParameter("documento",param[13])
                        .appendQueryParameter("id_tipo_documento",param[14])
                        .appendQueryParameter("id_tipo_pessoa",param[15])
                        .appendQueryParameter("termos_de_uso",param[16])
                        .appendQueryParameter("data_inclusao",param[17])
                        .appendQueryParameter("data_alteracao",param[18])
                        .appendQueryParameter("id_usuario",param[19]);

                url = new URL(AppUtilWebService.URL_WEB_SERVICE+"updateClientes.php?token=xpto");//TODO:Remover token fixado

            } else if (param[0].equals("usuario")) {

                builder.appendQueryParameter("id",param[1])
                        .appendQueryParameter("id_tipo_pessoa",param[2])
                        .appendQueryParameter("nome",param[3])
                        .appendQueryParameter("cpf_cnpj",param[4])
                        .appendQueryParameter("logradouro",param[5])
                        .appendQueryParameter("complemento",param[6])
                        .appendQueryParameter("email",param[7])
                        .appendQueryParameter("senha",param[8])
                        .appendQueryParameter("telefone",param[9])
                        .appendQueryParameter("lembrar_senha",param[10])
                        .appendQueryParameter("atualizar_senha",param[11])
                        .appendQueryParameter("data_de_inclusao",param[12])
                        .appendQueryParameter("data_de_alteracao",param[13]);


                url = new URL(AppUtilWebService.URL_WEB_SERVICE+"updateUsuario.phptoken=xpto");//TODO:Remover token fixado

                return "ERRO - Não foi possível definir uma parâmetro válido. "+param;
            }

        }catch(MalformedURLException err){
            Log.e(AppUtil.TAG,"MalformedURLException - "+err.getMessage());
        }catch(Exception err){
            Log.e(AppUtil.TAG,"Exception - "+err.getMessage());
        }


        //Conectando com o servidor apache
        try{
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(AppUtilWebService.CONNECTION_TIMEOUT);
            conn.setReadTimeout(AppUtilWebService.READ_TIMEOUT);
            conn.setRequestMethod("PUT");//"GET", "PUT", "POST", "DELETE"
            conn.setRequestProperty("charset", "utf-8");

            conn.setDoInput(true);
            conn.setDoOutput(true);

            String query = builder.build().getEncodedQuery();

            OutputStream outputStream = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

            writer.write(query);
            writer.flush();
            writer.close();
            outputStream.close();

            conn.connect();

        }catch(IOException err){
            Log.e(AppUtil.TAG,"IOException -"+err.getMessage());
        }catch(Exception err){
            Log.e(AppUtil.TAG,"Exceptio -"+err.getMessage());
        }

        //Verifica a conexão com o Apache e processa o retorno
        try{
            /**
             * 200 - OK
             * 403 - Forbideen
             * 404 - página não encontrada
             * 500 - erro interno do servidor
             */
            int response_code = conn.getResponseCode();

            if(response_code == HttpURLConnection.HTTP_OK){
                InputStream input = conn.getInputStream();

                BufferedReader reader = new BufferedReader((new InputStreamReader(input)));
                StringBuilder result = new StringBuilder();

                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();
                return (result.toString()); //Retorna o JSON

            }else{
                return "ERRO _ "+ response_code;
            }

        }catch(Exception err){
            Log.e(AppUtil.TAG,"[AtualizarSistema] "+err.getMessage());
            return "ERRO ao atualizar os dados";
        }finally {
            if(conn != null){
                conn.disconnect();
            }

        }

    }

    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);

        progressDialog.dismiss();
    }
}
