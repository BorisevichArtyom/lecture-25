package by.itacademy.javaenterprise.borisevich.dao;

import by.itacademy.javaenterprise.borisevich.dao.exception.DAOException;
import by.itacademy.javaenterprise.borisevich.entity.Training;
import by.itacademy.javaenterprise.borisevich.entity.TrainingSet;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface TrainingDAO extends DAO<Training> {

    List<Training> findAllPageByPage(int page, int size);

    Training findSetsJoinFetch(Long id) throws DAOException;
}
