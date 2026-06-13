/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrado.prog2.service;

import integrado.prog2.entities.Categoria;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fernando
 */
public class CategoriaService {

    private List<Categoria> categorias = new ArrayList<>();

    public List<Categoria> listarCategorias() {
        return categorias;

    }

    public void elimiarCategoria(Long categoriaId) {
        for (Categoria categoria : categorias) {
            if (categoria.getId().equals(categoriaId)) {
                categoria.setEliminado(true);
            }
        }

    }

    public void agregarCategoria(Categoria categoria) {

        for (Categoria categoriaAgregar : categorias) {
            if (categoriaAgregar.getId().equals(categoria.getId())) {
                throw new IllegalArgumentException("la categoria ya existe");
            }
        }
        categorias.add(categoria);

    }

    public void editarCategoria(Long categoriaId, String nombre,String descripcion) {
        for (Categoria categoriaEditar : categorias) {
            if(categoriaEditar.getId().equals(categoriaId)){
                categoriaEditar.setNombre(nombre);
                categoriaEditar.setDescripcion(descripcion);
                return;
            }
        }
        throw new IllegalArgumentException("La categoria con ID:" + categoriaId + "no se encontro");

    }

}
