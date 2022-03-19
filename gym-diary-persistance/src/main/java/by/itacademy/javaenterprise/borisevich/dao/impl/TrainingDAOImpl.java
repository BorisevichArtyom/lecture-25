package by.itacademy.javaenterprise.borisevich.dao.impl;

import by.itacademy.javaenterprise.borisevich.dao.TrainingDAO;
import by.itacademy.javaenterprise.borisevich.dao.exception.DAOException;
import by.itacademy.javaenterprise.borisevich.entity.Training;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Slf4j
@Repository
public class TrainingDAOImpl implements TrainingDAO {
    private final static String SELECT_SETS_TO_SPECIFIC_TRAINING =
            "SELECT a FROM Training a LEFT JOIN FETCH a.trainingSet where a.id = :id";
    private final static String PARAMETER_ID = "id";

    @PersistenceContext
    @Qualifier("entityManagerFactory")
    private EntityManager entityManager;

    @Override
    @Transactional
    public void saveOrUpdate(Training training)throws DAOException {
            if (training.getId() != null) {
                entityManager.merge(training);
            } else {
                entityManager.persist(training);
            }
    }

    @Override
    @Transactional(readOnly = true)
    public Training findById(Long id) throws DAOException {
        if (id == null) {
            throw new DAOException("id is null!");
        }
        return entityManager.find(Training.class, id);
    }

    @Override
    @Transactional
    public void deleteById(Long id) throws DAOException {
        if (id != null) {
            Training training = entityManager.find(Training.class, id);
            log.info("Entity to delete " + training);
            entityManager.remove(training);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<Training> findAll() throws DAOException {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Training> cr = cb.createQuery(Training.class);
        Root<Training> root = cr.from(Training.class);
        cr.select(root);
        Query query = entityManager.createQuery(cr);
        return (List<Training>) query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Training> findAllPageByPage(int page, int size)  {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Training> cr = cb.createQuery(Training.class);
        Root<Training> root = cr.from(Training.class);
        cr.orderBy(cb.asc(root.get(PARAMETER_ID)));
        TypedQuery<Training> query = entityManager.createQuery(cr);
        return query.setFirstResult(page)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Training findSetsJoinFetch(Long id) throws DAOException {
        TypedQuery<Training> query = entityManager.createQuery
                (SELECT_SETS_TO_SPECIFIC_TRAINING, Training.class)
                .setParameter(PARAMETER_ID, id);
        Training singleResult = query.getSingleResult();
        if (singleResult == null) {
            throw new DAOException("No such training");
        }
        return singleResult;
    }
}
