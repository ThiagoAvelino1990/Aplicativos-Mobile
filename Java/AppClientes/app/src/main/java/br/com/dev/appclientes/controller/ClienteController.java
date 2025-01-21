package br.com.dev.appclientes.controller;

import android.content.Context;

import java.util.Collections;
import java.util.List;

import br.com.dev.appclientes.datasource.AppDataBase;
import br.com.dev.appclientes.model.Cliente;

public class ClienteController extends AppDataBase implements ICRUD<Cliente> {

    public ClienteController(Context context){
        super(context);

    }

    @Override
    public boolean insertObject() {
        return false;
    }

    @Override
    public boolean deleteObject() {
        return false;
    }

    @Override
    public boolean updateObject() {
        return false;
    }

    @Override
    public List<Cliente> readObject() {
        return Collections.emptyList();
    }
}
