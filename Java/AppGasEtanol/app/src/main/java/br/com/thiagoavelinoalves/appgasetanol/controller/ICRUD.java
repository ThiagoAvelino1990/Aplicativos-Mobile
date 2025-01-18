package br.com.thiagoavelinoalves.appgasetanol.controller;

import java.util.List;

public interface ICRUD<T>{

    public boolean createObject(T obj);

    public List<T> readObject(int id);

    public boolean updateObject(T obj);

    public boolean deleteObject(T obj);
}
