package integrado.prog2.entities;

import java.time.LocalDateTime;

public abstract class Base {

    private Long id;
    private boolean eliminado;
    private LocalDateTime createdAt;

    public Base() {
        this.eliminado = false;
        this.createdAt = LocalDateTime.now();
    }

    public Base(Long id) {
        setId(id);
        this.eliminado = false;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El id debe ser mayor a 0.");
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
            throw new IllegalArgumentException("La fecha de creación no puede ser nula.");
        }
        this.createdAt = createdAt;
    }

    public void eliminar() {
        this.eliminado = true;
    }
}