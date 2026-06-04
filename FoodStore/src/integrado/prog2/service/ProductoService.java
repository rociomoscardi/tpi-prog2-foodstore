/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrado.prog2.service;

import integrado.prog2.entities.Producto;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author fernando
 */
public class ProductoService {
    private List<Producto> productos = new ArrayList<>();
    
    public void agregarProducto(Producto nuevoProducto){
           for (Producto producto : productos) {
            if(producto.getId().equals(nuevoProducto.getId())){
                throw new IllegalArgumentException("El producto que quieres agregar ya existe.");
            }
        }
        productos.add(nuevoProducto);
    }
    
        public void eliminarProducto(Long productoId){
           Iterator<Producto> iterator = productos.iterator();

           while(iterator.hasNext()){
               Producto productoAEliminar = iterator.next();

               if(productoAEliminar.getId().equals(productoId)){
                   iterator.remove();
                   return;
               }
           }

           throw new IllegalArgumentException("El producto a eliminar no se encontro");

        }

    
   
    
    
}
