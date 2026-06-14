/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrado.prog2.service;

import integrado.prog2.entities.Categoria;
import integrado.prog2.exception.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fernando
 */
public class CategoriaService {

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
            throw new IdNullException("El ID de la categoría no puede ser nulo.");
        }
        for (Categoria categoria : categorias) {
            if (categoria.getId().equals(categoriaId)) {
                categoria.eliminar();
                return;
            }
        }
        throw new IdNoEncontradoException("La categoria con ID: " + categoriaId + " no se encontró.");
    }

    public void agregarCategoria(Categoria categoria) {

        for (Categoria categoriaAgregar : categorias) {
            if(categoria == null){
                throw new NullException("La categoria no puede ser nula");
            }
            if (categoriaAgregar.getId().equals(categoria.getId())) {
                throw new IdDuplicadoException("la categoria ya existe y el ID es: " + categoria.getId());
            }
        }
        categorias.add(categoria);

    }

    public void editarCategoria(Long categoriaId, String nombre, String descripcion) {
        for (Categoria categoriaEditar : categorias) {
            if (categoriaEditar.getId().equals(categoriaId)) {

                if (categoriaEditar.isEliminado() == true) {
                    throw new IdEliminadoException(
                            "El ID: " + categoriaEditar.getId() + " fue removido de la lista y no se puede editar.");
                }

                categoriaEditar.setNombre(nombre);
                categoriaEditar.setDescripcion(descripcion);
                return;
            }
        }
        throw new IdNoEncontradoException("La categoria con ID:" + categoriaId + " no se encontro para editar");

    }

}
