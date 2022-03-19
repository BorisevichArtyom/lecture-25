package by.itacademy.javaenterprise.borisevich.dao.impl;

import by.itacademy.javaenterprise.borisevich.dao.ExerciseDAO;
import by.itacademy.javaenterprise.borisevich.dao.exception.DAOException;
import by.itacademy.javaenterprise.borisevich.entity.Exercise;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Slf4j
@Repository
public class ExerciseDAOImpl implements ExerciseDAO {
    private final static String PARAMETER_ID = "id";
    @PersistenceContext
    @Qualifier("entityManagerFactory")
    private EntityManager entityManager;

    @Override
    @Transactional
    public void saveOrUpdate(Exercise exercise) throws DAOException {
        if (exercise.getId() != null) {
            entityManager.merge(exercise);
        } else {
            entityManager.persist(exercise);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Exercise findById(Long id) throws DAOException {
        if (id == null) {
            throw new DAOException("id is null!");
        }
        return entityManager.find(Exercise.class, id);
    }

    @Override
    @Transactional
    public void deleteById(Long id) throws DAOException {
        if (id != null) {
            entityManager.remove(entityManager.find(Exercise.class, id));
        }
    }


    @Override
    @Transactional(readOnly = true)
    public List<Exercise> findAll(int page, int size) throws DAOException {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Exercise> cr = cb.createQuery(Exercise.class);
        Root<Exercise> root = cr.from(Exercise.class);
        cr.orderBy(cb.asc(root.get(PARAMETER_ID)));
        TypedQuery<Exercise> query = entityManager.createQuery(cr);
        return query.setFirstResult(page)
                .setMaxResults(size)
                .getResultList();
    }

}
