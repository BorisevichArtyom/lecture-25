package by.itacademy.javaenterprise.borisevich.services.impl;

import by.itacademy.javaenterprise.borisevich.dao.TrainingSetDAO;
import by.itacademy.javaenterprise.borisevich.dao.exception.DAOException;
import by.itacademy.javaenterprise.borisevich.entity.TrainingSet;
import by.itacademy.javaenterprise.borisevich.services.TrainingSetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TrainingSetServiceImpl implements TrainingSetService<TrainingSet> {
    @Autowired
    private TrainingSetDAO trainingSetDAO;


    @Override
    public void save(TrainingSet trainingSet) {
        try {
            trainingSetDAO.saveOrUpdate(trainingSet);
        } catch (DAOException e) {
            log.info("Error with saving", e);
        }
    }

    @Override
    public TrainingSet showOne(Long id) {
        TrainingSet trainingSet = null;
        try {
            trainingSet = trainingSetDAO.findById(id);
        } catch (DAOException e) {
            log.info("Error  with finding this id", e);
        }
        return trainingSet;
    }

    @Override
    public void delete(Long id) {
        try {
            trainingSetDAO.deleteById(id);
        } catch (DAOException e) {
            log.info("Error with delete", e);
        }
    }

    @Override
    public List<TrainingSet> showAll() {
        List<TrainingSet> trainingSets = null;
        try {
            trainingSets = trainingSetDAO.findAll();
        } catch (DAOException e) {
            log.info("Cant show trainings", e);
        }
        return trainingSets;
    }

    @Override
    public List<TrainingSet> showAllPageByPage(int page, int size) {
        return trainingSetDAO.findAllPageByPage(page, size);
    }


}
