package integrado.prog2.service;

import integrado.prog2.entities.Categoria;
import integrado.prog2.exception.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaService {

    // La colección de categorías se almacena en memoria utilizando una lista.
    private List<Categoria> categorias = new ArrayList<>();

    public List<Categoria> listarCategorias() {
        List<Categoria> categoriasDisponibles = new ArrayList<>();
        for (Categoria categoriaDisponible : categorias) {
            if (!categoriaDisponible.isEliminado()) {
                categoriasDisponibles.add(categoriaDisponible);
            }
        }
        return categoriasDisponibles;
    }

    public void eliminarCategoria(Long categoriaId) {
        if (categoriaId == null) {
            throw new IllegalArgumentException("El ID de la categoría no puede ser nulo.");
        }
        for (Categoria categoria : categorias) {
            if (categoria.getId().equals(categoriaId)) {
                categoria.eliminar();
                return;
            }
        }
        throw new IdNoEncontradoException("No se encontró una categoría con ID: " + categoriaId + ".");
    }

    public void agregarCategoria(Categoria categoria) {
        if (categoria == null) {
            throw new IllegalArgumentException("La categoría no puede ser nula.");
        }
        for (Categoria categoriaAgregar : categorias) {
            if (categoriaAgregar.getId().equals(categoria.getId())) {
                throw new IdDuplicadoException("Ya existe una categoría con ID: " + categoria.getId() + ".");
            }

            if (!categoriaAgregar.isEliminado()
                    && categoriaAgregar.getNombre().equalsIgnoreCase(categoria.getNombre())) {
                throw new IdDuplicadoException("Ya existe una categoría con el nombre: " + categoria.getNombre() + ".");
            }
        }
        categorias.add(categoria);
    }

    public void editarCategoria(Long categoriaId, String nombre, String descripcion) {
        if (categoriaId == null) {
            throw new IllegalArgumentException("El ID de la categoria no puede ser nulo.");
        }

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoria no puede estar vacio.");
        }

        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripcion de la categoria no puede estar vacia.");
        }

        nombre = nombre.trim();
        descripcion = descripcion.trim();

        for (Categoria categoriaEditar : categorias) {
            if (categoriaEditar.getId().equals(categoriaId)) {

                if (categoriaEditar.isEliminado()) {
                    throw new IdEliminadoException(
                            "La categoria con ID: " + categoriaEditar.getId() + " fue eliminada. No se puede editar.");
                }

                for (Categoria c : categorias) {
                    if (!c.isEliminado()
                            && !c.getId().equals(categoriaId)
                            && c.getNombre().equalsIgnoreCase(nombre)) {
                        throw new IdDuplicadoException("Ya existe una categoria con el nombre: " + nombre + ".");
                    }
                }

                categoriaEditar.setNombre(nombre);
                categoriaEditar.setDescripcion(descripcion);
                return;
            }
        }

        throw new IdNoEncontradoException("No se encontro una categoria con ID: " + categoriaId + ".");
    }

    // Busca una categoría por su ID en la colección. Devuelve la categoría si la
    // encuentra, o null si no existe.
    public Categoria buscarPorId(Long id) {
        for (Categoria categoria : categorias) {
            if (categoria.getId().equals(id)) {
                return categoria;
            }
        }
        return null;
    }

}
