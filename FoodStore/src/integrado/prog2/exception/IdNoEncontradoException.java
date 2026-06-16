package integrado.prog2.exception;

public class IdNoEncontradoException extends RuntimeException {

    public IdNoEncontradoException() {
    }

    public IdNoEncontradoException(String message) {
        super(message);
    }

    public IdNoEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }

    public IdNoEncontradoException(Throwable cause) {
        super(cause);
    }

}
