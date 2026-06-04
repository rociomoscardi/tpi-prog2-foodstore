# 🍔 Food Store – Sistema de Gestión de Pedidos

Trabajo Práctico Integrador – Programación 2  
Tecnicatura Universitaria en Programación – Universidad Tecnológica Nacional (UTN)



## 📋 Descripción

Food Store es una aplicación de consola desarrollada en Java 21, orientada a la gestión de productos de un negocio de comidas. Permite administrar categorías, productos, usuarios y pedidos mediante un menú interactivo, aplicando Programación Orientada a Objetos (POO) y almacenamiento en memoria con Colecciones.



## 👥 Integrantes

| Nombre | Rol |
|--------|-----|
| Rocío Moscardi | Documentación, README, DER y SQL |
| (Integrante B) | Entidades Java y generación de datos |
| Fernando Weisheim | Servicios de Categoría y Producto |
| (Integrante D) | Servicios de Usuario, Pedido y menú |



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
    ├── config/         (ConexionDB, configuración)
    ├── entities/       (clases del UML: Base, Categoria, Producto, Usuario, Pedido, DetallePedido)
    ├── enums/          (Rol, Estado, FormaPago)
    ├── exception/      (excepciones propias)
    └── service/        (lógica de negocio)
```



## 🎥 Video explicativo

> 🔗 [Insertar link al video]



## 📄 Documentación PDF

> 🔗 [Insertar link al PDF]



## ✅ Funcionalidades implementadas

- CRUD completo de Categorías
- CRUD completo de Productos
- CRUD completo de Usuarios
- CRUD completo de Pedidos con detalles
- Baja lógica (soft delete) en todas las entidades
- Validaciones de entrada y manejo de excepciones propias
- Interfaz `Calculable` para el cálculo de totales de pedidos
