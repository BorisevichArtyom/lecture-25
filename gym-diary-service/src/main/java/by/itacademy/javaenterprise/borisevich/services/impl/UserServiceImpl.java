package by.itacademy.javaenterprise.borisevich.services.impl;

import by.itacademy.javaenterprise.borisevich.dao.UserDAO;
import by.itacademy.javaenterprise.borisevich.dao.exception.DAOException;
import by.itacademy.javaenterprise.borisevich.entity.User;
import by.itacademy.javaenterprise.borisevich.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService<User> {
    @Autowired
    private UserDAO userDAO;

    @Override
    public void save(User user) {
        try {
            userDAO.saveOrUpdate(user);
        } catch (DAOException e) {
            log.info("Error with saving", e);
        }
    }

    @Override
    public User showOne(Long id) {
        User user = null;
        try {
            user = userDAO.findById(id);
        } catch (DAOException e) {
            log.info("Error  with finding this id", e);
        }
        return user;
    }

    @Override
    public void delete(Long id) {
        try {
            userDAO.deleteById(id);
        } catch (DAOException e) {
            log.info("Error with delete", e);
        }
    }

    @Override
    public List<User> showAll() {
        List<User> users = null;
        try {
            users = userDAO.findAll();
        } catch (DAOException e) {
            log.info("Cant show muscles", e);
        }
        return users;
    }

    @Override
    public List<User> showAllPageByPage(int page, int size) {
       return userDAO.findAllPageByPage(page, size);
    }
}
