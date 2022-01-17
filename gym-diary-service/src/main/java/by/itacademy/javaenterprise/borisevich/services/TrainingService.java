package by.itacademy.javaenterprise.borisevich.services;

import by.itacademy.javaenterprise.borisevich.entity.Training;
import by.itacademy.javaenterprise.borisevich.entity.TrainingSet;
import by.itacademy.javaenterprise.borisevich.services.exception.ServiceException;

import java.util.List;

public interface TrainingService<T> {
    void saveTraining(T training);

    T showOne(Long id);

    void delete(Long id);

    List<T> showAll();

    List<T> showAllPageByPage(int page, int size);

    Training showAllSets(Long id) throws ServiceException;

    TrainingSet showTrainingSets(Long trainingId, Long trainingSetId) throws ServiceException;
}
