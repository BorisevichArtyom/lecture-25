package by.itacademy.javaenterprise.borisevich.dao;

import by.itacademy.javaenterprise.borisevich.entity.Role;
import by.itacademy.javaenterprise.borisevich.entity.RoleType;

import java.util.Optional;

public interface RoleDAO extends DAO<Role> {
    Optional<Role> findByName(RoleType roleType);
}
