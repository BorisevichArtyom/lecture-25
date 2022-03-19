package by.itacademy.javaenterprise.borisevich.services.impl;

import by.itacademy.javaenterprise.borisevich.dao.RoleDAO;
import by.itacademy.javaenterprise.borisevich.dao.exception.DAOException;
import by.itacademy.javaenterprise.borisevich.entity.Role;
import by.itacademy.javaenterprise.borisevich.services.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService<Role> {
    @Autowired
    private RoleDAO roleDAO;

    @Override
    public void save(Role role) {
        try {
            roleDAO.saveOrUpdate(role);
        } catch (DAOException e) {
            log.info("Error with save", e);
        }
    }

    @Override
    public Role showOne(Long id) {
        Role role = null;
        try {
            role = roleDAO.findById(id);
        } catch (DAOException e) {
            log.info("Error with finding this id", e);
        }
        return role;
    }

    @Override
    public void delete(Long id) {
        try {
            roleDAO.deleteById(id);
        } catch (DAOException e) {
            log.info("Error with delete", e);
        }
    }

    @Override
    public List<Role> showAll() {
        List<Role> roles = null;
        try {
            roles = roleDAO.findAll();
        } catch (DAOException e) {
            log.info("Cant show roles", e);
        }
        return roles;
    }
}
