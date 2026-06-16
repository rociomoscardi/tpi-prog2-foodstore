package integrado.prog2.exception;

public class IdDuplicadoException extends RuntimeException {

    public IdDuplicadoException() {
    }

    public IdDuplicadoException(String message) {
        super(message);
    }

    public IdDuplicadoException(String message, Throwable cause) {
        super(message, cause);
    }

    public IdDuplicadoException(Throwable cause) {
        super(cause);
    }

}
