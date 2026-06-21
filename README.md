# 🍔 Food Store – Sistema de Gestión de Pedidos

Trabajo Práctico Integrador – Programación 2  
Tecnicatura Universitaria en Programación – Universidad Tecnológica Nacional (UTN)



## 📋 Descripción

Food Store es una aplicación de consola desarrollada en Java 21, orientada a la gestión de productos de un negocio de comidas. Permite administrar categorías, productos, usuarios y pedidos mediante un menú interactivo, aplicando Programación Orientada a Objetos (POO) y almacenamiento en memoria con Colecciones.



## 👥 Integrantes

| Nombre | Rol |
|--------|-----|
| Rocío Moscardi | Documentación, README, revisión de código, menú de Categorías y Productos |
| Casto Gil | Entidades Java, enums e interfaz Calculable |
| Fernando Weisheim | Servicios de Categoría y Producto, excepciones propias |
| Abigail Santillán | Servicios de Usuario y Pedido, menú de Usuarios y Pedidos |



## 🛠️ Tecnologías utilizadas

- Java 21
- Colecciones Java (ArrayList, etc.)
- Programación Orientada a Objetos (POO)
- Git / GitHub



## 📁 Estructura del proyecto

```
src/
└── integrado/prog2/
    ├── Main.java
    ├── entities/       (clases del UML: Base, Categoria, Producto, Usuario, Pedido, DetallePedido)
    ├── enums/          (Rol, Estado, FormaPago)
    ├── exception/      (excepciones propias)
    ├── interfaces/     (Calculable)
    └── service/        (lógica de negocio)
```



## 🎥 Video explicativo

> 🔗 [
Trabajo Práctico Integrador - Programación II - Food Store](https://www.youtube.com/watch?v=kEqlWBjtnas)



## 📄 Documentación

> 🔗 [Gil-Moscardi-Santillan-Weisheim-TPI-PROG2](https://drive.google.com/file/d/1UJuaSZTVg4oyIj5lbrOVAqWg3aN3i46z/view?usp=sharing)



## ✅ Funcionalidades implementadas

- CRUD completo de Categorías
- CRUD completo de Productos
- CRUD completo de Usuarios
- CRUD completo de Pedidos con detalles
- Baja lógica (soft delete) en todas las entidades
- Validaciones de entrada y manejo de excepciones propias
- Interfaz `Calculable` para el cálculo de totales de pedidos
- Validación de ID duplicado antes de pedir todos los datos
- Actualización de estado y forma de pago de pedidos
