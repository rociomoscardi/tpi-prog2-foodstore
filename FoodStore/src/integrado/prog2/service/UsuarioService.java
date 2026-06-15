/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrado.prog2.service;

import integrado.prog2.entities.Usuario;
import integrado.prog2.exception.IdDuplicadoException;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Abiga
 */
public class UsuarioService {
    // 1. Declaramos la colección (una Lista) para guardar los usuarios en memoria
    private List<Usuario> listaUsuarios;

    // 2. Creamos el constructor para inicializar la lista vacía cuando se crea este
    // servicio
    public UsuarioService() {
        this.listaUsuarios = new ArrayList<>();
    }

    // 3. Crear: Método para agregar un nuevo usuario a la lista
    // Recibe el usuario armado desde el menú y lo guarda en nuestro almacén
    public void agregarUsuario(Usuario nuevoUsuario) {
        if (nuevoUsuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo.");
        }
        for (Usuario u : listaUsuarios) {
            if (u.getId().equals(nuevoUsuario.getId())) {
                throw new IdDuplicadoException("Ya existe un usuario con ID: " + nuevoUsuario.getId() + ".");
            }
        }
        this.listaUsuarios.add(nuevoUsuario);
    }

    // 4. Leer: Método para obtener la lista completa de usuarios
    // Devuelve nuestro 'almacén' para que el menú lo pueda mostrar
    public List<Usuario> listarUsuarios() {
        List<Usuario> usuariosActivos = new ArrayList<>();
        for (Usuario u : listaUsuarios) {
            if (!u.isEliminado()) {
                usuariosActivos.add(u);
            }
        }
        return usuariosActivos;
    }

    // 5. Utilidad: Buscar un usuario por su ID
    // Nos ayuda a encontrarlo para después editarlo o eliminarlo lógicamente
    public Usuario buscarPorId(Long id) {
        // Recorremos nuestra lista de usuarios uno por uno
        for (Usuario u : listaUsuarios) {
            // Comparamos el ID del usuario u con el ID que buscamos
            if (u.getId().equals(id)) {
                return u; //
            }
        }
        // Recorrimos toda la lista y no encontramos ninguna coincidencia
        System.out.println("DEBUG Error: No se encontró usuario con ID: " + id);
        return null;
    }

    // 6. Actualizar: Método para editar los datos de un usuario existente
    // Usa buscarPorId para encontrarlo y, si existe, cambia sus datos
    public void editarUsuario(Long id, String nuevoNombre, String nuevomail) {
        // Primero, usamos nuestro buscador para encontrar el usuario
        Usuario usuarioAEditar = buscarPorId(id);

        // Si lo encontramos (es distinto de null)
        if (usuarioAEditar != null) {
            // Hacemos los cambios usando los métodos 'set' de la entidad Usuario
            usuarioAEditar.setNombre(nuevoNombre);
            usuarioAEditar.setMail(nuevomail);
            System.out.println("DEBUG: Usuario con ID: " + id + " editado correctamente.");
        } else {
        }
    }

    // 7. Eliminar: Método para borrar un usuario de la lista
    // Busca al usuario por su ID y, si lo encuentra, lo quita del almacén
    public void eliminarUsuario(Long id) {
        Usuario usuarioAEliminar = buscarPorId(id);

        if (usuarioAEliminar != null) {
            // Usamos el método .remove() de las Listas de Java para borrarlo
            this.listaUsuarios.remove(usuarioAEliminar);
            System.out.println("DEBUG: Usuario con ID: " + id + " eliminado correctamente.");
        }
    }

}
