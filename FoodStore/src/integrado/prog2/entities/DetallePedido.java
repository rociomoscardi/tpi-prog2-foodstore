package integrado.prog2.entities;

public class DetallePedido extends Base {

    // Atributos privados
    private int cantidad;
    private Double subtotal;
    private Producto producto;

    // Constructores
    public DetallePedido() {
        super();
        this.subtotal = 0.0;
    }

    public DetallePedido(Long id, int cantidad, Producto producto) {
        super(id);
        setCantidad(cantidad);
        setProducto(producto);
        calcularSubtotal();
    }

    // Getters y Setters
    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0.");
        }

        this.cantidad = cantidad;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        if (subtotal == null) {
            throw new IllegalArgumentException("El subtotal no debe ser nulo.");
        }

        if (subtotal < 0) {
            throw new IllegalArgumentException("El subtotal no puede ser negativo.");
        }

        this.subtotal = subtotal;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto del detalle no puede ser nulo.");
        }

        if (producto.isEliminado()) {
            throw new IllegalArgumentException("No se puede agregar un producto eliminado.");
        }

        if (!producto.getDisponible()) {
            throw new IllegalArgumentException("No se puede agregar un producto no disponible.");
        }

        this.producto = producto;
    }

    public void calcularSubtotal() {
        if (producto == null) {
            throw new IllegalArgumentException("No se puede calcular el subtotal sin producto.");
        }

        this.subtotal = cantidad * producto.getPrecio();
    }

    // Método toString para mostrar la información del detalle del pedido
    @Override
    public String toString() {
        return "ID: " + getId() +
                " | Producto: " + (producto != null ? producto.getNombre() : "Sin producto") +
                " | Cantidad: " + cantidad +
                " | Subtotal: $" + subtotal;
    }
}