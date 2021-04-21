package sv.edu.udb.iwfashionapp.interfaces;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import sv.edu.udb.iwfashionapp.models.Producto;

public interface productoAPI {

    @GET("products/{id}")
    public Call<Producto> find(@Path("id") String id);

    @GET("products/")
    Call<Producto> getAllProducts();

//Hombres

    @GET("products-men")
    Call<Producto> getMenProducts();

    @GET("clothes/men")
    Call<Producto> getMenClothes();

    @GET("shoes/men")
    Call<Producto> getMenShoes();


    @GET("accesories/men")
    Call<Producto> getMenAccesories();


    //Mujeres
    @GET("products-women")
    Call<Producto> getWomenProducts();

    @GET("clothes/women")
    Call<Producto> getWomenClothes();

    @GET("shoes/women")
    Call<Producto> getWomenShoes();


    @GET("accesories/women")
    Call<Producto> getWomenAccesories();


    //Niños


    @GET("products-kids")
    Call<Producto> getKidsProducts();

    @GET("clothes/kids")
    Call<Producto> getKidsClothes();


    @GET("shoes/kids")
    Call<Producto> getKidsShoes();


    @GET("accesories/kids")
    Call<Producto> getKidsAccesories();





}
