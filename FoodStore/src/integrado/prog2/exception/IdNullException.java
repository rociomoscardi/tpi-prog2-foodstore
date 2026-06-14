/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrado.prog2.exception;

/**
 *
 * @author fernando
 */
public class IdNullException extends RuntimeException{

    public IdNullException() {
    }

    public IdNullException(String message) {
        super(message);
    }

    public IdNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public IdNullException(Throwable cause) {
        super(cause);
    }
    
}
