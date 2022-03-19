package by.itacademy.javaenterprise.borisevich.services.impl;

import by.itacademy.javaenterprise.borisevich.dao.ExerciseDAO;
import by.itacademy.javaenterprise.borisevich.dao.exception.DAOException;
import by.itacademy.javaenterprise.borisevich.entity.Exercise;
import by.itacademy.javaenterprise.borisevich.services.ExerciseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ExerciseServiceImpl implements ExerciseService<Exercise> {
    @Autowired
    private ExerciseDAO exerciseDAO;

    @Override
    public void save(Exercise exercise) {
        try {
            exerciseDAO.saveOrUpdate(exercise);
        } catch (DAOException e) {
            log.info("Error with save", e);
        }
    }

    @Override
    public Exercise showOne(Long id) {
        Exercise exercise = null;
        try {
            exercise =exerciseDAO.findById(id);
        } catch (DAOException e) {
            log.info("Error with finding this id", e);
        }
        return exercise;
    }

    @Override
    public void delete(Long id) {
        try {
            exerciseDAO.deleteById(id);
        } catch (DAOException e) {
            log.info("Error with delete", e);
        }
    }

    @Override
    public List<Exercise> showAll(int page, int size) {
        List<Exercise> exercises = null;
        try {
            exercises = exerciseDAO.findAll(page,size);
        } catch (DAOException e) {
            log.info("Cant show exercises", e);
        }
        return exercises;
    }
}
