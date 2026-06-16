package integrado.prog2.entities;

import integrado.prog2.enums.Rol;
import java.util.ArrayList;
import java.util.List;

public class Usuario extends Base {

    // Atributos privados
    private String nombre;
    private String apellido;
    private String mail;
    private String celular;
    private String contrasenia;
    private Rol rol;
    private List<Pedido> pedidos;

    // Constructores
    public Usuario() {
        super();
        this.rol = Rol.USUARIO;
        this.pedidos = new ArrayList<>();
    }

    public Usuario(Long id, String nombre, String apellido, String mail,
            String celular, String contrasenia, Rol rol) {
        super(id);
        setNombre(nombre);
        setApellido(apellido);
        setMail(mail);
        setCelular(celular);
        setContrasenia(contrasenia);
        setRol(rol);
        this.pedidos = new ArrayList<>();
    }

    // Getters y Setters con validaciones
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede estar vacío.");
        }
        this.apellido = apellido;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        if (mail == null || mail.trim().isEmpty()) {
            throw new IllegalArgumentException("El mail no puede estar vacío.");
        }

        if (!mail.contains("@")) {
            throw new IllegalArgumentException("El mail debe tener un formato válido. Ejemplo: usuario@dominio.com");
        }

        this.mail = mail;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        if (celular == null || celular.trim().isEmpty()) {
            throw new IllegalArgumentException("El número de celular no puede estar vacío.");
        }
        this.celular = celular;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        if (contrasenia == null || contrasenia.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía.");
        }
        this.contrasenia = contrasenia;
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

    // Método toString para mostrar información del usuario
    @Override
    public String toString() {
        return "ID: " + getId() +
                " | " + nombre + " " + apellido +
                " | Mail: " + mail +
                " | Celular: " + celular +
                " | Rol: " + rol;
    }
}