package by.itacademy.javaenterprise.borisevich.dao;

import by.itacademy.javaenterprise.borisevich.entity.TrainingSet;

import java.util.List;

public interface TrainingSetDAO extends DAO<TrainingSet> {
    long count();

    List<TrainingSet> findAllPageByPage(int page, int size);
}
