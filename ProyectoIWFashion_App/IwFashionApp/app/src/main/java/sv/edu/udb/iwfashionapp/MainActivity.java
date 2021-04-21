package sv.edu.udb.iwfashionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sv.edu.udb.iwfashionapp.interfaces.productoAPI;
import sv.edu.udb.iwfashionapp.models.Producto;
import sv.edu.udb.iwfashionapp.services.CustomAdapter;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    EditText edtCodigo;
    TextView tvNombre;
    TextView tvDescripcion;
    TextView tvPrecio;
    ImageView imgproducto;
    ListView listviewProductos;

    Button BtnFind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.producto_layout);
        setContentView(R.layout.home);
        edtCodigo=findViewById(R.id.edtCodigo);
        tvNombre=findViewById(R.id.tvNombre);
        tvDescripcion=findViewById(R.id.tvDescripcion);
        tvPrecio=findViewById(R.id.tvPrecio);
        imgproducto=findViewById(R.id.imgProducto);

        BtnFind=findViewById(R.id.btnBuscar);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView=findViewById(R.id.navigation_view);
        drawerLayout=findViewById(R.id.drawer_layout);
        NavigationView navigationView=findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        listviewProductos=findViewById(R.id.ListViewProducts);

        ActionBarDrawerToggle toggle =new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_drawer,R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();



    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }
    public void Buscar(View view)
    {
        findProduct(edtCodigo.getText().toString());
    }


    public void findProduct(String id)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://veterinarialissette-vc170991-aa170621.000webhostapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        productoAPI productAPI=retrofit.create(productoAPI.class);
        Call<Producto> call=productAPI.find(id);

        call.enqueue(new Callback<Producto>() {
            @Override
            public void onResponse(Call<Producto> call, Response<Producto> response) {

                try {
                    if(response.isSuccessful())
                    {
                    //    response.body().getResults().get(0).getSlug();

                        Producto p=response.body();

                        tvNombre.setText(p.getResults().get(0).getSlug().toString());
                        //tvDescripcion.setText(p.getResults().get(0).getDescription().toString());
                        tvPrecio.setText(String.valueOf(p.getResults().get(0).getDiscount_price()).toString());
                        Glide.with(getApplication()).load(p.getResults().get(0).getUrl_img()).into(imgproducto);


                    }

                }catch(Exception exception)
                {
                    Toast.makeText(MainActivity.this,exception.toString(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Producto> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Ocurrio un error",Toast.LENGTH_SHORT).show();
            }
        });
    }





    //Metodo para verificar que item de menu se selecciona y luego realizar una accion
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.hombres_item:

                // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MenFragment()).commit();
        break;
            case R.id.mujeres_item:
               // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new WomenFragment()).commit();
                break;
            case R.id.kids_item:

             //   getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new KidsFragment()).commit();
                break;

            case R.id.custom_design_item:



             //   getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new CustomDesignsFragment()).commit();
                break;
            case R.id.form_custom_design:


             //   getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new FormCustomDesignFragment()).commit();
                break;

        }

        return true;

    }
}