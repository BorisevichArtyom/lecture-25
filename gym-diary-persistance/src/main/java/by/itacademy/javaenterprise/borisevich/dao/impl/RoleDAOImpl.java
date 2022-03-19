package by.itacademy.javaenterprise.borisevich.dao.impl;

import by.itacademy.javaenterprise.borisevich.dao.RoleDAO;
import by.itacademy.javaenterprise.borisevich.dao.exception.DAOException;
import by.itacademy.javaenterprise.borisevich.entity.Role;
import by.itacademy.javaenterprise.borisevich.entity.RoleType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class RoleDAOImpl implements RoleDAO {
    private static final String FIND_ROLE_WHERE_NAME = "SELECT u FROM Role u WHERE u.name=:name";
    private static final String FIND_ROLE_QUERY_PARAMETER = "name";

    @PersistenceContext
    @Qualifier("entityManagerFactory")
    private EntityManager entityManager;

    @Override
    @Transactional
    public void saveOrUpdate(Role roleType) throws DAOException {
        if (roleType.getId() != null) {
            entityManager.merge(roleType);
        } else {
            entityManager.persist(roleType);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Role findById(Long id) throws DAOException {
        if (id == null) {
            throw new DAOException("id is null!");
        }
        return entityManager.find(Role.class, id);
    }

    @Override
    @Transactional
    public void deleteById(Long id) throws DAOException {
        if (id != null) {
            entityManager.remove(entityManager.find(Role.class, id));
        }
    }

    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public List<Role> findAll() throws DAOException {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Role> cr = cb.createQuery(Role.class);
        Root<Role> root = cr.from(Role.class);
        cr.select(root);
        Query query = entityManager.createQuery(cr);
        return (List<Role>) query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public Optional<Role> findByName(RoleType name) {
        Query query = entityManager.createQuery(FIND_ROLE_WHERE_NAME).setParameter(FIND_ROLE_QUERY_PARAMETER, name);
        return (Optional<Role>) query.getResultStream().findAny();
    }
}
