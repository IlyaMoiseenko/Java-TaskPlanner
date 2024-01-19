package by.moiseenko.javataskplanner.exception;

/*
    @author Ilya Moiseenko on 19.01.24
*/

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
