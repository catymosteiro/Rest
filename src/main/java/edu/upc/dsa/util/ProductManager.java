package edu.upc.dsa.util;

import edu.upc.dsa.models.Pedido;
import edu.upc.dsa.models.Product;
import edu.upc.dsa.models.Usuario;

import java.util.List;

public interface ProductManager {
    List<Product> sortByPrice();

    List<Product> sortBySells();

    void realizarPedido(Pedido p);

    void servirPedido();

    List<Pedido> pedidosDeUsuario(Usuario u);
}
