package by.itacademy.javaenterprise.borisevich.services;

import java.util.List;

public interface ExerciseService<T> {

    void save(T entity);

    T showOne(Long id);

    void delete(Long id);

    List<T> showAll(int page, int size);
}
