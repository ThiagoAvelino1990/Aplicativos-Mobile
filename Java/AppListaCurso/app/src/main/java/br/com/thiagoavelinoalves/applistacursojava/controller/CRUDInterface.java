package br.com.thiagoavelinoalves.applistacursojava.controller;

public interface CRUDInterface <T>{

    public void createObject(T obj);

    public void deleteObject(T obj);

    public void updateObject(T obj);

    public void readObject(T obj);

}
