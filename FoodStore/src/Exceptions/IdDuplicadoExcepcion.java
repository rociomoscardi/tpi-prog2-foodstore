/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exceptions;

/**
 *
 * @author fernando
 */
public class IdDuplicadoExcepcion extends RuntimeException{

    public IdDuplicadoExcepcion() {
    }

    public IdDuplicadoExcepcion(String message) {
        super(message);
    }

    public IdDuplicadoExcepcion(String message, Throwable cause) {
        super(message, cause);
    }

    public IdDuplicadoExcepcion(Throwable cause) {
        super(cause);
    }
    
    
    
}
