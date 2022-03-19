package by.itacademy.javaenterprise.borisevich.services;

import java.util.List;

public interface UserService<T> {
    void save(T entity);

    T showOne(Long id);

    void delete(Long id);

    List<T> showAll();

    List<T> showAllPageByPage(int page, int size);
}
