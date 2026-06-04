package integrado.prog2.entities;

public class Producto extends Base {

    private String nombre;
    private Double precio;
    private String descripcion;
    private Integer stock;
    private String imagen;
    private Boolean disponible;
    private Categoria categoria;

    public Producto() {
        super();
        this.disponible = true;
    }

    public Producto(Long id, String nombre, Double precio, String descripcion,
            Integer stock, String imagen, Boolean disponible, Categoria categoria) {
        super(id);
        setNombre(nombre);
        setPrecio(precio);
        setDescripcion(descripcion);
        setStock(stock);
        setImagen(imagen);
        setDisponible(disponible);
        setCategoria(categoria);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        if (precio == null) {
            throw new IllegalArgumentException("El precio no puede ser nulo.");
        }

        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser menor a 0.");
        }

        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción del producto no puede estar vacía.");
        }
        this.descripcion = descripcion;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        if (stock == null) {
            throw new IllegalArgumentException("El stock no puede ser nulo.");
        }

        if (stock < 0) {
            throw new IllegalArgumentException("El stock no puede ser menor a 0.");
        }

        this.stock = stock;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        if (imagen == null) {
            this.imagen = "";
        } else {
            this.imagen = imagen;
        }
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        if (disponible == null) {
            throw new IllegalArgumentException("La disponibilidad no puede ser nula.");
        }
        this.disponible = disponible;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        if (categoria == null) {
            throw new IllegalArgumentException("El producto debe tener una categoría.");
        }

        if (categoria.isEliminado()) {
            throw new IllegalArgumentException("No se puede asignar una categoría eliminada.");
        }

        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Producto{"
                + "id=" + getId()
                + ", nombre='" + nombre + '\''
                + ", precio=" + precio
                + ", stock=" + stock
                + ", disponible=" + disponible
                + ", categoria=" + (categoria != null ? categoria.getNombre() : "Sin categoría") 
                + ", eliminado=" + isEliminado()
                + '}';
    }
}
