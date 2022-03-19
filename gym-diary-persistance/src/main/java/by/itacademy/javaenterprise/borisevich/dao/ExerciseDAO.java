package by.itacademy.javaenterprise.borisevich.dao;

import by.itacademy.javaenterprise.borisevich.dao.exception.DAOException;
import by.itacademy.javaenterprise.borisevich.entity.Exercise;

import java.util.List;

public interface ExerciseDAO{
    void saveOrUpdate(Exercise entity) throws DAOException;

    Exercise findById(Long id) throws DAOException;

    void deleteById(Long id) throws DAOException;

    List<Exercise> findAll(int page, int size) throws DAOException;

}
