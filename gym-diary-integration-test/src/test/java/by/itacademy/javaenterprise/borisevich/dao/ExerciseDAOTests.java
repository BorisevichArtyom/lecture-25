package by.itacademy.javaenterprise.borisevich.dao;

import by.itacademy.javaenterprise.borisevich.dao.exception.DAOException;
import by.itacademy.javaenterprise.borisevich.entity.Exercise;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;

public class ExerciseDAOTests extends BaseIntegrationTests {
    @Autowired
    private ExerciseDAO exerciseDAO;

    @Test
    public void testFindAllExercises() throws DAOException {
        assertNotNull(exerciseDAO.findAll(1, 1));
    }

    @Test
    public void testSaveExercise() throws DAOException {
        Exercise exercise = Exercise.builder().name("Test").description("No").build();
        exerciseDAO.saveOrUpdate(exercise);
        assertNotNull(exercise.getId());
        exerciseDAO.deleteById(exercise.getId());
    }

    @Test
    public void testSaveExerciseNull() {
        assertThrows(NullPointerException.class, () -> exerciseDAO.saveOrUpdate(null));
    }

    @Test
    public void testUpdateExercise() throws DAOException {
        Exercise exercise = Exercise.builder().name("Test").description("No").build();
        exerciseDAO.saveOrUpdate(exercise);
        exercise.setName("Test2");
        exerciseDAO.saveOrUpdate(exercise);
        assertNotEquals("Test1", exerciseDAO.findById(exercise.getId()));
        exerciseDAO.deleteById(exercise.getId());
    }

    @Test
    public void testUpdateExerciseInvalid() {
        assertThrows(DataIntegrityViolationException.class, () -> exerciseDAO.saveOrUpdate(new Exercise()));
    }

    @Test
    public void testDeleteExercise() throws DAOException {
        Exercise exercise = Exercise.builder().name("Test").description("No").build();
        exerciseDAO.saveOrUpdate(exercise);
        exerciseDAO.deleteById(exercise.getId());
        assertNull(exerciseDAO.findById(exercise.getId()));
    }

}
