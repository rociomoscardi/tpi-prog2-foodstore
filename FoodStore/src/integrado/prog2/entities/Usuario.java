package integrado.prog2.entities;

import integrado.prog2.enums.Rol;
import java.util.ArrayList;
import java.util.List;

public class Usuario extends Base {

    private String nombre;
    private String apellido;
    private String mail;
    private String celular;
    private String contrasena;
    private Rol rol;
    private List<Pedido> pedidos;

    public Usuario() {
        super();
        this.rol = Rol.USUARIO;
        this.pedidos = new ArrayList<>();
    }

    public Usuario(Long id, String nombre, String apellido, String mail,
            String celular, String contrasena, Rol rol) {
        super(id);
        setNombre(nombre);
        setApellido(apellido);
        setMail(mail);
        setCelular(celular);
        setContrasena(contrasena);
        setRol(rol);
        this.pedidos = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del usuario no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido del usuario no puede estar vacío.");
        }
        this.apellido = apellido;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        if (mail == null || mail.trim().isEmpty()) {
            throw new IllegalArgumentException("El mail del usuario no puede estar vacío.");
        }

        if (!mail.contains("@")) {
            throw new IllegalArgumentException("El mail debe tener un formato válido.");
        }

        this.mail = mail;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        if (celular == null || celular.trim().isEmpty()) {
            throw new IllegalArgumentException("El celular del usuario no puede estar vacío.");
        }
        this.celular = celular;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        if (contrasena == null || contrasena.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía.");
        }
        this.contrasena = contrasena;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        if (rol == null) {
            throw new IllegalArgumentException("El rol no puede ser nulo.");
        }
        this.rol = rol;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        if (pedidos == null) {
            throw new IllegalArgumentException("La lista de pedidos no puede ser nula.");
        }
        this.pedidos = pedidos;
    }

    public void agregarPedido(Pedido pedido) {
        if (pedido == null) {
            throw new IllegalArgumentException("El pedido no puede ser nulo.");
        }
        this.pedidos.add(pedido);
    }

    @Override
    public String toString() {
        return "ID: " + getId() +
                " | " + nombre + " " + apellido +
                " | Mail: " + mail +
                " | Celular: " + celular +
                " | Rol: " + rol;
    }
}