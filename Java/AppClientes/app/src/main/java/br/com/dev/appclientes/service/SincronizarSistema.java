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
import br.com.dev.appclientes.controller.UsuarioController;
import br.com.dev.appclientes.datamodel.UsuarioDataModel;
import br.com.dev.appclientes.model.Cliente;
import br.com.dev.appclientes.model.Usuario;

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

        // builder.appendQueryParameter("app","Cliente");
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {

        String param = "";

        for(int i = 0; i < strings.length; i++){
            param = strings[i];
        }

        //Montar URL com o endereço do script php
        try{
            url = new URL(AppUtilWebService.URL_WEB_SERVICE+"getDadosByIdUser.php?token=xpto&userID="+param);//TODO: Modificar token
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
            //JSONArray jsonArray;
            JSONObject jsonObject;
            Cliente cliente;
            ClienteController clienteController;
            Usuario usuario;
            UsuarioController usuarioController;
            // Aqui você parseia o JSON e salva no banco local
            Log.i(AppUtil.TAG, "Dados recebidos: " + result);

            try{
                //jsonArray = new JSONArray(result);
                jsonObject = new JSONObject(result);

                JSONArray usuarioJson = jsonObject.getJSONArray("usuario");
                JSONArray clienteJson = jsonObject.getJSONArray("cliente");

                cliente = new Cliente();
                clienteController = new ClienteController(context);
                usuario = new Usuario();
                usuarioController = new UsuarioController(context);

                for(int i = 0; i < clienteJson.length(); i++){
                    JSONObject objCliente = clienteJson.getJSONObject(i);

                    cliente.setId(objCliente.getInt("ID"));
                    cliente.setNome(objCliente.getString("NOME"));
                    cliente.setTelefone(objCliente.getString("TELEFONE"));
                    cliente.setEmail(objCliente.getString("EMAIL"));
                    cliente.setCep(objCliente.getInt("CEP"));
                    cliente.setLogradouro(objCliente.getString("LOGRADOURO"));
                    cliente.setComplemento(objCliente.getString("COMPLEMENTO"));
                    cliente.setNumero(objCliente.getString("NUMERO"));
                    cliente.setBairro(objCliente.getString("BAIRRO"));
                    cliente.setCidade(objCliente.getString("CIDADE"));
                    cliente.setEstado(objCliente.getString("ESTADO"));
                    cliente.setPais(objCliente.getString("PAIS"));
                    cliente.setDocumento(objCliente.getString("DOCUMENTO"));
                    cliente.setIdTipoDocumento(objCliente.getString("ID_TIPO_DOCUMENTO"));
                    cliente.setIdTipoPessoa(objCliente.getString("ID_TIPO_PESSOA"));

                    if(objCliente.getString("TERMOS_DE_USO").equals("0")){
                        cliente.setTermosDeUso(true);
                    }else{
                        cliente.setTermosDeUso(false);
                    }

                    cliente.setDataInclusao(objCliente.getString("DATA_INCLUSAO"));
                    cliente.setDataAlteracao(objCliente.getString("DATA_ALTERACAO"));
                    cliente.setFkIdUsuario(objCliente.getInt("ID_USUARIO"));

                    clienteController.updateObject(cliente);
                }

                /**
                 * Dados do usuario
                 */
                for (int j=0; j<usuarioJson.length();j++){
                    JSONObject objUsuario = usuarioJson.getJSONObject(j);

                    usuario.setId(objUsuario.getInt("ID"));

                    if (objUsuario.getString("ID_TIPO_PESSOA").equals("PF")){
                        usuario.setPessoaFisica(true);
                    }else usuario.setPessoaFisica(false);

                    usuario.setNome(objUsuario.getString("NOME"));
                    usuario.setCpfCnpj(objUsuario.getString("CPF_CNPJ"));
                    usuario.setLogradouro(objUsuario.getString("LOGRADOURO"));
                    usuario.setComplemento(objUsuario.getString("COMPLEMENTO"));
                    usuario.setEmail(objUsuario.getString("EMAIL"));
                    usuario.setSenha(objUsuario.getString("SENHA"));
                    if (objUsuario.getString("LEMBRAR_SENHA").equals("1")){
                        usuario.setChkLembrarSenha(false);
                    }else{
                        usuario.setChkLembrarSenha(true);
                    }
                    usuario.setDataInclusao(objUsuario.getString("DATA_DE_INCLUSAO"));
                    usuario.setDataAlteracao(objUsuario.getString("DATA_DE_ALTERACAO"));

                    usuarioController.updateObject(usuario);
                }

            }catch(JSONException err){
                Log.e(AppUtil.TAG,"Erro ao converter o JSON[onPostExecute] -"+err.getMessage());
            }catch(Exception err){
                Log.e(AppUtil.TAG,"Erro ao na execução JSON[onPostExecute] -"+err.getMessage());
            }




        } else {
            Log.i(AppUtil.TAG,"Verificar esta condição");
        }


    }
}
