package by.itacademy.javaenterprise.borisevich.services.impl;

import by.itacademy.javaenterprise.borisevich.dao.TrainingDAO;
import by.itacademy.javaenterprise.borisevich.dao.exception.DAOException;
import by.itacademy.javaenterprise.borisevich.entity.Training;
import by.itacademy.javaenterprise.borisevich.entity.TrainingSet;
import by.itacademy.javaenterprise.borisevich.services.TrainingService;
import by.itacademy.javaenterprise.borisevich.services.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class TrainingServiceImpl implements TrainingService<Training> {
    @Autowired
    private TrainingDAO trainingDAO;

    @Override
    public void saveTraining(Training training) {
        try {
            trainingDAO.saveOrUpdate(training);
        } catch (DAOException e) {
            log.info("Error with saving", e);
        }
    }

    @Override
    public Training showOne(Long id) {
        Training training = null;
        try {
            training = trainingDAO.findById(id);
        } catch (DAOException e) {
            log.info("Error  with finding this id", e);
        }
        return training;
    }

    @Override
    public void delete(Long id) {
        try {
            trainingDAO.deleteById(id);
        } catch (DAOException e) {
            log.info("Error with delete", e);
        }
    }

    @Override
    public List<Training> showAll() {
        List<Training> trainings = null;
        try {
            trainings = trainingDAO.findAll();
        } catch (DAOException e) {
            log.info("Cant show trainings", e);
        }
        return trainings;
    }

    @Override
    public List<Training> showAllPageByPage(int page, int size) {
        return trainingDAO.findAllPageByPage(page, size);
    }

    @Override
    public Training showAllSets(Long id) throws ServiceException {
        try {
            return trainingDAO.findSetsJoinFetch(id);
        } catch (DAOException e) {
            throw new ServiceException("No such Training");
        }
    }

    @Override
    public TrainingSet showTrainingSets(Long trainingId, Long trainingSetId) throws ServiceException {
        Training training = null;
        try {
            training = trainingDAO.findSetsJoinFetch(trainingId);
        } catch (DAOException e) {
            throw new ServiceException("No training with that id");
        }
        Set<TrainingSet> trainingSets = training.getTrainingSet();
        TrainingSet set = null;
        for (TrainingSet trainingSet : trainingSets) {
            if (trainingSet.getId().equals(trainingSetId)) {
                set = trainingSet;
            }
        }
        if (set == null) {
            throw new ServiceException("No sets with that id");
        }
        return set;
    }
}
