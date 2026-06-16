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
                throw new IdDuplicadoException("Ya existe una categoría con ID: " + categoria.getId() + "." + " No se pueden agregar categorías con IDs duplicados.");
            }
        }
        categorias.add(categoria);
    }

    public void editarCategoria(Long categoriaId, String nombre, String descripcion) {
        for (Categoria categoriaEditar : categorias) {
            if (categoriaEditar.getId().equals(categoriaId)) {

                if (categoriaEditar.isEliminado()) {
                    throw new IdEliminadoException(
                            "La categoría con ID: " + categoriaEditar.getId() + " fue eliminada. No se puede editar.");
                }

                categoriaEditar.setNombre(nombre);
                categoriaEditar.setDescripcion(descripcion);
                return;
            }
        }
        throw new IdNoEncontradoException("No se encontró una categoría con ID: " + categoriaId + ".");

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
