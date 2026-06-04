package integrado.prog2.entities;

import java.util.ArrayList;
import java.util.List;

public class Categoria extends Base {

    private String nombre;
    private String descripcion;
    private List<Producto> productos;

    public Categoria() {
        super();
        this.productos = new ArrayList<>();
    }

    public Categoria(Long id, String nombre, String descripcion) {
        super(id);
        setNombre(nombre);
        setDescripcion(descripcion);
        this.productos = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoría no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción de la categoría no puede estar vacía.");
        }
        this.descripcion = descripcion;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        if (productos == null) {
            throw new IllegalArgumentException("La lista de productos no puede ser nula.");
        }
        this.productos = productos;
    }

    public void agregarProducto(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo.");
        }
        this.productos.add(producto);
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + getId() +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", eliminado=" + isEliminado() +
                '}';
    }
}