package sv.edu.udb.iwfashionapp;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sv.edu.udb.iwfashionapp.interfaces.productoAPI;
import sv.edu.udb.iwfashionapp.models.Producto;
import sv.edu.udb.iwfashionapp.services.CustomAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenFragment newInstance(String param1, String param2) {
        MenFragment fragment = new MenFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    ListView listviewProductos;
    Button btn_ropa;
    Button btn_zapatos;
    Button btn_accesorios;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_men, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listviewProductos=view.findViewById(R.id.ListViewProducts);
        btn_ropa=view.findViewById(R.id.btn_clothes);
        btn_zapatos=view.findViewById(R.id.btn_shoes);
        btn_accesorios=view.findViewById(R.id.btn_accesories);
        final Button btn_todo=view.findViewById(R.id.btn_all);
        ListAllProducts(MenFragment.this.getContext(),1);

        btn_ropa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListAllProducts(MenFragment.this.getContext(),2);
            }
        });

        btn_zapatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListAllProducts(MenFragment.this.getContext(),3);
            }
        });

        btn_accesorios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListAllProducts(MenFragment.this.getContext(),4);
            }
        });

        btn_todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListAllProducts(MenFragment.this.getContext(),1);
            }
        });
    }
    public void ListAllProducts(Context context, int categorie)
    {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://veterinarialissette-vc170991-aa170621.000webhostapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        productoAPI productAPI=retrofit.create(productoAPI.class);

        Call<Producto> call;
        if(categorie==1) {
            call = productAPI.getMenProducts();
        }
        else if(categorie==2)
        {
            call = productAPI.getMenClothes();

        }
        else if(categorie==3)
        {
            call = productAPI.getMenShoes();
        }
        else
        {
            call = productAPI.getMenAccesories();
        }


        call.enqueue(new Callback<Producto>() {
            @Override
            public void onResponse(Call<Producto> call, Response<Producto> response) {

                try {
                    if(response.isSuccessful())
                    {
                        //    response.body().getResults().get(0).getSlug();

                        Producto _producto=response.body();

                        CustomAdapter adapter = new CustomAdapter(MenFragment.this.getContext(), _producto.getResults());
                        listviewProductos.setAdapter(adapter);

                    }

                }catch(Exception exception)
                {
                    Toast.makeText(context,exception.toString(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Producto> call, Throwable t) {
                Toast.makeText(context,"Ocurrio un error",Toast.LENGTH_SHORT).show();
            }
        });


    }

}