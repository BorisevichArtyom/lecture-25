package by.itacademy.javaenterprise.borisevich.services.impl;

import by.itacademy.javaenterprise.borisevich.dao.MuscleDAO;
import by.itacademy.javaenterprise.borisevich.dao.exception.DAOException;
import by.itacademy.javaenterprise.borisevich.entity.Muscle;
import by.itacademy.javaenterprise.borisevich.services.MuscleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MuscleServiceImpl implements MuscleService<Muscle> {
    @Autowired
    private MuscleDAO muscleDAO;

    @Override
    public void save(Muscle muscle) {
        try {
            muscleDAO.saveOrUpdate(muscle);
        } catch (DAOException e) {
            log.info("Error with save", e);
        }
    }

    @Override
    public Muscle showOne(Long id) {
        Muscle muscle = null;
        try {
            muscle = muscleDAO.findById(id);
        } catch (DAOException e) {
            log.info("Error with finding this id", e);
        }
        return muscle;
    }

    @Override
    public void delete(Long id) {
        try {
            muscleDAO.deleteById(id);
        } catch (DAOException e) {
            log.info("Error with delete", e);
        }
    }

    @Override
    public List<Muscle> showAll() {
        List<Muscle> muscles = null;
        try {
            muscles = muscleDAO.findAll();
        } catch (DAOException e) {
            log.info("Cant show muscles", e);
        }
        return muscles;
    }

    @Override
    public List<Muscle> showAllPageByPage(int page, int size) {
        return muscleDAO.findAllPageByPage(page, size);
    }
}
