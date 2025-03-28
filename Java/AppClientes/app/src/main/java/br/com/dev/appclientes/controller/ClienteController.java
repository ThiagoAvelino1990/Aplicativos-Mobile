package br.com.dev.appclientes.controller;

import android.content.ContentValues;
import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.dev.appclientes.datamodel.ClienteDataModel;
import br.com.dev.appclientes.datasource.AppDataBase;
import br.com.dev.appclientes.model.Cliente;

public class ClienteController extends AppDataBase implements ICRUD<Cliente> {

    ContentValues contentValues;

    public ClienteController(Context context){
        super(context);
    }

    @Override
    public boolean insertObject(Cliente obj) {

        contentValues = new ContentValues();

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
        contentValues.put(ClienteDataModel.TERMOSDEUSO, obj.isTermosDeUso());

        return insertDados(ClienteDataModel.TABELA,contentValues);
    }

    @Override
    public boolean deleteObject(Cliente obj) {

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
        contentValues.put(ClienteDataModel.TERMOSDEUSO, obj.isTermosDeUso());

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
        contentValues.put(ClienteDataModel.TERMOSDEUSO, obj.isTermosDeUso());

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
}
