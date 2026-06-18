package integrado.prog2.service;

import integrado.prog2.entities.Usuario;
import integrado.prog2.exception.IdDuplicadoException;
import integrado.prog2.exception.IdEliminadoException;
import integrado.prog2.exception.IdNoEncontradoException;

import java.util.ArrayList;
import java.util.List;

public class UsuarioService {

    // La colección de usuarios se almacena en memoria utilizando una lista
    private List<Usuario> listaUsuarios;

    // Inicializa la lista vacía de usuarios
    public UsuarioService() {
        this.listaUsuarios = new ArrayList<>();
    }

    // Crear: Método para agregar un nuevo usuario a la lista
    // Recibe el usuario armado desde el menú y lo guarda en nuestro almacén
    public void agregarUsuario(Usuario nuevoUsuario) {
        if (nuevoUsuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo.");
        }
        for (Usuario u : listaUsuarios) {
            if (u.getId().equals(nuevoUsuario.getId())) {
                throw new IdDuplicadoException("Ya existe un usuario con ID: " + nuevoUsuario.getId() + ".");
            }

            if (!u.isEliminado()
                    && u.getMail().equalsIgnoreCase(nuevoUsuario.getMail())) {
                throw new IdDuplicadoException("Ya existe un usuario con el mail: " + nuevoUsuario.getMail() + ".");
            }
        }
        this.listaUsuarios.add(nuevoUsuario);
    }

    // Leer: Método para obtener la lista de usuarios activos
    // Filtra el almacén y devuelve solo los usuarios que NO tienen baja lógica
    public List<Usuario> listarUsuarios() {
        List<Usuario> usuariosActivos = new ArrayList<>();
        for (Usuario u : listaUsuarios) {
            if (!u.isEliminado()) {
                usuariosActivos.add(u);
            }
        }
        return usuariosActivos;
    }

    // Buscar un usuario por su ID
    public Usuario buscarPorId(Long id) {
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getId().equals(id)) {
                return usuario;
            }
        }
        return null;
    }

    // Actualizar: Método para editar los datos de un usuario existente
    // Usa buscarPorId para encontrarlo y, si existe, cambia sus datos
    public void editarUsuario(Long id, String nuevoNombre, String nuevoApellido, String nuevoMail, String nuevoCelular,
            String nuevaContrasenia) {
        Usuario usuarioAEditar = buscarPorId(id);
        if (usuarioAEditar == null) {
            throw new IdNoEncontradoException("No se encontró un usuario con ID: " + id + ".");
        }
        if (usuarioAEditar.isEliminado()) {
            throw new IdEliminadoException("El usuario con ID: " + id + " fue eliminado y no se puede editar.");
        }
        for (Usuario u : listaUsuarios) {
            if (!u.isEliminado()
                    && !u.getId().equals(id)
                    && u.getMail().equalsIgnoreCase(nuevoMail)) {
                throw new IdDuplicadoException("Ya existe un usuario con el mail: " + nuevoMail + ".");
            }
        }
        usuarioAEditar.setNombre(nuevoNombre);
        usuarioAEditar.setApellido(nuevoApellido);
        usuarioAEditar.setMail(nuevoMail);
        usuarioAEditar.setCelular(nuevoCelular);
        usuarioAEditar.setContrasenia(nuevaContrasenia);
    }

    // Eliminar: Método para dar de baja un usuario (Soft Delete)
    // Busca al usuario por su ID y, si lo encuentra, le cambia el estado a
    // eliminado sin borrarlo físicamente
    public void eliminarUsuario(Long id) {
        Usuario usuarioAEliminar = buscarPorId(id);
        if (usuarioAEliminar == null) {
            throw new IdNoEncontradoException("No se encontró un usuario con ID: " + id + ".");
        }
        usuarioAEliminar.eliminar();
    }

}
