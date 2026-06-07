/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrado.prog2.service;

import Exceptions.IdDuplicadoExcepcion;
import Exceptions.IdNoEncontradoExcepcion;
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
                return;
            }
        }

        throw new IdNoEncontradoExcepcion("El ID: " + productoId + "no se encontro para eliminar el producto.");

    }

    public void editarProducto(Long productoId, Producto producto) {
        for (Producto productoActualizar : productos) {
            
            if (productoActualizar.getId().equals(productoId)) {
                
                productoActualizar.setPrecio(producto.getPrecio());
                productoActualizar.setStock(producto.getStock());
                productoActualizar.setCategoria(producto.getCategoria());
                
                return;
                
            }
            
        }
        throw new IdNoEncontradoExcepcion("Error. El ID:" + productoId + "no se encontro." );

    }
    
    
    
    public List<Producto> listarProductos(){
        List<Producto> productosDisponibles = new ArrayList<>();
        for (Producto producto : productos) {
            if(producto.getDisponible()){
                productosDisponibles.add(producto);
            }
        }
        
        
        return productosDisponibles;
    }

}
