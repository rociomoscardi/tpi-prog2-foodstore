package integrado.prog2.service;

import integrado.prog2.exception.*;
import integrado.prog2.entities.Categoria;
import integrado.prog2.entities.Producto;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProductoService {

    // La colección de productos se almacena en memoria utilizando una lista.
    private List<Producto> productos = new ArrayList<>();

    public void agregarProducto(Producto nuevoProducto) {
        if (nuevoProducto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo.");
        }
        for (Producto producto : productos) {
            if (producto.getId().equals(nuevoProducto.getId())) {
                throw new IdDuplicadoException(
                        "Ya existe un producto con ID:" + nuevoProducto.getId()
                                + ". No se pueden agregar productos con IDs duplicados.");
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
                productoAEliminar.eliminar();

                return;
            }
        }

        throw new IdNoEncontradoException("No se encontró un producto con ID: " + productoId + ".");

    }

    public void editarProducto(Long productoId, double precio, int stock, Categoria categoria) {
        for (Producto productoActualizar : productos) {

            if (productoActualizar.getId().equals(productoId)) {

                if (productoActualizar.isEliminado()) {
                    throw new IdEliminadoException(
                            "El producto con ID: " + productoId + " fue eliminado y. No se puede editar");
                }

                productoActualizar.setPrecio(precio);
                productoActualizar.setStock(stock);
                productoActualizar.setCategoria(categoria);

                return;

            }

        }
        throw new IdNoEncontradoException("No se encontró un producto con ID: " + productoId + ".");

    }

    public List<Producto> listarProductos() {
        List<Producto> productosDisponibles = new ArrayList<>();
        for (Producto producto : productos) {
            if (producto.getDisponible() && !producto.isEliminado()) {
                productosDisponibles.add(producto);
            }
        }

        return productosDisponibles;
    }

    // Método adicional para buscar un producto por su ID. Devuelve el producto si
    // se encuentra, o null si no se encuentra.
    public Producto buscarPorId(Long idProd) {
        for (Producto producto : productos) {
            if (producto.getId().equals(idProd)) {
                return producto;
            }
        }
        return null;
    }

}
