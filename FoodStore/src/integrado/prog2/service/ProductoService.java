/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrado.prog2.service;

import integrado.prog2.exception.*;
import integrado.prog2.entities.Categoria;
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

    public void agregarProducto(Producto nuevoProducto) {
        for (Producto producto : productos) {
            if (producto.getId().equals(nuevoProducto.getId())) {
                throw new IdDuplicadoExcepcion("El producto con el ID:" + nuevoProducto.getId() +  "que quieres agregar ya existe.");
            }
        }
        productos.add(nuevoProducto);
    }

    public void eliminarProducto(Long productoId) {
        Iterator<Producto> iterator = productos.iterator();

        while (iterator.hasNext()) {
            Producto productoAEliminar = iterator.next();

            if (productoAEliminar.getId().equals(productoId)) {
                productoAEliminar.setDisponible(false);
                productoAEliminar.setEliminado(true);
              
                return;
            }
        }

        throw new IdNoEncontradoExcepcion("El ID: " + productoId + " no se encontro para eliminar el producto.");

    }

    public void editarProducto(Long productoId, double precio, int stock, Categoria categoria) {
        for (Producto productoActualizar : productos) {
            
            if (productoActualizar.getId().equals(productoId)) {
                
                if(productoActualizar.isEliminado()==true){
                    throw new IdEliminadoException("El producto con el ID:"+ productoId + " fue removido de la lista y no se puede editar");
                }
                
                productoActualizar.setPrecio(precio);
                productoActualizar.setStock(stock);
                productoActualizar.setCategoria(categoria);
                
               
                return;
                
            }
            
           
            
            
        }
        throw new IdNoEncontradoExcepcion("Error. El ID:" + productoId + " no se encontro." );

    }
    
    
    
    public List<Producto> listarProductos(){
        List<Producto> productosDisponibles = new ArrayList<>();
        for (Producto producto : productos) {
            if(producto.getDisponible()==true && producto.isEliminado() == false){
                productosDisponibles.add(producto);
            }
        }
        
        
        return productosDisponibles;
    }

}
