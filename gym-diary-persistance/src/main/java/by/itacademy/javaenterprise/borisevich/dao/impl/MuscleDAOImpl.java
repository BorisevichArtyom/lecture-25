package by.itacademy.javaenterprise.borisevich.dao.impl;

import by.itacademy.javaenterprise.borisevich.dao.MuscleDAO;
import by.itacademy.javaenterprise.borisevich.dao.exception.DAOException;
import by.itacademy.javaenterprise.borisevich.entity.Muscle;
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
public class MuscleDAOImpl implements MuscleDAO {
    private final static String PARAMETER_ID = "id";
    @PersistenceContext
    @Qualifier("entityManagerFactory")
    private EntityManager entityManager;

    @Override
    @Transactional
    public void saveOrUpdate(Muscle muscle) throws DAOException {
        if (muscle.getId() != null) {
            entityManager.merge(muscle);
        } else {
            entityManager.persist(muscle);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Muscle findById(Long id) throws DAOException {
        if (id == null) {
            throw new DAOException("id is null!");
        }
        return entityManager.find(Muscle.class, id);
    }

    @Override
    @Transactional
    public void deleteById(Long id) throws DAOException {
        if (id != null) {
            entityManager.remove(entityManager.find(Muscle.class, id));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Muscle> findAll() throws DAOException {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Muscle> cr = cb.createQuery(Muscle.class);
        Root<Muscle> root = cr.from(Muscle.class);
        cr.select(root);
        Query query = entityManager.createQuery(cr);
        return (List<Muscle>) query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Muscle> findAllPageByPage(int page, int size) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Muscle> cr = cb.createQuery(Muscle.class);
        Root<Muscle> root = cr.from(Muscle.class);
        cr.orderBy(cb.asc(root.get(PARAMETER_ID)));
        TypedQuery<Muscle> query = entityManager.createQuery(cr);
        return query.setFirstResult(page)
                .setMaxResults(size)
                .getResultList();
    }
}
