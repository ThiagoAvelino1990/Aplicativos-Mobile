package br.com.dev.applistadecompras.controller;

import java.util.List;

public interface ICRUD <T>{

    public boolean insert(T obj);
    public List<T> read();
    public boolean update(T obj);
    public boolean delete(T obj);
    public boolean deleteByID(int id);
}
