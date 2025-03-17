package br.com.dev.appclientes.controller;

import java.util.List;

public interface ICRUD <T>{

    public boolean insertObject(T obj);

    public boolean deleteObject(T obj);

    public boolean updateObject(T obj);

    public List<T> readObject();

    public List<T> readObjectById(int id);

}
