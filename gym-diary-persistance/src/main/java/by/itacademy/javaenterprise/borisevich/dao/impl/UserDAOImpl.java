package by.itacademy.javaenterprise.borisevich.dao.impl;

import by.itacademy.javaenterprise.borisevich.dao.UserDAO;
import by.itacademy.javaenterprise.borisevich.dao.exception.DAOException;
import by.itacademy.javaenterprise.borisevich.entity.User;
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
import java.util.Optional;

@Slf4j
@Repository
public class UserDAOImpl implements UserDAO {
    private static final String FIND_USER_WHERE_LASTNAME = "SELECT u FROM User u WHERE u.lastName=:lastName";
    private static final String FIND_USER_WHERE_EMAIL = "SELECT u FROM User u WHERE u.email=:email";
    private static final String FIND_USER_QUERY_PARAMETER = "lastName";
    private static final String EXISTS_EMAIL_QUERY_PARAMETER = "email";
    private final static String PARAMETER_ID = "id";
    @PersistenceContext
    @Qualifier("entityManagerFactory")
    private EntityManager entityManager;

    @Override
    @Transactional
    public void saveOrUpdate(User user) throws DAOException {
        if (user.getId() != null) {
            entityManager.merge(user);
        } else {
            entityManager.persist(user);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) throws DAOException {
        if (id == null) {
            throw new DAOException("id is null!");
        }
        return entityManager.find(User.class, id);
    }

    @Override
    @Transactional
    public void deleteById(Long id) throws DAOException {
        if (id != null) {
            entityManager.remove(entityManager.find(User.class, id));
        }
    }

    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public List<User> findAll() throws DAOException {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cr = cb.createQuery(User.class);
        Root<User> root = cr.from(User.class);
        cr.select(root);
        Query query = entityManager.createQuery(cr);
        return (List<User>) query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllPageByPage(int page, int size) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cr = cb.createQuery(User.class);
        Root<User> root = cr.from(User.class);
        cr.orderBy(cb.asc(root.get(PARAMETER_ID)));
        TypedQuery<User> query = entityManager.createQuery(cr);
        return query.setFirstResult(page)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public Optional<User> findByUsername(String username) {
        Query query = entityManager.createQuery(FIND_USER_WHERE_LASTNAME).setParameter(FIND_USER_QUERY_PARAMETER, username);
        return (Optional<User>) query.getResultStream().findAny();
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existsByUsername(String username) {
        Query query = entityManager.createQuery(FIND_USER_WHERE_LASTNAME).setParameter(FIND_USER_QUERY_PARAMETER, username);
        return query.getResultStream().findAny().isPresent();
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existsByEmail(String email) {
        Query query = entityManager.createQuery(FIND_USER_WHERE_EMAIL).setParameter(EXISTS_EMAIL_QUERY_PARAMETER, email);
        return query.getResultStream().findAny().isPresent();
    }

}
