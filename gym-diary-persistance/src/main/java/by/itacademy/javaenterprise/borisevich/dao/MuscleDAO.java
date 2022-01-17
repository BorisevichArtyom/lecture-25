package by.itacademy.javaenterprise.borisevich.dao;

import by.itacademy.javaenterprise.borisevich.entity.Muscle;

import java.util.List;

public interface MuscleDAO extends DAO<Muscle> {
    List<Muscle> findAllPageByPage(int page, int size);
}
