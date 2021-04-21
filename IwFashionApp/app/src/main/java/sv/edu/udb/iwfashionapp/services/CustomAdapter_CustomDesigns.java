package sv.edu.udb.iwfashionapp.services;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sv.edu.udb.iwfashionapp.R;
import sv.edu.udb.iwfashionapp.interfaces.CustomDesignAPI;
import sv.edu.udb.iwfashionapp.models.CustomDesign;

public class CustomAdapter_CustomDesigns extends BaseAdapter{
    Context context;
    List<CustomDesign.item> list;
    public CustomAdapter_CustomDesigns(Context context, java.util.List<CustomDesign.item> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ImageView imageCustomDesign,Btn_delete_custom_design;
        TextView TxtNombre;
        TextView TxtDescrition;
        Button btn_reserva;


        CustomDesign.item item=list.get(i);

        if(view==null)
            view= LayoutInflater.from(context).inflate(R.layout.item_custom_design,null);

        imageCustomDesign=view.findViewById(R.id.imageViewCdesign);
        TxtNombre=view.findViewById(R.id.nombre_Cdesign);
        TxtDescrition=view.findViewById(R.id.description_Cdesign);
        Btn_delete_custom_design=view.findViewById(R.id.btn_delete_custom_Design);
        btn_reserva=view.findViewById(R.id.btn_reservarCdesign);
        Glide.with(view)
                .load(item.getUrl_img().toString())
                .into(imageCustomDesign);

        TxtNombre.setText(item.getName());
        TxtDescrition.setText(item.getDescription());



        btn_reserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Se ha resevado este diseño",Toast.LENGTH_SHORT);
            }
        });

        Btn_delete_custom_design.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://veterinarialissette-vc170991-aa170621.000webhostapp.com/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                CustomDesignAPI customDesignAPI=retrofit.create(CustomDesignAPI.class);
                Call<CustomDesign.item> call=customDesignAPI.DeleteCustomDesign(String.valueOf(item.getId_custom_design()));

                call.enqueue(new Callback<CustomDesign.item>() {
                    @Override
                    public void onResponse(Call<CustomDesign.item> call, Response<CustomDesign.item> response) {

                        try {
                            if(response.isSuccessful())
                            {
                                //    response.body().getResults().get(0).getSlug();

                                CustomDesign.item p=response.body();

                                Toast.makeText(context,"codigo"+response.code(),Toast.LENGTH_LONG);
                                Toast.makeText(context,"codigo"+response.message(),Toast.LENGTH_LONG);

                            }

                        }catch(Exception exception)
                        {
                            Toast.makeText(context,exception.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CustomDesign.item> call, Throwable t) {
                        Toast.makeText(context,"Ocurrio un error",Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
        return view;
    }
}
