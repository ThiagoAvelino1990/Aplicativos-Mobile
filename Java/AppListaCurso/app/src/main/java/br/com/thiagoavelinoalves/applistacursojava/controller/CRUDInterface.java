package br.com.thiagoavelinoalves.applistacursojava.controller;

import java.util.List;

public interface CRUDInterface <T>{

    public boolean createObject(T obj);

    public boolean deleteObject(T obj);

    public boolean updateObject(T obj);

    public List<T> readObject();

}
