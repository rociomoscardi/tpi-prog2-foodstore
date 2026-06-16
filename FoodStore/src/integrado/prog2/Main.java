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
        // Inicializar los services
        CategoriaService cService = new CategoriaService();
        ProductoService pService = new ProductoService();
        UsuarioService uService = new UsuarioService();
        PedidoService pedService = new PedidoService();

        // Inicializar el Scanner para leer entradas del usuario
        Scanner scanner = new Scanner(System.in);

        int opcion;
        do {
            // Mostrar el menú principal
            System.out.println("\n=== SISTEMA DE PEDIDOS (FOOD STORE) ===");
            System.out.println("1. Categorías");
            System.out.println("2. Productos");
            System.out.println("3. Usuarios");
            System.out.println("4. Pedidos");
            System.out.println("0. Salir");
            System.out.print("Seleccione: ");
            opcion = scanner.nextInt();

            // Llamar al método correspondiente según la opción elegida
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

        // Cerrar el Scanner al salir
        scanner.close();
    }

    // --- MENÚ DE CATEGORÍAS ---
    private static void menuCategorias(Scanner scanner, CategoriaService cService) {
        int opcion;

        do {
            // Mostrar las opciones disponibles del submenú de Categorías
            System.out.println("\n=== CATEGORÍAS ===");
            System.out.println("1. Listar categorías");
            System.out.println("2. Agregar categoría");
            System.out.println("3. Editar categoría");
            System.out.println("4. Eliminar categoría");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");
            opcion = scanner.nextInt();

            switch (opcion) {

                case 1:
                    // Obtener la lista de categorías no eliminadas a través del service
                    List<Categoria> categorias = cService.listarCategorias();
                    if (categorias.isEmpty()) {
                        System.out.println("No hay categorías cargadas.");
                    } else {
                        // Recorrer e imprimir cada categoría usando su toString()
                        for (Categoria categoria : categorias) {
                            System.out.println(categoria);
                        }
                    }
                    break;

                case 2:
                    try {
                        // Limpiar el buffer después de leer el número de opción
                        scanner.nextLine();

                        // Solicitar los datos de la nueva categoría al usuario
                        System.out.print("Ingrese el ID: ");
                        Long id = scanner.nextLong();
                        scanner.nextLine();

                        // Verificar si el ID ya existe antes de pedir más datos al usuario
                        if (cService.buscarPorId(id) != null) {
                            System.out.println("Error: ya existe una categoría con ID: " + id);
                            break;
                        }

                        System.out.print("Ingrese el nombre: ");
                        String nombre = scanner.nextLine();

                        System.out.print("Ingrese la descripción: ");
                        String descripcion = scanner.nextLine();

                        // Crear el objeto Categoria con los datos ingresados
                        // En este punto se ejecutan las validaciones del constructor
                        Categoria nuevaCategoria = new Categoria(id, nombre, descripcion);

                        // Delegar al service la lógica de agregar y validar duplicados
                        cService.agregarCategoria(nuevaCategoria);
                        System.out.println("Categoría agregada correctamente con ID: " + id);

                    } catch (IdDuplicadoException e) {
                        // El service detectó que ya existe una categoría con ese ID
                        System.out.println("Error: " + e.getMessage());
                    } catch (IllegalArgumentException e) {
                        // El constructor detectó datos inválidos (nombre vacío, ID inválido, etc.)
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 3:
                    // Mostrar las categorías disponibles para que el usuario sepa qué ID ingresar
                    List<Categoria> categoriasEditar = cService.listarCategorias();
                    if (categoriasEditar.isEmpty()) {
                        System.out.println("No hay categorías cargadas.");
                    } else {
                        for (Categoria categoria : categoriasEditar) {
                            System.out.println(categoria);
                        }
                        try {
                            scanner.nextLine();

                            // Solicitar el ID de la categoría a editar y los nuevos datos
                            System.out.print("Ingrese el ID de la categoría a editar: ");
                            Long id = scanner.nextLong();
                            scanner.nextLine();

                            System.out.print("Ingrese el nuevo nombre: ");
                            String nombre = scanner.nextLine();

                            System.out.print("Ingrese la nueva descripción: ");
                            String descripcion = scanner.nextLine();

                            // El service valida que el ID exista y no esté eliminado antes de editar
                            cService.editarCategoria(id, nombre, descripcion);
                            System.out.println("Categoría con ID: " + id + " editada correctamente.");

                        } catch (IdNoEncontradoException e) {
                            // El service no encontró ninguna categoría con ese ID
                            System.out.println("Error: " + e.getMessage());
                        } catch (IdEliminadoException e) {
                            // La categoría existe pero fue dada de baja lógicamente
                            System.out.println("Error: " + e.getMessage());
                        } catch (IllegalArgumentException e) {
                            // Los nuevos datos ingresados son inválidos
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                    break;

                case 4:
                    // Mostrar las categorías disponibles para que el usuario sepa qué ID ingresar
                    List<Categoria> categoriasEliminar = cService.listarCategorias();
                    if (categoriasEliminar.isEmpty()) {
                        System.out.println("No hay categorías cargadas.");
                    } else {
                        for (Categoria categoria : categoriasEliminar) {
                            System.out.println(categoria);
                        }
                        try {
                            scanner.nextLine();
                            System.out.print("Ingrese el ID de la categoría a eliminar: ");
                            Long id = scanner.nextLong();
                            scanner.nextLine();

                            // Pedir confirmación antes de realizar la baja lógica
                            System.out.print("¿Está seguro? (S/N): ");
                            String confirmacion = scanner.nextLine();

                            if (confirmacion.equalsIgnoreCase("S")) {
                                // El service marca eliminado = true sin borrar físicamente el objeto
                                cService.eliminarCategoria(id);
                                System.out.println("Categoría con ID: " + id + " eliminada correctamente.");
                            } else {
                                System.out.println("Operación cancelada.");
                            }
                        } catch (IdNoEncontradoException e) {
                            System.out.println("Error: " + e.getMessage());
                        } catch (IdEliminadoException e) {
                            // La categoría ya había sido eliminada previamente
                            System.out.println("Error: " + e.getMessage());
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                    break;

                case 0:
                    // El usuario eligió volver al menú principal
                    System.out.println("Volviendo al menú principal...");
                    break;

                default:
                    // El usuario ingresó un número que no corresponde a ninguna opción
                    System.out.println("Opción inválida. Intente nuevamente.");
            }

        } while (opcion != 0); // El menú se repite hasta que el usuario elija 0
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
            opcion = scanner.nextInt();

            switch (opcion) {

                case 1:
                    // Obtener la lista de productos no eliminados y disponibles
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
                        scanner.nextLine();

                        System.out.print("Ingrese el ID del producto: ");
                        Long id = scanner.nextLong();
                        scanner.nextLine();

                        // Verificar si el ID ya existe antes de pedir más datos
                        if (pService.buscarPorId(id) != null) {
                            System.out.println("Error: Ya existe un producto con ID: " + id);
                            break;
                        }

                        System.out.print("Ingrese el nombre: ");
                        String nombre = scanner.nextLine();

                        System.out.print("Ingrese la descripción: ");
                        String descripcion = scanner.nextLine();

                        System.out.print("Ingrese el precio: ");
                        Double precio = scanner.nextDouble();
                        scanner.nextLine();

                        System.out.print("Ingrese el stock: ");
                        int stock = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Ingrese la imagen (o Enter para omitir): ");
                        String imagen = scanner.nextLine();

                        System.out.print("¿Está disponible? (S/N): ");
                        String dispStr = scanner.nextLine();
                        boolean disponible = dispStr.equalsIgnoreCase("S");

                        // Mostrar las categorías disponibles para asociar
                        List<Categoria> categorias = cService.listarCategorias();
                        if (categorias.isEmpty()) {
                            System.out.println("Error: No hay categorías disponibles. Cree una categoría primero.");
                            break;
                        }
                        System.out.println("Categorías disponibles:");
                        for (Categoria cat : categorias) {
                            System.out.println(cat);
                        }
                        System.out.print("Ingrese el ID de la categoría: ");
                        Long idCategoria = scanner.nextLong();
                        scanner.nextLine();

                        // Buscar la categoría seleccionada
                        Categoria categoria = cService.buscarPorId(idCategoria);
                        if (categoria == null) {
                            System.out.println("Error: No se encontró una categoría con ese ID.");
                            break;
                        }

                        // Crear el producto con todos los datos
                        Producto nuevoProducto = new Producto(id, nombre, precio, descripcion, stock, imagen,
                                disponible, categoria);
                        pService.agregarProducto(nuevoProducto);
                        System.out.println("Producto agregado correctamente con ID: " + id);

                    } catch (IdDuplicadoException e) {
                        System.out.println("Error: " + e.getMessage());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 3:
                    // Mostrar productos disponibles para que el usuario sepa qué ID ingresar
                    List<Producto> productosEditar = pService.listarProductos();
                    if (productosEditar.isEmpty()) {
                        System.out.println("No hay productos disponibles para editar.");
                    } else {
                        for (Producto producto : productosEditar) {
                            System.out.println(producto);
                        }
                        try {
                            scanner.nextLine();

                            System.out.print("Ingrese el ID del producto a editar: ");
                            Long id = scanner.nextLong();
                            scanner.nextLine();

                            System.out.print("Ingrese el nuevo precio: ");
                            double precio = scanner.nextDouble();
                            scanner.nextLine();

                            System.out.print("Ingrese el nuevo stock: ");
                            int stock = scanner.nextInt();
                            scanner.nextLine();

                            // Mostrar categorías disponibles
                            List<Categoria> categoriasEditar = cService.listarCategorias();
                            System.out.println("Categorías disponibles:");
                            for (Categoria cat : categoriasEditar) {
                                System.out.println(cat);
                            }
                            System.out.print("Ingrese el ID de la nueva categoría: ");
                            Long idCategoria = scanner.nextLong();
                            scanner.nextLine();

                            Categoria categoria = cService.buscarPorId(idCategoria);
                            if (categoria == null) {
                                System.out.println("Error: No se encontró una categoría con el ID ingresado.");
                                break;
                            }

                            pService.editarProducto(id, precio, stock, categoria);
                            System.out.println("Producto con ID: " + id + " editado correctamente.");

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
                    // Mostrar productos disponibles para que el usuario sepa qué ID ingresar
                    List<Producto> productosEliminar = pService.listarProductos();
                    if (productosEliminar.isEmpty()) {
                        System.out.println("No hay productos disponibles para eliminar.");
                    } else {
                        for (Producto producto : productosEliminar) {
                            System.out.println(producto);
                        }
                        try {
                            scanner.nextLine();

                            System.out.print("Ingrese el ID del producto a eliminar: ");
                            Long id = scanner.nextLong();
                            scanner.nextLine();

                            // Pedir confirmación antes de realizar la baja lógica
                            System.out.print("¿Está seguro? (S/N): ");
                            String confirmacion = scanner.nextLine();

                            if (confirmacion.equalsIgnoreCase("S")) {
                                pService.eliminarProducto(id);
                                System.out.println("Producto con ID: " + id + " eliminado correctamente.");
                            } else {
                                System.out.println("Operación cancelada.");
                            }

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
            opcion = scanner.nextInt();

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
                        scanner.nextLine(); // Limpiamos el buffer

                        System.out.print("Ingrese el ID: ");
                        Long id = scanner.nextLong();
                        scanner.nextLine();

                        if (uService.buscarPorId(id) != null) {
                            System.out.println("Error: ya existe un usuario con ID: " + id);
                            break;
                        }

                        System.out.print("Ingrese el nombre: ");
                        String nombre = scanner.nextLine();

                        System.out.print("Ingrese el mail: ");
                        String mail = scanner.nextLine();

                        System.out.print("Ingrese el apellido: ");
                        String apellido = scanner.nextLine();

                        System.out.print("Ingrese el celular: ");
                        String celular = scanner.nextLine();

                        System.out.print("Ingrese la contraseña: ");
                        String contrasena = scanner.nextLine();

                        System.out.println("Rol: 1. USUARIO | 2. ADMIN");
                        int rolOpcion = scanner.nextInt();
                        scanner.nextLine();
                        Rol rol = rolOpcion == 2 ? Rol.ADMIN : Rol.USUARIO;

                        Usuario nuevoUsuario = new Usuario(id, nombre, apellido, mail, celular, contrasena, rol);

                        uService.agregarUsuario(nuevoUsuario);
                        System.out.println("Usuario agregado correctamente con ID: " + id);

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
                            scanner.nextLine();

                            System.out.print("Ingrese el ID del usuario a editar: ");
                            Long id = scanner.nextLong();
                            scanner.nextLine();

                            System.out.print("Ingrese el nuevo nombre: ");
                            String nombre = scanner.nextLine();

                            System.out.print("Ingrese el nuevo apellido: ");
                            String apellido = scanner.nextLine();

                            System.out.print("Ingrese el nuevo mail: ");
                            String mail = scanner.nextLine();

                            System.out.print("Ingrese el nuevo celular: ");
                            String celular = scanner.nextLine();

                            System.out.print("Ingrese la nueva contraseña: ");
                            String contrasena = scanner.nextLine();

                            uService.editarUsuario(id, nombre, apellido, mail, celular, contrasena);
                            System.out.println("Usuario con ID: " + id + " editado correctamente.");

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
                            scanner.nextLine();
                            System.out.print("Ingrese el ID del usuario a eliminar: ");
                            Long id = scanner.nextLong();
                            scanner.nextLine();

                            System.out.print("¿Está seguro que desea dar de baja al usuario? (S/N): ");
                            String confirmacion = scanner.nextLine();

                            if (confirmacion.equalsIgnoreCase("S")) {
                                uService.eliminarUsuario(id);
                                System.out.println("Usuario con ID: " + id + " eliminado correctamente.");
                            } else {
                                System.out.println("Operación cancelada.");
                            }
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
            opcion = scanner.nextInt();

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
                        Long idPedido = scanner.nextLong();

                        System.out.print("Ingrese el ID del usuario que realiza el pedido: ");
                        Long idUsuario = scanner.nextLong();

                        // Buscamos si el usuario existe antes de hacer el pedido
                        Usuario user = uService.buscarPorId(idUsuario);
                        if (user == null) {
                            System.out.println("No se encontró un usuario con el ID ingresado.");
                            break;
                        }

                        // Menú rápido para la forma de pago
                        System.out.println("Formas de Pago: 1. EFECTIVO | 2. TARJETA | 3. TRANSFERENCIA");
                        System.out.print("Seleccione (1-3): ");
                        int fpOpcion = scanner.nextInt();
                        FormaPago fp = FormaPago.EFECTIVO; // Por defecto
                        if (fpOpcion == 2)
                            fp = FormaPago.TARJETA;
                        if (fpOpcion == 3)
                            fp = FormaPago.TRANSFERENCIA;

                        // Creamos y guardamos el pedido
                        Pedido nuevoPedido = new Pedido(idPedido, user, fp);
                        pedService.crearPedido(nuevoPedido);
                        System.out.println("Pedido creado con éxito con estado PENDIENTE.");

                    } catch (IdDuplicadoException e) {
                        System.out.println("Error: " + e.getMessage());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }

                    break;

                case 3:
                    try {
                        System.out.print("Ingrese el ID del pedido: ");
                        Long idPed = scanner.nextLong();

                        System.out.print("Ingrese el ID del producto a agregar: ");
                        Long idProd = scanner.nextLong();

                        System.out.print("Ingrese la cantidad: ");
                        int cant = scanner.nextInt();

                        // Buscamos si el producto existe
                        Producto prod = pService.buscarPorId(idProd);
                        if (prod == null) {
                            System.out.println("No se encontró un producto con el ID ingresado.");
                            break;
                        }

                        pedService.agregarProductoAPedido(idPed, prod, cant);
                        System.out.println("Producto agregado correctamente al pedido.");

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
                        scanner.nextLine();
                        System.out.print("Ingrese el ID del pedido a dar de baja: ");
                        Long id = scanner.nextLong();
                        scanner.nextLine();

                        // Pedir confirmación antes de realizar la baja lógica
                        System.out.print("¿Está seguro que desea eliminar el pedido? (S/N): ");
                        String confirmacion = scanner.nextLine();

                        if (confirmacion.equalsIgnoreCase("S")) {
                            pedService.eliminarPedido(id);
                            System.out.println("Pedido con ID: " + id + " eliminado correctamente.");
                        } else {
                            System.out.println("Operación cancelada.");
                        }
                    } catch (IdNoEncontradoException e) {
                        System.out.println("Error: " + e.getMessage());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 5:
                    try {
                        // Mostramos los pedidos activos para que el usuario sepa qué ID ingresar
                        List<Pedido> pedidosActualizar = pedService.listarPedidos();
                        if (pedidosActualizar.isEmpty()) {
                            System.out.println("No hay pedidos activos.");
                            break;
                        }
                        for (Pedido p : pedidosActualizar) {
                            System.out.println(p);
                        }

                        scanner.nextLine();
                        System.out.print("Ingrese el ID del pedido a actualizar: ");
                        Long id = scanner.nextLong();
                        scanner.nextLine();

                        // Preguntamos qué quiere actualizar
                        System.out.println("¿Qué desea actualizar?");
                        System.out.println("1. Estado");
                        System.out.println("2. Forma de pago");
                        System.out.print("Seleccione: ");
                        int subOpcion = scanner.nextInt();
                        scanner.nextLine();

                        if (subOpcion == 1) {
                            // Mostramos los estados disponibles según el enum Estado
                            System.out.println("Estados disponibles:");
                            System.out.println("1. PENDIENTE");
                            System.out.println("2. CONFIRMADO");
                            System.out.println("3. TERMINADO");
                            System.out.println("4. CANCELADO");
                            System.out.print("Seleccione: ");
                            int estadoOpcion = scanner.nextInt();
                            scanner.nextLine();

                            // Convertimos la opción numérica al enum correspondiente
                            Estado nuevoEstado = Estado.PENDIENTE;
                            switch (estadoOpcion) {
                                case 1:
                                    nuevoEstado = Estado.PENDIENTE;
                                    break;
                                case 2:
                                    nuevoEstado = Estado.CONFIRMADO;
                                    break;
                                case 3:
                                    nuevoEstado = Estado.TERMINADO;
                                    break;
                                case 4:
                                    nuevoEstado = Estado.CANCELADO;
                                    break;
                                default:
                                    System.out.println("Opción inválida.");
                                    break;
                            }
                            // Delegamos al service la actualización del estado
                            pedService.cambiarEstadoPedido(id, nuevoEstado);
                            System.out.println("Estado del pedido con ID: " + id + " actualizado correctamente.");

                        } else if (subOpcion == 2) {
                            // Mostramos las formas de pago disponibles según el enum FormaPago
                            System.out.println("Formas de pago disponibles:");
                            System.out.println("1. EFECTIVO");
                            System.out.println("2. TARJETA");
                            System.out.println("3. TRANSFERENCIA");
                            System.out.print("Seleccione: ");
                            int fpOpcion = scanner.nextInt();
                            scanner.nextLine();

                            // Convertimos la opción numérica al enum correspondiente
                            FormaPago nuevaFormaPago = FormaPago.EFECTIVO;
                            switch (fpOpcion) {
                                case 1:
                                    nuevaFormaPago = FormaPago.EFECTIVO;
                                    break;
                                case 2:
                                    nuevaFormaPago = FormaPago.TARJETA;
                                    break;
                                case 3:
                                    nuevaFormaPago = FormaPago.TRANSFERENCIA;
                                    break;
                                default:
                                    System.out.println("Opción inválida.");
                                    break;
                            }
                            // Delegamos al service la actualización de la forma de pago
                            pedService.cambiarFormaPagoPedido(id, nuevaFormaPago);
                            System.out
                                    .println("Forma de pago del pedido con ID: " + id + " actualizada correctamente.");

                        } else {
                            System.out.println("Opción inválida.");
                        }

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