package integrado.prog2;

import integrado.prog2.entities.Categoria;
import integrado.prog2.entities.Producto;
import integrado.prog2.service.CategoriaService;
import integrado.prog2.service.ProductoService;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        ProductoService pService = new ProductoService();
        CategoriaService cService = new CategoriaService();
        System.out.println("Sistema Food Store iniciado");
        Categoria c = new Categoria(1l, "postre", "postres frios");
        Producto p = new Producto(1l, "helado", 100.00, "palito bombom", 2, "helado.png", true, c);
        Producto p1 = new Producto(2l, "heladoss", 100.00, "palito bombom", 2, "helado.png", true, c);
        
        
       cService.agregarCategoria(c);
       pService.agregarProducto(p);
       pService.agregarProducto(p1);
       
       pService.eliminarProducto(1l);
       
       cService.editarCategoria(1l, "helados", "palitos bombones");
       
       pService.editarProducto(1l, 300, 10, c);
       pService.editarProducto(2l, 300, 10, c);
       
       
       List<Producto> pLi = pService.listarProductos();
        for (Producto producto : pLi) {
            System.out.println(producto +" "+producto.getCategoria());
        }
       List<Categoria> cLi = cService.listarCategorias();
        for (Categoria categoria : cLi) {
            System.out.println(categoria);   
        }
        
        
        
       
    }
}