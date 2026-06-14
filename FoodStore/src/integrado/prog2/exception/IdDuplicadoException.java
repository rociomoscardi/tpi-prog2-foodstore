/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrado.prog2.exception;

/**
 *
 * @author fernando
 */
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
