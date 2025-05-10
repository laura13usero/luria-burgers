package dao;

import java.util.List;

public interface DAO<T> {
    void save(T entity);
    void update(T entity);
    void delete(T entity);
    T findById(int id);
    List<T> findAll();
}
