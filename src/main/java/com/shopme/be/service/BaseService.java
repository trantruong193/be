package com.shopme.be.service;

import java.util.List;

public interface BaseService<T> {
    T add(T t);
    List<T> findAll();
    T findById(Long id);
    T update (T t);
    boolean remove(Long id);
}
