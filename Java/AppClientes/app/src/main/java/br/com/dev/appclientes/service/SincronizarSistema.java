package br.com.dev.appclientes.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import br.com.dev.appclientes.controller.ClienteController;
import br.com.dev.appclientes.model.Cliente;

public class SincronizarSistema extends AsyncTask<String, String, String> {

    private ProgressDialog progressDialog;
    private Context context;
    private HttpURLConnection conn = null;
    private URL url = null;
    private Uri.Builder builder;


    public SincronizarSistema(Context context){
        this.context = context;
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

        String param = strings[0];

        //Montar URL com o endereço do script php
        try{
            url = new URL(AppUtilWebService.URL_WEB_SERVICE+"getClientesByIdUser.php?token=xpto&userID="+param);//TODO: Modificar token
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
            conn.setRequestMethod("GET");//"GET", "PUT", "POST", "DELETE"
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
            JSONArray jsonArray;
            JSONObject jsonObject;
            Cliente cliente;
            ClienteController clienteController;
            // Aqui você parseia o JSON e salva no banco local
            Log.i(AppUtil.TAG, "Dados recebidos: " + result);

            try{
                jsonArray = new JSONArray(result);
                cliente = new Cliente();
                clienteController = new ClienteController(context);

                for(int i = 0; i < jsonArray.length(); i++){
                    jsonObject = new JSONObject();

                    cliente.setId(jsonObject.getInt("ID"));
                    cliente.setNome(jsonObject.getString("NOME"));
                    cliente.setTelefone(jsonObject.getString("TELEFONE"));
                    cliente.setEmail(jsonObject.getString("EMAIL"));
                    cliente.setCep(jsonObject.getInt("CEP"));
                    cliente.setLogradouro(jsonObject.getString("LOGRAODURO"));
                    cliente.setComplemento(jsonObject.getString("COMPLEMENTO"));
                    cliente.setNumero(jsonObject.getString("NUMERO"));
                    cliente.setBairro(jsonObject.getString("BAIRRO"));
                    cliente.setCidade(jsonObject.getString("CIDADE"));
                    cliente.setEstado(jsonObject.getString("ESTADO"));
                    cliente.setPais(jsonObject.getString("PAIS"));
                    cliente.setDocumento(jsonObject.getString("DOCUMENTO"));
                    cliente.setIdTipoDocumento(jsonObject.getString("ID_TIPO_DOCUMENTO"));
                    cliente.setIdTipoPessoa(jsonObject.getString("ID_TIPO_PESSOA"));

                    if(jsonObject.getString("TERMOS_DE_USO") == "0"){
                        cliente.setTermosDeUso(true);
                    }else{
                        cliente.setTermosDeUso(false);
                    }

                    cliente.setDataInclusao(jsonObject.getString("DATA_INCLUSAO"));
                    cliente.setDataAlteracao(jsonObject.getString("DATA_ALTERACAO"));
                    cliente.setFkIdUsuario(jsonObject.getInt("ID_USUARIO"));

                    //TODO: Modificar o programa para atualizar os dados e não excluir e incluir novos dados
                    clienteController.insertObject(cliente);
                }


            }catch(JSONException err){
                Log.e(AppUtil.TAG,"Erro ao converter o JSON[onPostExecute] -"+err.getMessage());
            }catch(Exception err){
                Log.e(AppUtil.TAG,"Erro ao na execução JSON[onPostExecute] -"+err.getMessage());
            }




        } else {

        }

    }
}
