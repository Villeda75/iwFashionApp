package sv.edu.udb.iwfashionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.nex3z.notificationbadge.NotificationBadge;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sv.edu.udb.iwfashionapp.interfaces.productoAPI;
import sv.edu.udb.iwfashionapp.models.Cliente;
import sv.edu.udb.iwfashionapp.models.Producto;
import sv.edu.udb.iwfashionapp.services.CustomAdapter;
import sv.edu.udb.iwfashionapp.services.DataBaseUtilities;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    NotificationBadge notificationBadge;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    EditText edtCodigo;
    TextView tvNombre;
    TextView tvDescripcion;
    TextView tvPrecio;
    ImageView imgproducto;
    ImageView CartBtn;
    ListView listviewProductos;
    FirebaseAuth firebaseAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions gso;
    Button BtnFind;
    int id_cliente_global=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        edtCodigo=findViewById(R.id.edtCodigo);
        tvNombre=findViewById(R.id.tvNombre);
        tvDescripcion=findViewById(R.id.tvDescripcion);
        tvPrecio=findViewById(R.id.tvPrecio);
        imgproducto=findViewById(R.id.imgProducto);
        firebaseAuth = FirebaseAuth.getInstance();
        BtnFind=findViewById(R.id.btnBuscar);
//sirve para mostrar la notificacion que muestra los articulos en carrito
        notificationBadge=findViewById(R.id.badge);



        CartBtn=findViewById(R.id.cart_img);

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

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        CartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new CartFragment()).commit();
            }
        });


        DataBaseUtilities dataBaseUtilities =new DataBaseUtilities();

        Cliente _cliente=dataBaseUtilities.GetActiveClient(this);
        if(_cliente!=null)
        {
            id_cliente_global=_cliente.getId_cliente();
            CountItemsCar();
        }


    }






    //Este metodo sirve para asignarle el numero de items en el carrito asociados al cliente
    public void CountItemsCar()
    {
        int total_items = 0;
        SqlLiteOpenHelperAdmin admin = new SqlLiteOpenHelperAdmin(this,"iwfashion_database",null,1);

        SQLiteDatabase database = admin.getReadableDatabase();

            Cursor fila0 = database.rawQuery("SELECT COUNT(id_item) FROM carrito WHERE id_cliente=" + String.valueOf(id_cliente_global) + "", null);

            if (fila0.moveToFirst()) {
                total_items = fila0.getInt(0);
            }

            notificationBadge.setNumber(total_items);

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
        Call<Producto> call=productAPI.findProduct(id);

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

                 getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MenFragment()).commit();
        break;
            case R.id.mujeres_item:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new WomenFragment()).commit();
                break;
            case R.id.kids_item:

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new KidsFragment()).commit();
                break;

            case R.id.custom_design_item:



                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new CustomDesignsFragment()).commit();
                break;
            case R.id.form_custom_design:


                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new FormCustomDesignFragment()).commit();
                break;

            case R.id.item_logout:

                closeFirebaseAccount();
                closeFacebookAccount();
                closeGoogleAccount();
                DataBaseUtilities dataBaseUtilities=new DataBaseUtilities();
                dataBaseUtilities.LogOutSqlLiteSession(this);
                break;

        }

        return true;

    }

    public void closeFirebaseAccount() {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(MainActivity.this, "Sesión cerrada con éxito", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(MainActivity.this,Login.class);
        startActivity(i);
        MainActivity.this.finish();
    }

    public void closeFacebookAccount() {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        Toast.makeText(MainActivity.this, "Sesión cerrada con éxito", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(MainActivity.this,Login.class);
        startActivity(i);
        MainActivity.this.finish();
    }

    public void closeGoogleAccount() {
        FirebaseAuth.getInstance().signOut();
        mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent mainActivity = new Intent(getApplicationContext(), Login.class);
                    startActivity(mainActivity);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "No se pudo cerrar sesión con google",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        Toast.makeText(MainActivity.this, "Sesión cerrada con éxito", Toast.LENGTH_SHORT).show();
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Intent IntentMainActivity = new Intent(getApplicationContext(), Login.class);
                IntentMainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(IntentMainActivity);
                finish();
            }
        });
    }
}