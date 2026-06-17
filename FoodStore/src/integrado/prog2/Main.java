package integrado.prog2;

import integrado.prog2.entities.Categoria;
import integrado.prog2.entities.Pedido;
import integrado.prog2.entities.Producto;
import integrado.prog2.entities.Usuario;
import integrado.prog2.enums.Estado;
import integrado.prog2.enums.FormaPago;
import integrado.prog2.enums.Rol;
import integrado.prog2.exception.IdDuplicadoException;
import integrado.prog2.exception.IdEliminadoException;
import integrado.prog2.exception.IdNoEncontradoException;
import integrado.prog2.service.CategoriaService;
import integrado.prog2.service.PedidoService;
import integrado.prog2.service.ProductoService;
import integrado.prog2.service.UsuarioService;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        CategoriaService cService = new CategoriaService();
        ProductoService pService = new ProductoService();
        UsuarioService uService = new UsuarioService();
        PedidoService pedService = new PedidoService();

        Scanner scanner = new Scanner(System.in);

        int opcion;
        do {
            System.out.println("\n=== SISTEMA DE PEDIDOS (FOOD STORE) ===");
            System.out.println("1. Categorías");
            System.out.println("2. Productos");
            System.out.println("3. Usuarios");
            System.out.println("4. Pedidos");
            System.out.println("0. Salir");
            System.out.print("Seleccione: ");
            try {
                opcion = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Opción inválida. Ingrese un número.");
                opcion = -1;
            }

            switch (opcion) {
                case 1:
                    menuCategorias(scanner, cService);
                    break;
                case 2:
                    menuProductos(scanner, pService, cService);
                    break;
                case 3:
                    menuUsuarios(scanner, uService);
                    break;
                case 4:
                    menuPedidos(scanner, pedService, uService, pService);
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida. Intentelo nuevamente.");
            }
        } while (opcion != 0);

        scanner.close();
    }

    // --- MENÚ DE CATEGORÍAS ---
    private static void menuCategorias(Scanner scanner, CategoriaService cService) {
        int opcion;

        do {
            System.out.println("\n=== CATEGORÍAS ===");
            System.out.println("1. Listar categorías");
            System.out.println("2. Agregar categoría");
            System.out.println("3. Editar categoría");
            System.out.println("4. Eliminar categoría");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");
            try {
                opcion = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Opción inválida. Ingrese un número.");
                opcion = -1;
                continue;
            }

            switch (opcion) {
                case 1:
                    List<Categoria> categorias = cService.listarCategorias();
                    if (categorias.isEmpty()) {
                        System.out.println("No hay categorías cargadas.");
                    } else {
                        for (Categoria categoria : categorias) {
                            System.out.println(categoria);
                        }
                    }
                    break;

case 2:
                    try {
                        System.out.print("Ingrese el ID: ");
                        String idStr = scanner.nextLine().trim();
                        if (idStr.isEmpty()) {
                            System.out.println("Error: El ID no puede estar vacío.");
                            break;
                        }
                        Long id = Long.parseLong(idStr);

                        if (cService.buscarPorId(id) != null) {
                            System.out.println("Ya existe una categoría con ID: " + id + ".");
                            break;
                        }

                        System.out.print("Ingrese el nombre: ");
                        String nombre = scanner.nextLine().trim();

                        //Validar nombre único al agregar ---
                        boolean nombreDuplicado = false;
                        for (Categoria c : cService.listarCategorias()) {
                            if (c.getNombre().equalsIgnoreCase(nombre)) {
                                nombreDuplicado = true;
                                break;
                            }
                        }
                        if (nombreDuplicado) {
                            System.out.println("Error: Ya existe una categoría con el nombre '" + nombre + "'.");
                            break;
                        }
                        // -----------------------------------------------------

                        System.out.print("Ingrese la descripción: ");
                        String descripcion = scanner.nextLine();

                        Categoria nuevaCategoria = new Categoria(id, nombre, descripcion);
                        cService.agregarCategoria(nuevaCategoria);
                        System.out.println("Categoría agregada correctamente con ID: " + id);

                    } catch (NumberFormatException e) {
                        System.out.println("Error: El ID debe ser un número válido.");
                    } catch (IdDuplicadoException e) {
                        System.out.println("Error: " + e.getMessage());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 3:
                    List<Categoria> categoriasEditar = cService.listarCategorias();
                    if (categoriasEditar.isEmpty()) {
                        System.out.println("No hay categorías cargadas.");
                    } else {
                        for (Categoria categoria : categoriasEditar) {
                            System.out.println(categoria);
                        }
                        try {
                            System.out.print("Ingrese el ID de la categoría a editar: ");
                            String idStr = scanner.nextLine().trim();
                            if (idStr.isEmpty()) {
                                System.out.println("Error: El ID no puede estar vacío.");
                                break;
                            }
                            Long id = Long.parseLong(idStr);

                            System.out.print("Ingrese el nuevo nombre: ");
                            String nombre = scanner.nextLine().trim();

                            // Validar nombre único al editar (que no sea de otra categoría) ---
                            boolean nombreDuplicadoEdit = false;
                            for (Categoria c : cService.listarCategorias()) {
                                if (c.getNombre().equalsIgnoreCase(nombre) && !c.getId().equals(id)) {
                                    nombreDuplicadoEdit = true;
                                    break;
                                }
                            }
                            if (nombreDuplicadoEdit) {
                                System.out.println("Error: Ya existe otra categoría con el nombre '" + nombre + "'.");
                                break;
                            }
                            // ----------------------------------------------------------------------------------

                            System.out.print("Ingrese la nueva descripción: ");
                            String descripcion = scanner.nextLine();

                            cService.editarCategoria(id, nombre, descripcion);
                            System.out.println("Categoría con ID: " + id + " editada correctamente.");

                        } catch (NumberFormatException e) {
                            System.out.println("Error: El ID debe ser un número válido.");
                        } catch (IdNoEncontradoException e) {
                            System.out.println("Error: " + e.getMessage());
                        } catch (IdEliminadoException e) {
                            System.out.println("Error: " + e.getMessage());
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                    break;

                case 4:
                    List<Categoria> categoriasEliminar = cService.listarCategorias();
                    if (categoriasEliminar.isEmpty()) {
                        System.out.println("No hay categorías cargadas.");
                    } else {
                        for (Categoria categoria : categoriasEliminar) {
                            System.out.println(categoria);
                        }
                        try {
                            System.out.print("Ingrese el ID de la categoría a eliminar: ");
                            String idStr = scanner.nextLine().trim();
                            if (idStr.isEmpty()) {
                                System.out.println("Error: El ID no puede estar vacío.");
                                break;
                            }
                            Long id = Long.parseLong(idStr);

                            System.out.print("¿Está seguro? (S/N): ");
                            String confirmacion = scanner.nextLine();
                            if (confirmacion.equalsIgnoreCase("S")) {
                                cService.eliminarCategoria(id);
                                System.out.println("Categoría con ID: " + id + " eliminada correctamente.");
                            } else {
                                System.out.println("Operación cancelada.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Error: El ID debe ser un número válido.");
                        } catch (IdNoEncontradoException e) {
                            System.out.println("Error: " + e.getMessage());
                        } catch (IdEliminadoException e) {
                            System.out.println("Error: " + e.getMessage());
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                    break;

                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;

                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 0);
    }

    // --- MENÚ DE PRODUCTOS ---
    private static void menuProductos(Scanner scanner, ProductoService pService, CategoriaService cService) {
        int opcion;

        do {
            System.out.println("\n=== PRODUCTOS ===");
            System.out.println("1. Listar productos");
            System.out.println("2. Agregar producto");
            System.out.println("3. Editar producto");
            System.out.println("4. Eliminar producto");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");
            try {
                opcion = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Opción inválida. Ingrese un número.");
                opcion = -1;
                continue;
            }

            switch (opcion) {
                case 1:
                    List<Producto> productos = pService.listarProductos();
                    if (productos.isEmpty()) {
                        System.out.println("No hay productos disponibles.");
                    } else {
                        for (Producto producto : productos) {
                            System.out.println(producto);
                        }
                    }
                    break;

                case 2:
                    try {
                        System.out.print("Ingrese el ID del producto: ");
                        String idStr = scanner.nextLine().trim();
                        if (idStr.isEmpty()) {
                            System.out.println("Error: El ID no puede estar vacío.");
                            break;
                        }
                        Long id = Long.parseLong(idStr);

                        if (pService.buscarPorId(id) != null) {
                            System.out.println("Ya existe un producto con ID: " + id + ".");
                            break;
                        }

                        System.out.print("Ingrese el nombre: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Ingrese la descripción: ");
                        String descripcion = scanner.nextLine();
                        System.out.print("Ingrese el precio: ");
                        Double precio = Double.parseDouble(scanner.nextLine().trim());
                        System.out.print("Ingrese el stock: ");
                        int stock = Integer.parseInt(scanner.nextLine().trim());
                        System.out.print("Ingrese la imagen (o Enter para omitir): ");
                        String imagen = scanner.nextLine();
                        System.out.print("¿Está disponible? (S/N): ");
                        boolean disponible = scanner.nextLine().trim().equalsIgnoreCase("S");

                        List<Categoria> categorias = cService.listarCategorias();
                        if (categorias.isEmpty()) {
                            System.out.println("No hay categorías disponibles. Cree una categoría primero.");
                            break;
                        }
                        System.out.println("Categorías disponibles:");
                        for (Categoria cat : categorias) {
                            System.out.println(cat);
                        }
                        System.out.print("Ingrese el ID de la categoría: ");
                        String idCatStr = scanner.nextLine().trim();
                        if (idCatStr.isEmpty()) {
                            System.out.println("Error: El ID no puede estar vacío.");
                            break;
                        }
                        Long idCategoria = Long.parseLong(idCatStr);

                        Categoria categoria = cService.buscarPorId(idCategoria);
                        if (categoria == null) {
                            System.out.println("No se encontró una categoría con ese ID.");
                            break;
                        }

                        Producto nuevoProducto = new Producto(id, nombre, precio, descripcion, stock, imagen,
                                disponible, categoria);
                        pService.agregarProducto(nuevoProducto);
                        System.out.println("Producto agregado correctamente con ID: " + id);

                    } catch (NumberFormatException e) {
                        System.out.println("Error: Ingrese un valor numérico válido.");
                    } catch (IdDuplicadoException e) {
                        System.out.println("Error: " + e.getMessage());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 3:
                    List<Producto> productosEditar = pService.listarProductos();
                    if (productosEditar.isEmpty()) {
                        System.out.println("No hay productos disponibles para editar.");
                    } else {
                        for (Producto producto : productosEditar) {
                            System.out.println(producto);
                        }
                        try {
                            System.out.print("Ingrese el ID del producto a editar: ");
                            String idStr = scanner.nextLine().trim();
                            if (idStr.isEmpty()) {
                                System.out.println("Error: El ID no puede estar vacío.");
                                break;
                            }
                            Long id = Long.parseLong(idStr);

                            System.out.print("Ingrese el nuevo precio: ");
                            double precio = Double.parseDouble(scanner.nextLine().trim());
                            System.out.print("Ingrese el nuevo stock: ");
                            int stock = Integer.parseInt(scanner.nextLine().trim());

                            List<Categoria> categoriasEditar = cService.listarCategorias();
                            System.out.println("Categorías disponibles:");
                            for (Categoria cat : categoriasEditar) {
                                System.out.println(cat);
                            }
                            System.out.print("Ingrese el ID de la nueva categoría: ");
                            String idCatStr = scanner.nextLine().trim();
                            if (idCatStr.isEmpty()) {
                                System.out.println("Error: El ID no puede estar vacío.");
                                break;
                            }
                            Long idCategoria = Long.parseLong(idCatStr);

                            Categoria categoria = cService.buscarPorId(idCategoria);
                            if (categoria == null) {
                                System.out.println("No se encontró una categoría con ese ID.");
                                break;
                            }

                            pService.editarProducto(id, precio, stock, categoria);
                            System.out.println("Producto con ID: " + id + " editado correctamente.");

                        } catch (NumberFormatException e) {
                            System.out.println("Error: Ingrese un valor numérico válido.");
                        } catch (IdNoEncontradoException e) {
                            System.out.println("Error: " + e.getMessage());
                        } catch (IdEliminadoException e) {
                            System.out.println("Error: " + e.getMessage());
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                    break;

                case 4:
                    List<Producto> productosEliminar = pService.listarProductos();
                    if (productosEliminar.isEmpty()) {
                        System.out.println("No hay productos disponibles para eliminar.");
                    } else {
                        for (Producto producto : productosEliminar) {
                            System.out.println(producto);
                        }
                        try {
                            System.out.print("Ingrese el ID del producto a eliminar: ");
                            String idStr = scanner.nextLine().trim();
                            if (idStr.isEmpty()) {
                                System.out.println("Error: El ID no puede estar vacío.");
                                break;
                            }
                            Long id = Long.parseLong(idStr);

                            System.out.print("¿Está seguro? (S/N): ");
                            String confirmacion = scanner.nextLine();
                            if (confirmacion.equalsIgnoreCase("S")) {
                                pService.eliminarProducto(id);
                                System.out.println("Producto con ID: " + id + " eliminado correctamente.");
                            } else {
                                System.out.println("Operación cancelada.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Error: El ID debe ser un número válido.");
                        } catch (IdNoEncontradoException e) {
                            System.out.println("Error: " + e.getMessage());
                        } catch (IdEliminadoException e) {
                            System.out.println("Error: " + e.getMessage());
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                    break;

                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;

                default:
                    System.out.println("Opción inválida. Intentelo nuevamente.");
            }
        } while (opcion != 0);
    }

    // --- MENÚ DE USUARIOS ---
    private static void menuUsuarios(Scanner scanner, UsuarioService uService) {
        int opcion;

        do {
            System.out.println("\n=== USUARIOS ===");
            System.out.println("1. Listar usuarios");
            System.out.println("2. Agregar usuario");
            System.out.println("3. Editar usuario");
            System.out.println("4. Eliminar usuario");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");
            try {
                opcion = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Opción inválida. Ingrese un número.");
                opcion = -1;
                continue;
            }

            switch (opcion) {
                case 1:
                    List<Usuario> usuarios = uService.listarUsuarios();
                    if (usuarios.isEmpty()) {
                        System.out.println("No hay usuarios cargados.");
                    } else {
                        for (Usuario usuario : usuarios) {
                            System.out.println(usuario);
                        }
                    }
                    break;

           case 2:
                    try {
                        System.out.print("Ingrese el ID: ");
                        String idStr = scanner.nextLine().trim();
                        if (idStr.isEmpty()) {
                            System.out.println("Error: El ID no puede estar vacío.");
                            break;
                        }
                        Long id = Long.parseLong(idStr);

                        if (uService.buscarPorId(id) != null) {
                            System.out.println("Ya existe un usuario con ID: " + id + ".");
                            break;
                        }

                        System.out.print("Ingrese el nombre: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Ingrese el mail: ");
                        String mail = scanner.nextLine().trim();

                        // Validar mail único al crear ---
                        boolean mailDuplicado = false;
                        for (Usuario u : uService.listarUsuarios()) {
                            if (u.getMail().equalsIgnoreCase(mail)) {
                                mailDuplicado = true;
                                break;
                            }
                        }
                        if (mailDuplicado) {
                            System.out.println("Error: El mail '" + mail + "' ya está registrado.");
                            break;
                        }
                        // --------------------------------------------------

                        System.out.print("Ingrese el apellido: ");
                        String apellido = scanner.nextLine();
                        System.out.print("Ingrese el celular: ");
                        String celular = scanner.nextLine();
                        System.out.print("Ingrese la contraseña: ");
                        String contrasena = scanner.nextLine();
                        System.out.println("Rol: 1. USUARIO | 2. ADMIN");
                        int rolOpcion = Integer.parseInt(scanner.nextLine().trim());
                        Rol rol = rolOpcion == 2 ? Rol.ADMIN : Rol.USUARIO;

                        Usuario nuevoUsuario = new Usuario(id, nombre, apellido, mail, celular, contrasena, rol);
                        uService.agregarUsuario(nuevoUsuario);
                        System.out.println("Usuario agregado correctamente con ID: " + id);

                    } catch (NumberFormatException e) {
                        System.out.println("Error: Ingrese un valor numérico válido.");
                    } catch (IdDuplicadoException e) {
                        System.out.println("Error: " + e.getMessage());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 3:
                    List<Usuario> usuariosEditar = uService.listarUsuarios();
                    if (usuariosEditar.isEmpty()) {
                        System.out.println("No hay usuarios cargados para editar.");
                    } else {
                        for (Usuario usuario : usuariosEditar) {
                            System.out.println(usuario);
                        }
                        try {
                            System.out.print("Ingrese el ID del usuario a editar: ");
                            String idStr = scanner.nextLine().trim();
                            if (idStr.isEmpty()) {
                                System.out.println("Error: El ID no puede estar vacío.");
                                break;
                            }
                            Long id = Long.parseLong(idStr);

                            System.out.print("Ingrese el nuevo nombre: ");
                            String nombre = scanner.nextLine();
                            System.out.print("Ingrese el nuevo apellido: ");
                            String apellido = scanner.nextLine();
                            System.out.print("Ingrese el nuevo mail: ");
                            String mail = scanner.nextLine().trim();

                            // Validar mail único al editar (que no sea de otro usuario) ---
                            boolean mailDuplicadoEdit = false;
                            for (Usuario u : uService.listarUsuarios()) {
                                if (u.getMail().equalsIgnoreCase(mail) && !u.getId().equals(id)) {
                                    mailDuplicadoEdit = true;
                                    break;
                                }
                            }
                            if (mailDuplicadoEdit) {
                                System.out.println("Error: El mail '" + mail + "' ya pertenece a otro usuario.");
                                break;
                            }
                            // --------------------------------------------------------------------------------

                            System.out.print("Ingrese el nuevo celular: ");
                            String celular = scanner.nextLine();
                            System.out.print("Ingrese la nueva contraseña: ");
                            String contrasena = scanner.nextLine();

                            uService.editarUsuario(id, nombre, apellido, mail, celular, contrasena);
                            System.out.println("Usuario con ID: " + id + " editado correctamente.");

                        } catch (NumberFormatException e) {
                            System.out.println("Error: El ID debe ser un número válido.");
                        } catch (IdNoEncontradoException e) {
                            System.out.println("Error: " + e.getMessage());
                        } catch (IdEliminadoException e) {
                            System.out.println("Error: " + e.getMessage());
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                    break;

                case 4:
                    List<Usuario> usuariosEliminar = uService.listarUsuarios();
                    if (usuariosEliminar.isEmpty()) {
                        System.out.println("No hay usuarios cargados para eliminar.");
                    } else {
                        for (Usuario usuario : usuariosEliminar) {
                            System.out.println(usuario);
                        }
                        try {
                            System.out.print("Ingrese el ID del usuario a eliminar: ");
                            String idStr = scanner.nextLine().trim();
                            if (idStr.isEmpty()) {
                                System.out.println("Error: El ID no puede estar vacío.");
                                break;
                            }
                            Long id = Long.parseLong(idStr);

                            System.out.print("¿Está seguro que desea dar de baja al usuario? (S/N): ");
                            String confirmacion = scanner.nextLine();
                            if (confirmacion.equalsIgnoreCase("S")) {
                                uService.eliminarUsuario(id);
                                System.out.println("Usuario con ID: " + id + " eliminado correctamente.");
                            } else {
                                System.out.println("Operación cancelada.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Error: El ID debe ser un número válido.");
                        } catch (IdNoEncontradoException e) {
                            System.out.println("Error: " + e.getMessage());
                        } catch (IdEliminadoException e) {
                            System.out.println("Error: " + e.getMessage());
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                    break;

                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;

                default:
                    System.out.println("Opción inválida. Intentelo nuevamente.");
            }
        } while (opcion != 0);
    }

    // --- MENÚ DE PEDIDOS ---
private static void menuPedidos(Scanner scanner, PedidoService pedService, UsuarioService uService,
            ProductoService pService) {
        int opcion;

        do {
            System.out.println("\n=== PEDIDOS ===");
            System.out.println("1. Listar pedidos");
            System.out.println("2. Crear nuevo pedido");
            System.out.println("3. Agregar producto a un pedido");
            System.out.println("4. Eliminar pedido");
            System.out.println("5. Actualizar estado o forma de pago");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");
            try {
                opcion = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Opción inválida. Ingrese un número.");
                opcion = -1;
                continue;
            }

            switch (opcion) {
                case 1:
                    List<Pedido> pedidos = pedService.listarPedidos();
                    if (pedidos.isEmpty()) {
                        System.out.println("No hay pedidos activos.");
                    } else {
                        for (Pedido p : pedidos) {
                            System.out.println(p);
                        }
                    }
                    break;

                case 2:
                    try {
                        System.out.print("Ingrese el ID para el nuevo pedido: ");
                        String idPedStr = scanner.nextLine().trim();
                        if (idPedStr.isEmpty()) {
                            System.out.println("Error: El ID no puede estar vacío.");
                            break;
                        }
                        Long idPedido = Long.parseLong(idPedStr);

                        System.out.print("Ingrese el ID del usuario que realiza el pedido: ");
                        String idUsrStr = scanner.nextLine().trim();
                        if (idUsrStr.isEmpty()) {
                            System.out.println("Error: El ID no puede estar vacío.");
                            break;
                        }
                        Long idUsuario = Long.parseLong(idUsrStr);

                        Usuario user = uService.buscarPorId(idUsuario);
                        if (user == null) {
                            System.out.println("No se encontró un usuario con el ID ingresado.");
                            break;
                        }

                        // Bucle 'while' para restringir formas de pago válidas ---
                        int fpOpcion = 0;
                        while (fpOpcion < 1 || fpOpcion > 3) {
                            System.out.println("Formas de Pago: 1. EFECTIVO | 2. TARJETA | 3. TRANSFERENCIA");
                            System.out.print("Seleccione (1-3): ");
                            try {
                                fpOpcion = Integer.parseInt(scanner.nextLine().trim());
                                if (fpOpcion < 1 || fpOpcion > 3) {
                                    System.out.println("Opción inválida. Por favor seleccione 1, 2 o 3.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println(" Error: Debe ingresar un número entero.");
                                fpOpcion = -1;
                            }
                        }

                        FormaPago fp = FormaPago.EFECTIVO;
                        if (fpOpcion == 2) fp = FormaPago.TARJETA;
                        if (fpOpcion == 3) fp = FormaPago.TRANSFERENCIA;

                        Pedido nuevoPedido = new Pedido(idPedido, user, fp);
                        pedService.crearPedido(nuevoPedido);
                        System.out.println("Pedido creado con éxito con estado PENDIENTE.");

                    } catch (NumberFormatException e) {
                        System.out.println("Error: Ingrese un valor numérico válido.");
                    } catch (IdDuplicadoException e) {
                        System.out.println("Error: " + e.getMessage());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 3:
                    try {
                        System.out.print("Ingrese el ID del pedido: ");
                        String idPedStr = scanner.nextLine().trim();
                        if (idPedStr.isEmpty()) {
                            System.out.println("Error: El ID no puede estar vacío.");
                            break;
                        }
                        Long idPed = Long.parseLong(idPedStr);

                        System.out.print("Ingrese el ID del producto a agregar: ");
                        String idProdStr = scanner.nextLine().trim();
                        if (idProdStr.isEmpty()) {
                            System.out.println("Error: El ID no puede estar vacío.");
                            break;
                        }
                        Long idProd = Long.parseLong(idProdStr);

                        System.out.print("Ingrese la cantidad: ");
                        int cant = Integer.parseInt(scanner.nextLine().trim());

                        Producto prod = pService.buscarPorId(idProd);
                        if (prod == null) {
                            System.out.println("No se encontró un producto con el ID ingresado.");
                            break;
                        }

                        pedService.agregarProductoAPedido(idPed, prod, cant);
                        System.out.println("Producto agregado correctamente al pedido.");

                    } catch (NumberFormatException e) {
                        System.out.println("Error: Ingrese un valor numérico válido.");
                    } catch (IdNoEncontradoException e) {
                        System.out.println("Error: " + e.getMessage());
                    } catch (IdEliminadoException e) {
                        System.out.println("Error: " + e.getMessage());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 4:
                    try {
                        System.out.print("Ingrese el ID del pedido a dar de baja: ");
                        String idStr = scanner.nextLine().trim();
                        if (idStr.isEmpty()) {
                            System.out.println("Error: El ID no puede estar vacío.");
                            break;
                        }
                        Long id = Long.parseLong(idStr);

                        System.out.print("¿Está seguro que desea eliminar el pedido? (S/N): ");
                        String confirmacion = scanner.nextLine();
                        if (confirmacion.equalsIgnoreCase("S")) {

                            // Limpiar la lista interna de detalles antes de borrar el pedido ---
                          for (Pedido p : pedService.listarPedidos()) {
                                if (p.getId().equals(id)) {
                                    if (p.getDetalles() != null) {
                                        p.getDetalles().clear();
                                    }
                                    break;
                                }
                            }

                            pedService.eliminarPedido(id);
                            System.out.println("Pedido con ID: " + id + " eliminado correctamente.");
                        } else {
                            System.out.println("Operación cancelada.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error: El ID debe ser un número válido.");
                    } catch (IdNoEncontradoException e) {
                        System.out.println("Error: " + e.getMessage());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 5:
                    try {
                        List<Pedido> pedidosActualizar = pedService.listarPedidos();
                        if (pedidosActualizar.isEmpty()) {
                            System.out.println("No hay pedidos activos.");
                            break;
                        }
                        for (Pedido p : pedidosActualizar) {
                            System.out.println(p);
                        }

                        System.out.print("Ingrese el ID del pedido a actualizar: ");
                        String idStr = scanner.nextLine().trim();
                        if (idStr.isEmpty()) {
                            System.out.println("Error: El ID no puede estar vacío.");
                            break;
                        }
                        Long id = Long.parseLong(idStr);

                        System.out.println("¿Qué desea actualizar?");
                        System.out.println("1. Estado");
                        System.out.println("2. Forma de pago");
                        System.out.print("Seleccione: ");
                        int subOpcion = Integer.parseInt(scanner.nextLine().trim());

                        if (subOpcion == 1) {
                            // Bucle 'while' para restringir estados válidos (1-4) ---
                            int estadoOpcion = 0;
                            while (estadoOpcion < 1 || estadoOpcion > 4) {
                                System.out.println("Estados disponibles: 1. PENDIENTE | 2. CONFIRMADO | 3. TERMINADO | 4. CANCELADO");
                                System.out.print("Seleccione (1-4): ");
                                try {
                                    estadoOpcion = Integer.parseInt(scanner.nextLine().trim());
                                    if (estadoOpcion < 1 || estadoOpcion > 4) {
                                        System.out.println("Opción inválida. Por favor seleccione de 1 a 4.");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println(" Error: Debe ingresar un número entero.");
                                    estadoOpcion = -1;
                                }
                            }

                            Estado nuevoEstado = Estado.PENDIENTE;
                            if (estadoOpcion == 2) nuevoEstado = Estado.CONFIRMADO;
                            if (estadoOpcion == 3) nuevoEstado = Estado.TERMINADO;
                            if (estadoOpcion == 4) nuevoEstado = Estado.CANCELADO;

                            pedService.cambiarEstadoPedido(id, nuevoEstado);
                            System.out.println("Estado del pedido con ID: " + id + " actualizado correctamente.");

                        } else if (subOpcion == 2) {
                            // Bucle 'while' para restringir formas de pago válidas (1-3) ---
                            int fpOpcion = 0;
                            while (fpOpcion < 1 || fpOpcion > 3) {
                                System.out.println("Formas de pago disponibles: 1. EFECTIVO | 2. TARJETA | 3. TRANSFERENCIA");
                                System.out.print("Seleccione (1-3): ");
                                try {
                                    fpOpcion = Integer.parseInt(scanner.nextLine().trim());
                                    if (fpOpcion < 1 || fpOpcion > 3) {
                                        System.out.println("Opción inválida. Por favor seleccione 1, 2 o 3.");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Error: Debe ingresar un número entero.");
                                    fpOpcion = -1;
                                }
                            }

                            FormaPago nuevaFormaPago = FormaPago.EFECTIVO;
                            if (fpOpcion == 2) nuevaFormaPago = FormaPago.TARJETA;
                            if (fpOpcion == 3) nuevaFormaPago = FormaPago.TRANSFERENCIA;

                            pedService.cambiarFormaPagoPedido(id, nuevaFormaPago);
                            System.out.println("Forma de pago del pedido con ID: " + id + " actualizada correctamente.");

                        } else {
                            System.out.println("Opción inválida.");
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Error: Ingrese un valor numérico válido.");
                    } catch (IdNoEncontradoException e) {
                        System.out.println("Error: " + e.getMessage());
                    } catch (IdEliminadoException e) {
                        System.out.println("Error: " + e.getMessage());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;

                default:
                    System.out.println("Opción inválida. Intentelo nuevamente.");
            }
        } while (opcion != 0);
    }
}