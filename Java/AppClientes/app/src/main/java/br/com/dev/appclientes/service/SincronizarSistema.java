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

public class SincronizarSistema extends AsyncTask<String, String, String> {

    private ProgressDialog progressDialog;
    private Context context;
    private HttpURLConnection conn = null;
    private URL url = null;
    private Uri.Builder builder;

    public SincronizarSistema(){
        this.builder = new Uri.Builder();
        this.progressDialog = new ProgressDialog(context);

        builder.appendQueryParameter("app","Cliente");
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        progressDialog.setMessage("Sincronizando dados");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        //Montar URL com o endereço do script php
        try{
            url = new URL(AppUtilWebService.URL_WEB_SERVICE+"nomedoScript.php");//TODO:Ajustar URL correta
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
            conn.setRequestProperty("chatset", "utf-8");

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
            Log.e(AppUtil.TAG,"[SincronizarSistema] "+err.getMessage());
            return "ERRO ao sincronizar os dados";
        }finally {
            conn.disconnect();
        }
    }

    @Override
    protected void onPostExecute(String result){

        if (result != null && !result.startsWith("ERRO")) {
            // Aqui você parseia o JSON e salva no banco local
            Log.i(AppUtil.TAG, "Dados recebidos: " + result);

            // TODO: chamar método para salvar no banco


            // Depois de salvar, inicia a MainActivity

        } else {

        }

    }
}
