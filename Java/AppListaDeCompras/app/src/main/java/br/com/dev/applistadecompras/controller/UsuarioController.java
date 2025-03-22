package br.com.dev.applistadecompras.controller;

import java.util.Collections;
import java.util.List;

import br.com.dev.applistadecompras.model.Usuario;

public class UsuarioController implements ICRUD<Usuario>{
    @Override
    public boolean insert(Usuario obj) {
        return false;
    }

    @Override
    public List<Usuario> read() {
        return Collections.emptyList();
    }

    @Override
    public boolean update(Usuario obj) {
        return false;
    }

    @Override
    public boolean delete(Usuario obj) {
        return false;
    }

    @Override
    public boolean deleteByID(int id) {
        return false;
    }
}
