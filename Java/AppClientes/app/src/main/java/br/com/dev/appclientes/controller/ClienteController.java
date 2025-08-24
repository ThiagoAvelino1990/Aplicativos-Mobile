package br.com.dev.appclientes.controller;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import br.com.dev.appclientes.api.AppUtil;
import br.com.dev.appclientes.api.AppUtilSharedPreferences;
import br.com.dev.appclientes.datamodel.ClienteDataModel;
import br.com.dev.appclientes.datasource.AppDataBase;
import br.com.dev.appclientes.model.Cliente;
import br.com.dev.appclientes.service.AtualizarDadosTask;
import br.com.dev.appclientes.service.DeletarDadosTask;
import br.com.dev.appclientes.service.InserirDadosTask;

public class ClienteController extends AppDataBase implements ICRUD<Cliente> {

    ContentValues contentValues;

    private Context context;

    SharedPreferences prefs;

    public ClienteController(Context context){
        super(context);
        this.context = context;
    }

    @Override
    public boolean insertObject(Cliente obj) {

        contentValues = new ContentValues();
        prefs = context.getSharedPreferences(AppUtilSharedPreferences.PREF_APP,MODE_PRIVATE);

        contentValues.put(ClienteDataModel.NOME, obj.getNome());
        contentValues.put(ClienteDataModel.TELEFONE, obj.getTelefone());
        contentValues.put(ClienteDataModel.EMAIL, obj.getEmail());
        contentValues.put(ClienteDataModel.CEP, obj.getCep());
        contentValues.put(ClienteDataModel.LOGRADOURO, obj.getLogradouro());
        contentValues.put(ClienteDataModel.COMPLEMENTO, obj.getComplemento());
        contentValues.put(ClienteDataModel.NUMERO, obj.getNumero());
        contentValues.put(ClienteDataModel.BAIRRO, obj.getBairro());
        contentValues.put(ClienteDataModel.CIDADE, obj.getCidade());
        contentValues.put(ClienteDataModel.ESTADO, obj.getEstado());
        contentValues.put(ClienteDataModel.PAIS, obj.getPais());
        contentValues.put(ClienteDataModel.DOCUMENTO, AppUtil.formatarDocumento(obj.getDocumento(), obj.getDocumento().length()));//Formatar o documento de acordo com o tipo
        if(obj.getDocumento().length() == 14){
            contentValues.put(ClienteDataModel.IDTIPODOCUMENTO, "CNPJ");
            contentValues.put(ClienteDataModel.IDTIPOPESSOA, "PJ");
        } else if (obj.getDocumento().length() == 11) {
            contentValues.put(ClienteDataModel.IDTIPODOCUMENTO, "CPF");
            contentValues.put(ClienteDataModel.IDTIPOPESSOA, "PF");
        }else{
            contentValues.put(ClienteDataModel.IDTIPODOCUMENTO, "N/A");
            contentValues.put(ClienteDataModel.IDTIPOPESSOA, "ES");
        }
        contentValues.put(ClienteDataModel.TERMOSDEUSO, obj.isTermosDeUso());
        contentValues.put(ClienteDataModel.DATAINLCUSAO, AppUtil.getDataFormat());
        contentValues.put(ClienteDataModel.IDUSUARIO, prefs.getString("idUsuario",String.valueOf(-1)));

        // Chamaa a classe assincrona
        new InserirDadosTask(context, contentValues).execute("cliente");

        return insertDados(ClienteDataModel.TABELA,contentValues);
    }

    @Override
    public boolean deleteObject(Cliente obj) {

        contentValues = new ContentValues();

        contentValues.put(ClienteDataModel.ID, obj.getId());

        // Chama a classe assincrona
        new DeletarDadosTask(context).execute("cliente",contentValues.getAsString("ID"));

        return deleteDados(ClienteDataModel.TABELA, (Integer) contentValues.get(ClienteDataModel.ID));
    }

    @Override
    public boolean updateObject(Cliente obj) {

        contentValues = new ContentValues();

        contentValues.put(ClienteDataModel.ID, obj.getId());
        contentValues.put(ClienteDataModel.NOME, obj.getNome());
        contentValues.put(ClienteDataModel.TELEFONE, obj.getTelefone());
        contentValues.put(ClienteDataModel.EMAIL, obj.getEmail());
        contentValues.put(ClienteDataModel.CEP, obj.getCep());
        contentValues.put(ClienteDataModel.LOGRADOURO, obj.getLogradouro());
        contentValues.put(ClienteDataModel.COMPLEMENTO, obj.getComplemento());
        contentValues.put(ClienteDataModel.NUMERO, obj.getNumero());
        contentValues.put(ClienteDataModel.BAIRRO, obj.getBairro());
        contentValues.put(ClienteDataModel.CIDADE, obj.getCidade());
        contentValues.put(ClienteDataModel.ESTADO, obj.getEstado());
        contentValues.put(ClienteDataModel.PAIS, obj.getPais());
        contentValues.put(ClienteDataModel.DOCUMENTO, AppUtil.formatarDocumento(obj.getDocumento(), obj.getDocumento().length()));//Formatar o documento de acordo com o tipo
        if(obj.getDocumento().length() == 14){
            contentValues.put(ClienteDataModel.IDTIPODOCUMENTO, "CNPJ");
            contentValues.put(ClienteDataModel.IDTIPOPESSOA, "PJ");
        } else if (obj.getDocumento().length() == 11) {
            contentValues.put(ClienteDataModel.IDTIPODOCUMENTO, "CPF");
            contentValues.put(ClienteDataModel.IDTIPOPESSOA, "PF");
        }else{
            contentValues.put(ClienteDataModel.IDTIPODOCUMENTO, "N/A");
            contentValues.put(ClienteDataModel.IDTIPOPESSOA, "ES");
        }
        contentValues.put(ClienteDataModel.TERMOSDEUSO, obj.isTermosDeUso());
        contentValues.put(ClienteDataModel.DATAALTERACAO, AppUtil.getDataFormat());
        contentValues.put(ClienteDataModel.IDUSUARIO, obj.getFkIdUsuario());

        // Chama a classe assincrona
        new AtualizarDadosTask(context, contentValues).execute("cliente");


        return updateDados(ClienteDataModel.TABELA, contentValues);
    }

    @Override
    public List<Cliente> readObject() {
        return getAllClientes(ClienteDataModel.TABELA);
    }

    @Override
    public List<Cliente> readObjectById(int id) {

        return getClienteById(ClienteDataModel.TABELA, id);
    }


    public List<String> getAllClientesListView() {

        List<String> clientList = new ArrayList<>();

        for (Cliente objCliente: readObject()) {

            clientList.add(objCliente.getId()+" "+objCliente.getNome());
        }

        return clientList;
    }

    public ArrayList<Cliente> getallClientesByIdUser(int idUser){

            return getClientesByIdUsuario(ClienteDataModel.TABELA, idUser);

    }
}
