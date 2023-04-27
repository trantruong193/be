package com.shopme.be.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface BaseService<T> {
    T add(T t);
    List<T> findAll();
    CompletableFuture<T> findById(Long id) throws InterruptedException;
    T update (T t);
    boolean remove(Long id);
}
