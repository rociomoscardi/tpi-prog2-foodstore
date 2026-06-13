/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrado.prog2.exception;

/**
 *
 * @author fernando
 */
public class IdEliminadoExecption extends RuntimeException{

    public IdEliminadoExecption() {
    }

    public IdEliminadoExecption(String message) {
        super(message);
    }

    public IdEliminadoExecption(String message, Throwable cause) {
        super(message, cause);
    }

    public IdEliminadoExecption(Throwable cause) {
        super(cause);
    }
    
}
