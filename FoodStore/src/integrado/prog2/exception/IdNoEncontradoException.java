/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrado.prog2.exception;

/**
 *
 * @author fernando
 */
public class IdNoEncontradoException extends RuntimeException{

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
