package edu.upc.dsa.services;


import edu.upc.dsa.TracksManager;
import edu.upc.dsa.TracksManagerImpl;
import edu.upc.dsa.models.Pedido;
import edu.upc.dsa.models.Product;
import edu.upc.dsa.models.Track;
import edu.upc.dsa.models.Usuario;
import edu.upc.dsa.util.ProductManager;
import edu.upc.dsa.util.ProductManagerImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.javatuples.Pair;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Api(value = "/products", description = "Endpoint to Products Service")
@Path("/products")
public class ProductService {

    private ProductManager tm;

    public ProductService() {
        this.tm = ProductManagerImpl.getInstance();
        if (tm.sortByPrice().size()==0) {
            Product cola;
            Product pepsi;
            Product zumo;

            cola = new Product("cola",2,5);
            pepsi = new Product("pepsi",2.5,3);
            zumo = new Product("zumo",1.5,8);
            ProductManagerImpl.getInstance().addtoListaProductos(List.of(cola, pepsi, zumo));

            ProductManagerImpl.getInstance().addUser(new Usuario("Pau"));
            ProductManagerImpl.getInstance().addUser(new Usuario("Caty"));
            ProductManagerImpl.getInstance().addUser(new Usuario("Julia"));

            List<Pair<Product, Integer>> prodList = new ArrayList<>(List.of(new Pair<>(cola, 4), new Pair<>(pepsi, 2), new Pair<>(zumo, 8)));
            ProductManagerImpl.getInstance().realizarPedido(new Pedido(ProductManagerImpl.getInstance().getUsuarioHashMap().get("Pau"), prodList));
        }
    }

    @GET
    @ApiOperation(value = "get all product sort by price", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Product.class, responseContainer="List"),
    })
    @Path("/sortbyprice")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductsSortByPrice() {

        List<Product> listaDeProductos1 = this.tm.sortByPrice();

        GenericEntity<List<Product>> entity = new GenericEntity<List<Product>>(listaDeProductos1) {};
        return Response.status(201).entity(entity).build()  ;

    }


    @GET
    @ApiOperation(value = "get all product sort by sells", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Product.class, responseContainer="List"),
    })
    @Path("/sortbysells")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductsSortBySells() {

        List<Product> listaDeProductos2 = this.tm.sortBySells();

        GenericEntity<List<Product>> entity = new GenericEntity<List<Product>>(listaDeProductos2) {};
        return Response.status(201).entity(entity).build()  ;

    }

    @POST
    @ApiOperation(value = "create a new Pedido", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=Pedido.class),
            @ApiResponse(code = 500, message = "Validation Error")
    })

    @Path("/realizarPedido")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response realizarPedido(Pedido pedido) {

        if (pedido.getUsuario()==null || pedido.getProducts()==null)  return Response.status(500).entity(pedido).build();
        this.tm.realizarPedido(pedido);
        return Response.status(201).entity(pedido).build();
    }

    @DELETE
    @ApiOperation(value = "servir pedido", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful",response=Pedido.class),
            @ApiResponse(code = 404, message = "Track not found")
    })
    @Path("servirPedido")
    public Response servirPedido() {
        if (tm.sortByPrice() == null) return Response.status(404).build();
        else this.tm.servirPedido();
        return Response.status(201).build();
    }

/*
    @GET
    @ApiOperation(value = "get a Track", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Track.class),
            @ApiResponse(code = 404, message = "Track not found")
    })
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTrack(@PathParam("id") String id) {
        Track t = this.tm.getTrack(id);
        if (t == null) return Response.status(404).build();
        else  return Response.status(201).entity(t).build();
    }

    @DELETE
    @ApiOperation(value = "delete a Track", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Track not found")
    })
    @Path("/{id}")
    public Response deleteTrack(@PathParam("id") String id) {
        Track t = this.tm.getTrack(id);
        if (t == null) return Response.status(404).build();
        else this.tm.deleteTrack(id);
        return Response.status(201).build();
    }

    @PUT
    @ApiOperation(value = "update a Track", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Track not found")
    })
    @Path("/")
    public Response updateTrack(Track track) {

        Track t = this.tm.updateTrack(track);

        if (t == null) return Response.status(404).build();

        return Response.status(201).build();
    }



    @POST
    @ApiOperation(value = "create a new Track", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=Track.class),
            @ApiResponse(code = 500, message = "Validation Error")

    })

    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newTrack(Track track) {

        if (track.getSinger()==null || track.getTitle()==null)  return Response.status(500).entity(track).build();
        this.tm.addTrack(track);
        return Response.status(201).entity(track).build();
    }
*/
}