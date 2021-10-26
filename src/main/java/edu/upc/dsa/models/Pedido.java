package edu.upc.dsa.models;

import org.javatuples.Pair;

import java.util.List;

public class Pedido {
    private Usuario usuario;
    private List <Pair<Product, Integer>> products;

    public Pedido(Usuario usuario, List<Pair<Product, Integer>> products) {
        this();
        this.usuario = usuario;
        this.products = products;
    }
    public Pedido (){}

    public void setProducts(List<Pair<Product, Integer>> products) {
        this.products = products;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public List<Pair<Product, Integer>> getProducts() {
        return products;
    }
}
