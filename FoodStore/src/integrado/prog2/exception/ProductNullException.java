/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrado.prog2.exception;

/**
 *
 * @author fernando
 */
public class ProductNullException extends RuntimeException{

    public ProductNullException() {
    }

    public ProductNullException(String message) {
        super(message);
    }

    public ProductNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductNullException(Throwable cause) {
        super(cause);
    }
    
}
