package by.itacademy.javaenterprise.borisevich.dao;

import by.itacademy.javaenterprise.borisevich.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO extends DAO<User> {
    List<User> findAllPageByPage(int page, int size);
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
