package sv.edu.udb.iwfashionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.nex3z.notificationbadge.NotificationBadge;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sv.edu.udb.iwfashionapp.interfaces.productoAPI;
import sv.edu.udb.iwfashionapp.models.Cliente;
import sv.edu.udb.iwfashionapp.models.Producto;
import sv.edu.udb.iwfashionapp.services.DataBaseUtilities;

public class DetalleProductoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    DrawerLayout drawerLayout;
    TextView Txt_slug,Txt_Description,Txt_sales_price;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    ImageView imgProduct;
    ImageView CartBtn;
    NotificationBadge notificationBadge;
    Button BtnAddProduct;
    int id_product=0;
    int id_cliente_global=0;
    double precio=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto);

        Bundle bundle = getIntent().getExtras();
        String id=bundle.getString("id_product");
        id_product=Integer.parseInt(id);
        String url_img=bundle.getString("url_img");
        String description=bundle.getString("description");
        String slug=bundle.getString("slug");
        double sales_price=bundle.getDouble("sales_price");

        precio=sales_price;
        BtnAddProduct=findViewById(R.id.btn_agregar);
        CartBtn=findViewById(R.id.cart_img2);
        notificationBadge=findViewById(R.id.badge2);
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



        CartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2,new CartFragment()).commit();
            }
        });

        DataBaseUtilities dataBaseUtilities =new DataBaseUtilities();
        Cliente _cliente=dataBaseUtilities.GetActiveClient(this);
        id_cliente_global=_cliente.getId_cliente();
        CountItemsCar();


        dataBaseUtilities.InsertProduct(this,id_product,slug,description,url_img,precio);

    }
    public void CountItemsCar()
    {
        int total_items = 0;
        SqlLiteOpenHelperAdmin admin = new SqlLiteOpenHelperAdmin(this,"iwfashion_database",null,1);

        SQLiteDatabase database = admin.getReadableDatabase();

        Cursor fila0 = database.rawQuery("SELECT COUNT(id_item) FROM carrito WHERE id_cliente="+String.valueOf(id_cliente_global)+"",null);

        if(fila0.moveToFirst())
        {
            total_items = fila0.getInt(0);
        }

        notificationBadge.setNumber(total_items);

    }
    public void AddToCart(View view)
    {
        DataBaseUtilities dataBaseUtilities =new DataBaseUtilities();
        Cliente _cliente=dataBaseUtilities.GetActiveClient(this);
        dataBaseUtilities.AddToCart(this,_cliente.getId_cliente(),id_product);
        CountItemsCar();
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