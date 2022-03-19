package by.itacademy.javaenterprise.borisevich.dao.impl;

import by.itacademy.javaenterprise.borisevich.dao.TrainingSetDAO;
import by.itacademy.javaenterprise.borisevich.dao.exception.DAOException;
import by.itacademy.javaenterprise.borisevich.entity.TrainingSet;
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

@Repository
@Slf4j
public class TrainingSetDAOImpl implements TrainingSetDAO {
    private final static String COUNT_ALL = "select count(m) from TrainingSet";
    private final static String PARAMETER_ID = "id";
    @PersistenceContext
    @Qualifier("entityManagerFactory")
    private EntityManager entityManager;

    @Override
    @Transactional
    public void saveOrUpdate(TrainingSet trainingSet) throws DAOException {
        if (trainingSet.getId() != null) {
            entityManager.merge(trainingSet);
        } else {
            entityManager.persist(trainingSet);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TrainingSet findById(Long id) throws DAOException {
        if (id == null) {
            throw new DAOException("id is null!");
        }
        return entityManager.find(TrainingSet.class, id);
    }

    @Override
    @Transactional
    public void deleteById(Long id) throws DAOException {
        if (id != null) {
            TrainingSet trainingSet = entityManager.find(TrainingSet.class, id);
            log.info("Entity to delete" + trainingSet);
            entityManager.remove(trainingSet);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrainingSet> findAll() throws DAOException {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TrainingSet> cr = cb.createQuery(TrainingSet.class);
        Root<TrainingSet> root = cr.from(TrainingSet.class);
        cr.select(root);
        Query query = entityManager.createQuery(cr);
        return (List<TrainingSet>) query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return entityManager
                .createQuery(COUNT_ALL, Long.class)
                .getSingleResult();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrainingSet> findAllPageByPage(int page, int size) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TrainingSet> cr = cb.createQuery(TrainingSet.class);
        Root<TrainingSet> root = cr.from(TrainingSet.class);
        cr.orderBy(cb.asc(root.get(PARAMETER_ID)));
        TypedQuery<TrainingSet> query = entityManager.createQuery(cr);
        return query.setFirstResult(page)
                .setMaxResults(size)
                .getResultList();
    }
}
