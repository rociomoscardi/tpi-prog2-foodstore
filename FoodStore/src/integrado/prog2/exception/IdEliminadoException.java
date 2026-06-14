/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrado.prog2.exception;

/**
 *
 * @author fernando
 */
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
