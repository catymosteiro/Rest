package edu.upc.dsa;

import edu.upc.dsa.models.Pedido;
import edu.upc.dsa.models.Product;
import edu.upc.dsa.models.Usuario;
import edu.upc.dsa.util.ProductManagerImpl;
import org.javatuples.Pair;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class test {

    Product cola;
    Product pepsi;
    Product zumo;
    ProductManagerImpl productManager;

    @Before
    public void setUp(){
        cola = new Product("cola",2,5);
        pepsi = new Product("pepsi",2.5,3);
        zumo = new Product("zumo",1.5,8);
        productManager = ProductManagerImpl.getInstance();
        productManager.addtoListaProductos(List.of(cola, pepsi, zumo));

        productManager.addUser(new Usuario("Pau"));
        productManager.addUser(new Usuario("Caty"));
        productManager.addUser(new Usuario("Julia"));

        List<Pair<Product, Integer>> prodList = new ArrayList<>(List.of(new Pair<>(cola, 4), new Pair<>(pepsi, 2), new Pair<>(zumo, 8)));
        productManager.realizarPedido(new Pedido(productManager.getUsuarioHashMap().get("Pau"), prodList));
    }

    @After
    public void tearDown(){
        productManager.cleanCache();
    }

    @Test
    public void testSortByPrice(){


        Product[] arr = {zumo,cola,pepsi};
        Assert.assertArrayEquals(arr,productManager.sortByPrice().toArray());
    }

    @Test
    public void testSortBySells(){
        Product[] arr = {zumo,cola,pepsi};
        Assert.assertArrayEquals(arr,productManager.sortBySells().toArray());
    }

    @Test
    public void testRealizarPedido(){
        testServirPedido();
        List<Pair<Product, Integer>> prodList = new ArrayList<>(List.of(new Pair<>(cola, 2), new Pair<>(pepsi, 1), new Pair<>(zumo, 10)));
        Pedido pedido = new Pedido(productManager.getUsuarioHashMap().get("Caty"), prodList);
        productManager.realizarPedido(pedido);
        Pedido[] arr = {pedido};
        Assert.assertArrayEquals(arr, productManager.getColaPedidos().toArray());

        Assert.assertArrayEquals(arr, productManager.getUsuarioHashMap().get("Caty").getListaPedidos().toArray());
        Assert.assertEquals( 6,pedido.getProducts().get(0).getValue0().getCantidadVendidos());

    }

    @Test
    public void testServirPedido(){
        productManager.servirPedido();
        Pedido[] arr = {};
        Assert.assertArrayEquals(arr,productManager.getColaPedidos().toArray());
    }
/*
    @Test
    public void testPedidosDeUsuario(){
        testPedidosDeUsuario();
        List<Pair<Product, Integer>> prodList = new ArrayList<>(List.of(new Pair<>(cola, 1), new Pair<>(zumo, 3)));
        Pedido pedido = new Pedido(productManager.getUsuarioHashMap().get("Julia"), prodList);
        List<Pedido> lista = productManager.pedidosDeUsuario(productManager.getUsuarioHashMap().get("Julia"));
        Assert.assertEquals(lista.get(0).getUsuario().getNombre(),"Julia");
    }
 */
}
