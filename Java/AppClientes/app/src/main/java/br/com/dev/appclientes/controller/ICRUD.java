package br.com.dev.appclientes.controller;

import java.util.List;

public interface ICRUD <T>{

    public boolean insertObject();

    public boolean deleteObject();

    public boolean updateObject();

    public List<T> readObject();

}
