package sv.edu.udb.iwfashionapp;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sv.edu.udb.iwfashionapp.interfaces.CustomDesignAPI;
import sv.edu.udb.iwfashionapp.interfaces.productoAPI;
import sv.edu.udb.iwfashionapp.models.CustomDesign;
import sv.edu.udb.iwfashionapp.models.Producto;
import sv.edu.udb.iwfashionapp.services.CustomAdapter;
import sv.edu.udb.iwfashionapp.services.CustomAdapter_CustomDesigns;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomDesignsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomDesignsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CustomDesignsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CustomDesignsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CustomDesignsFragment newInstance(String param1, String param2) {
        CustomDesignsFragment fragment = new CustomDesignsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    ListView listviewCustomDesign;
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
        return inflater.inflate(R.layout.fragment_custom_designs, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listviewCustomDesign = view.findViewById(R.id.ListView_CustomDesigns);
        ListAllCustomDesigns(CustomDesignsFragment.this.getContext());
    }
    public void ListAllCustomDesigns(Context context)
    {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://veterinarialissette-vc170991-aa170621.000webhostapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CustomDesignAPI customDesignAPI=retrofit.create(CustomDesignAPI.class);

        Call<CustomDesign> call;

            call = customDesignAPI.getCustomDesigns();



        call.enqueue(new Callback<CustomDesign>() {
            @Override
            public void onResponse(Call<CustomDesign> call, Response<CustomDesign> response) {

                try {
                    if(response.isSuccessful())
                    {
                        //    response.body().getResults().get(0).getSlug();

                        CustomDesign _customDesign=response.body();

                        CustomAdapter_CustomDesigns adapter = new CustomAdapter_CustomDesigns(CustomDesignsFragment.this.getContext(), _customDesign.getResults());
                        listviewCustomDesign.setAdapter(adapter);

                    }

                }catch(Exception exception)
                {
                    Toast.makeText(context,exception.toString(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CustomDesign> call, Throwable t) {
                Toast.makeText(context,"Ocurrió un error",Toast.LENGTH_SHORT).show();
            }
        });

    }
}