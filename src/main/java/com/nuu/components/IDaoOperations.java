package com.nuu.components;

import java.util.List;

public interface IDaoOperations<T,K> {
    public boolean insert(T source);
    public T selectForObject(K key);
    public List<T> selectAll();
    public boolean update(T source);
    public boolean delete(K key);
}
