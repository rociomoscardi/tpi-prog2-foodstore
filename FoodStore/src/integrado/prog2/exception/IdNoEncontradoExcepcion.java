/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrado.prog2.exception;

/**
 *
 * @author fernando
 */
public class IdNoEncontradoExcepcion extends RuntimeException{

    public IdNoEncontradoExcepcion() {
    }

    public IdNoEncontradoExcepcion(String message) {
        super(message);
    }

    public IdNoEncontradoExcepcion(String message, Throwable cause) {
        super(message, cause);
    }

    public IdNoEncontradoExcepcion(Throwable cause) {
        super(cause);
    }
    
    
    
}
