package integrado.prog2.entities;

import java.time.LocalDateTime;

public abstract class Base {

    // Atributos comunes a todas las entidades
    private Long id;
    private boolean eliminado;
    private LocalDateTime createdAt;

    // Constructor sin argumentos
    public Base() {
        this.eliminado = false;
        this.createdAt = LocalDateTime.now();
    }

    // Constructor con id
    public Base(Long id) {
        setId(id);
        this.eliminado = false;
        this.createdAt = LocalDateTime.now();
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor a 0 y no puede estar vacío.");
        }
        this.id = id;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        if (createdAt == null) {
            throw new IllegalArgumentException("La fecha de creación no puede estar vacía.");
        }
        this.createdAt = createdAt;
    }

    // Método para marcar la entidad como eliminada
    public void eliminar() {
        this.eliminado = true;
    }
}