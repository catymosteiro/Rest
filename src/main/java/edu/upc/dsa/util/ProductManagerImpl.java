package edu.upc.dsa.util;

import edu.upc.dsa.models.Pedido;
import edu.upc.dsa.models.Product;
import edu.upc.dsa.models.Usuario;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ProductManagerImpl implements ProductManager{

    private static ProductManagerImpl instance;
    private Queue<Pedido> colaPedidos;
    private List<Product> listaProductos;
    private HashMap<String, Usuario> usuarioHashMap;
    private final static Logger logger = Logger.getLogger(ProductManagerImpl.class);

    private ProductManagerImpl() {
        colaPedidos = new LinkedList<>();
        listaProductos = new LinkedList<>();
        usuarioHashMap = new HashMap<>();
    }

    //singleton
    public static ProductManagerImpl getInstance(){
        logger.info("Access singleton instance");
        if(instance==null){ instance = new ProductManagerImpl(); logger.info("New singleton Instance");}
        return instance;
    }

    public void addtoListaProductos(List<Product> l){
        logger.info("Add to lista productos");
        logger.info(l);
        listaProductos.addAll(l);
        logger.info(listaProductos);
    }

    public void addUser(Usuario u){
        logger.info("Add user");
        logger.info(u);
        usuarioHashMap.put(u.getNombre(),u);
        logger.info(usuarioHashMap);
    }

    public HashMap<String, Usuario> getUsuarioHashMap() {
        return usuarioHashMap;
    }

    public Queue<Pedido> getColaPedidos() {
        return colaPedidos;
    }

    @Override
    public List<Product> sortByPrice() {
        logger.info(listaProductos);
        List<Product> l = new LinkedList<>(listaProductos);
        l.sort(Product::compareTo);
        logger.info(l);
        return l;
    }

    @Override
    public List<Product> sortBySells() {
        logger.info(listaProductos);
        List<Product> l = new LinkedList<>(listaProductos);
        l.sort((Product o1, Product o2) ->{return Integer.compare(o2.getCantidadVendidos(), o1.getCantidadVendidos());});
        logger.info(l);
        return l;
    }


    @Override
    public void realizarPedido(Pedido p) {
        logger.info(p);
        p.getProducts().forEach(productPair -> {
            productPair.getValue0().comprar(productPair.getValue1());
        });
        p.getUsuario().getListaPedidos().add(p);
        colaPedidos.add(p);
        logger.info(usuarioHashMap.get(p.getUsuario().getNombre()));
        logger.info(colaPedidos);
    }

    @Override
    public void servirPedido() {
        logger.info(colaPedidos);
        colaPedidos.poll();
        logger.info(colaPedidos);
    }

    @Override
    public List<Pedido> pedidosDeUsuario(Usuario u) {
        return u.getListaPedidos();
    }

    public void cleanCache() {
        this.colaPedidos.clear();
        this.listaProductos.clear();
        this.usuarioHashMap.clear();
    }
}
