package integrado.prog2.entities;

import integrado.prog2.enums.Estado;
import integrado.prog2.enums.FormaPago;
import integrado.prog2.interfaces.Calculable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido extends Base implements Calculable {

    // Atributos privados
    private LocalDate fecha;
    private Estado estado;
    private Double total;
    private FormaPago formaPago;
    private Usuario usuario;
    private List<DetallePedido> detalles;

    // Constructores
    public Pedido() {
        super();
        this.fecha = LocalDate.now();
        this.estado = Estado.PENDIENTE;
        this.total = 0.0;
        this.detalles = new ArrayList<>();
    }

    public Pedido(Long id, Usuario usuario, FormaPago formaPago) {
        super(id);
        this.fecha = LocalDate.now();
        this.estado = Estado.PENDIENTE;
        this.total = 0.0;
        setUsuario(usuario);
        setFormaPago(formaPago);
        this.detalles = new ArrayList<>();
    }

    // Getters y Setters con validaciones
    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha del pedido no puede ser nula.");
        }
        this.fecha = fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        if (estado == null) {
            throw new IllegalArgumentException("El estado del pedido no puede ser nulo.");
        }
        this.estado = estado;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        if (total == null) {
            throw new IllegalArgumentException("El total no puede ser nulo.");
        }

        if (total < 0) {
            throw new IllegalArgumentException("El total no puede ser negativo.");
        }

        this.total = total;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        if (formaPago == null) {
            throw new IllegalArgumentException("La forma de pago no puede ser nula.");
        }
        this.formaPago = formaPago;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("El pedido debe tener un usuario asociado.");
        }

        if (usuario.isEliminado()) {
            throw new IllegalArgumentException("No se puede crear un pedido para un usuario eliminado.");
        }

        this.usuario = usuario;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedido> detalles) {
        if (detalles == null) {
            throw new IllegalArgumentException("La lista de detalles no puede ser nula.");
        }
        this.detalles = detalles;
        calcularTotal();
    }

    public void addDetallePedido(int cantidad, Double subtotal, Producto producto) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0.");
        }

        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo.");
        }

        if (producto.isEliminado()) {
            throw new IllegalArgumentException("No se puede agregar un producto eliminado.");
        }

        if (!producto.getDisponible()) {
            throw new IllegalArgumentException("No se puede agregar un producto no disponible.");
        }

        if (producto.getStock() < cantidad) {
            throw new IllegalArgumentException("No hay stock suficiente para el producto: " + producto.getNombre());
        }

        DetallePedido detalle = new DetallePedido();
        detalle.setCantidad(cantidad);
        detalle.setProducto(producto);

        if (subtotal == null || subtotal < 0) {
            detalle.calcularSubtotal();
        } else {
            detalle.setSubtotal(subtotal);
        }

        this.detalles.add(detalle);

        producto.setStock(producto.getStock() - cantidad);

        calcularTotal();
    }

    public DetallePedido findDetallePedidoByProducto(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo.");
        }

        for (DetallePedido detalle : detalles) {
            if (!detalle.isEliminado() && detalle.getProducto().equals(producto)) {
                return detalle;
            }
        }

        return null;
    }

    public void deleteDetallePedidoByProducto(Producto producto) {
        DetallePedido detalle = findDetallePedidoByProducto(producto);

        if (detalle == null) {
            throw new IllegalArgumentException("No existe un detalle para el producto ingresado.");
        }

        detalle.eliminar();
        calcularTotal();
    }

    @Override
    public void calcularTotal() {
        double suma = 0.0;

        for (DetallePedido detalle : detalles) {
            if (!detalle.isEliminado()) {
                suma += detalle.getSubtotal();
            }
        }

        this.total = suma;
    }

    // Método toString para mostrar información del pedido
    @Override
    public String toString() {
        return "ID: " + getId() +
                " | Fecha: " + fecha +
                " | Estado: " + estado +
                " | Total: $" + total +
                " | Forma de pago: " + formaPago +
                " | Usuario: " + (usuario != null ? usuario.getNombre() + " " + usuario.getApellido() : "Sin usuario");
    }
}
