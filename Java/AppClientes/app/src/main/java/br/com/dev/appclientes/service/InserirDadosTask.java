package br.com.dev.appclientes.service;

import android.app.ProgressDialog;
import android.content.ContentValues;
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
import java.util.Map;

import br.com.dev.appclientes.api.AppUtil;
import br.com.dev.appclientes.api.AppUtilWebService;

//Enviar os dados para o Servidor
public class InserirDadosTask extends AsyncTask<String, String, String> {


    private ProgressDialog progressDialog;
    private Context context;
    private HttpURLConnection conn = null;
    private URL url = null;
    private Uri.Builder builder;
    private ContentValues values;


    public InserirDadosTask(Context context, ContentValues contentValues){
        this.context = context;
        this.builder = new Uri.Builder();
        this.progressDialog = new ProgressDialog(context);
        this.values = contentValues;

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

                for(Map.Entry<String, Object> entry : values.valueSet()){
                    builder.appendQueryParameter(entry.getKey(), String.valueOf(entry.getValue()));
                }

                url = new URL(AppUtilWebService.URL_WEB_SERVICE+"insertClientes.php");
                Log.i(AppUtil.TAG, url.toString());

            } else if (param[0].equals("usuario")) {

                for(Map.Entry<String, Object> entry : values.valueSet()){
                    builder.appendQueryParameter(entry.getKey(), String.valueOf(entry.getValue()));
                }

                url = new URL(AppUtilWebService.URL_WEB_SERVICE+"insertUsuario.php");
                Log.i(AppUtil.TAG, url.toString());

            }else{
                Log.e(AppUtil.TAG,"ERRO - Não foi possível definir uma parâmetro válido. "+param);
            }

            builder.appendQueryParameter("token","xpto"); //TODO:Remover token fixado

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
            conn.setRequestMethod("POST");//"GET", "PUT", "POST", "DELETE"
            //conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            conn.setDoInput(true);
            conn.setDoOutput(true);

            String query = builder.build().getEncodedQuery();
            Log.i(AppUtil.TAG, query);

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
                Log.i(AppUtil.TAG, result.toString());
                reader.close();
                return (result.toString()); //Retorna o JSON

            }else{
                return "ERRO _ "+ response_code;
            }

        }catch(Exception err){
            Log.e(AppUtil.TAG,"[InsedirDados] "+err.getMessage());
            return "ERRO ao inserir os dados";
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
