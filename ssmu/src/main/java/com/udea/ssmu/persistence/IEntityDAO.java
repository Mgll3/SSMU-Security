package com.udea.ssmu.persistence;

import java.util.List;

public interface IEntityDAO <T>{

    void create(T entity);
    List<T> findAll();
    T find(T entity);
    void update(T entity);
    void delete(T entity);
}
