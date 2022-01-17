package by.itacademy.javaenterprise.borisevich.dao;

import by.itacademy.javaenterprise.borisevich.dao.exception.DAOException;
import by.itacademy.javaenterprise.borisevich.entity.Training;
import by.itacademy.javaenterprise.borisevich.entity.TrainingSet;
import by.itacademy.javaenterprise.borisevich.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TrainingDAOTests extends BaseIntegrationTests {
    @Autowired
    private TrainingDAO trainingDAO;

    @Test
    public void testFindAllTrainings() throws DAOException {
        assertNotNull(trainingDAO.findAll());
    }


    @Test
    public void testSaveTrainingNull() {
        assertThrows(NullPointerException.class, () -> trainingDAO.saveOrUpdate(null));
    }

    @Test
    public void testSaveAndGetIdTraining() throws DAOException {
        Training training = Training.builder().trainingDate(LocalDate.now()).user(User.builder().id(1L).build()).build();
        trainingDAO.saveOrUpdate(training);
        assertNotNull(trainingDAO.findById(training.getId()));
        trainingDAO.deleteById(training.getId());
    }

    @Test
    public void testUpdateTraining() throws DAOException {
        LocalDate date = LocalDate.now();
        Training training = Training.builder().trainingDate(date).user(User.builder().id(1L).build()).build();
        trainingDAO.saveOrUpdate(training);
        LocalDate trainingDate1 = training.getTrainingDate();
        training.setTrainingDate(date.minusDays(1));
        trainingDAO.saveOrUpdate(training);
        LocalDate trainingDate2 = trainingDAO.findById(training.getId()).getTrainingDate();
        assertNotEquals(trainingDate1, trainingDate2);
        trainingDAO.deleteById(training.getId());
    }

    @Test
    public void testUpdateTrainingInvalid() {
        assertThrows(DataIntegrityViolationException.class, () -> trainingDAO.saveOrUpdate(new Training()));
    }

    @Test
    public void testDeleteTraining() throws DAOException {
        Training training = Training.builder().trainingDate(LocalDate.now()).user(User.builder().id(1L).build()).build();
        trainingDAO.saveOrUpdate(training);
        trainingDAO.deleteById(training.getId());
        assertNull(trainingDAO.findById(training.getId()));
    }

    @Test
    public void testFindAllSets() throws DAOException {
        Training training = trainingDAO.findSetsJoinFetch(1L);
        Set<TrainingSet> trainingSet = training.getTrainingSet();
        assertFalse(trainingSet.isEmpty());
    }

    @Test
    public void testFindAllPageByPage() {
        List<Training> allPageByPage = trainingDAO.findAllPageByPage(1, 1);
        assertEquals(1, allPageByPage.size());
        List<Training> allPageByPage2 = trainingDAO.findAllPageByPage(1, 2);
        assertEquals(2, allPageByPage2.size());
    }


}
