package by.itacademy.javaenterprise.borisevich.exceptions;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(Long id) {
        super("Entity id not found : " + id);
    }
}
