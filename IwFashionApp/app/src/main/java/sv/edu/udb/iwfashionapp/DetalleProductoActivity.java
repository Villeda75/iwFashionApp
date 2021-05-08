package sv.edu.udb.iwfashionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
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

public class DetalleProductoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    DrawerLayout drawerLayout;
    TextView Txt_slug,Txt_Description,Txt_sales_price;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    ImageView imgProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto);

        Bundle bundle = getIntent().getExtras();
        String id=bundle.getString("id_product");
        String url_img=bundle.getString("url_img");
        String description=bundle.getString("description");
        String slug=bundle.getString("slug");
        double sales_price=bundle.getDouble("sales_price");


        imgProduct=findViewById(R.id.imageProduct);
        Txt_slug=findViewById(R.id.txt_slug);
        Txt_Description=findViewById(R.id.txt_description);
        Txt_sales_price=findViewById(R.id.txt_sales_price);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView=findViewById(R.id.navigation_view);
        drawerLayout=findViewById(R.id.drawer_layout);
        NavigationView navigationView=findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle =new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_drawer,R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        Glide.with(this)
                .load(url_img)
                .into(imgProduct);

       Txt_Description.setText(description);
       Txt_slug.setText(slug);
       Txt_sales_price.setText("$ " + String.valueOf(sales_price) );
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.hombres_item:

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2,new MenFragment()).commit();
                break;
            case R.id.mujeres_item:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2,new WomenFragment()).commit();
                break;
            case R.id.kids_item:

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2,new KidsFragment()).commit();
                break;

            case R.id.custom_design_item:


                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2,new CustomDesignsFragment()).commit();
                break;
            case R.id.form_custom_design:


                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2,new FormCustomDesignFragment()).commit();
                break;

        }

        return true;
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



}