package integrado.prog2.service;

import integrado.prog2.entities.Pedido;
import integrado.prog2.entities.Producto;
import integrado.prog2.enums.Estado;
import integrado.prog2.enums.FormaPago;
import integrado.prog2.exception.IdDuplicadoException;
import integrado.prog2.exception.IdEliminadoException;
import integrado.prog2.exception.IdNoEncontradoException;

import java.util.ArrayList;
import java.util.List;

public class PedidoService {
    // La colección de pedidos se almacena en memoria utilizando una lista
    private final List<Pedido> listaPedidos;

    public PedidoService() {
        this.listaPedidos = new ArrayList<>();
    }

    // Crear Pedido (Inicia vacío, solo con Usuario y Forma de Pago)
    public void crearPedido(Pedido nuevoPedido) {
        if (nuevoPedido == null) {
            throw new IllegalArgumentException("El pedido no puede ser nulo.");
        }
        for (Pedido p : listaPedidos) {
            if (p.getId().equals(nuevoPedido.getId())) {
                throw new IdDuplicadoException("Ya existe un pedido con ID: " + nuevoPedido.getId() + ".");
            }
        }
        this.listaPedidos.add(nuevoPedido);
    }

    // Listar pedidos activos
    public List<Pedido> listarPedidos() {
        List<Pedido> pedidosActivos = new ArrayList<>();
        for (Pedido p : listaPedidos) {
            if (!p.isEliminado()) {
                pedidosActivos.add(p);
            }
        }
        return pedidosActivos;
    }

    // Buscar pedido por ID
    public Pedido buscarPorId(Long id) {
        for (Pedido p : listaPedidos) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    // Cambiar Estado del Pedido (ej: de PENDIENTE a CONFIRMADO)
    public void cambiarEstadoPedido(Long id, Estado nuevoEstado) {
        Pedido pedido = buscarPorId(id);
        if (pedido == null) {
            throw new IdNoEncontradoException("No se encontró un pedido con ID: " + id + ".");
        }
        if (pedido.isEliminado()) {
            throw new IdEliminadoException("El pedido con ID: " + id + " está eliminado.");
        }
        pedido.setEstado(nuevoEstado);
    }

    // Cambiar la forma de pago de un pedido existente
    // Valida que el pedido exista y no esté eliminado antes de modificarlo
    public void cambiarFormaPagoPedido(Long id, FormaPago nuevaFormaPago) {
        Pedido pedido = buscarPorId(id);
        if (pedido == null) {
            throw new IdNoEncontradoException("No se encontró un pedido con ID: " + id + ".");
        }
        if (pedido.isEliminado()) {
            throw new IdEliminadoException("El pedido con ID: " + id + " está eliminado.");
        }
        pedido.setFormaPago(nuevaFormaPago);
    }

    // Eliminar Pedido (Baja Lógica)
    public void eliminarPedido(Long id) {
        Pedido pedido = buscarPorId(id);
        if (pedido == null) {
            throw new IdNoEncontradoException("No se encontró un pedido con ID: " + id + ".");
        }
        pedido.eliminar();
    }

    // --- MÉTODOS PARA GESTIONAR EL CARRITO (Detalles del Pedido) ---

    // Agregar un producto al pedido
    public void agregarProductoAPedido(Long idPedido, Producto producto, int cantidad) {
        Pedido pedido = buscarPorId(idPedido);
        if (pedido == null) {
            throw new IdNoEncontradoException("No se encontró un pedido con ID: " + idPedido + ".");
        }
        if (pedido.isEliminado()) {
            throw new IdEliminadoException("El pedido está eliminado. No se pueden agregar productos.");
        }
        if (pedido.getEstado() != Estado.PENDIENTE) {
            throw new IllegalArgumentException("Solo se pueden agregar productos a pedidos PENDIENTES.");
        }

        // El pedido mismo se encarga de las validaciones de stock y subtotal a través
        // de este método
        pedido.addDetallePedido(cantidad, null, producto);
    }

    // Quitar un producto del pedido
    public void quitarProductoDePedido(Long idPedido, Producto producto) {
        Pedido pedido = buscarPorId(idPedido);
        if (pedido == null) {
            throw new IdNoEncontradoException("No se encontró un pedido con ID: " + idPedido + ".");
        }
        // El pedido hace la baja lógica del detalle
        pedido.deleteDetallePedidoByProducto(producto);
    }
}
