package by.itacademy.javaenterprise.borisevich.exceptions;

public class EntityUnSupportedFieldPatchException extends RuntimeException{
    public EntityUnSupportedFieldPatchException() {
        super("Field " + "" + " update is not allow.");
    }
}
