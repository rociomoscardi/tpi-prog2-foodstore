package integrado.prog2.exception;

public class IdEliminadoException extends RuntimeException {

    public IdEliminadoException() {
    }

    public IdEliminadoException(String message) {
        super(message);
    }

    public IdEliminadoException(String message, Throwable cause) {
        super(message, cause);
    }

    public IdEliminadoException(Throwable cause) {
        super(cause);
    }

}
