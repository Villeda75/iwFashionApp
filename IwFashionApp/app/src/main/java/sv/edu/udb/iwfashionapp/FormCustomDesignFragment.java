package sv.edu.udb.iwfashionapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import sv.edu.udb.iwfashionapp.interfaces.CustomDesignAPI;
import sv.edu.udb.iwfashionapp.interfaces.CustomDesign_RequestAPI;
import sv.edu.udb.iwfashionapp.models.CustomDesign;
import sv.edu.udb.iwfashionapp.models.CustomDesign_Request;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FormCustomDesignFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FormCustomDesignFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FormCustomDesignFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FormCustomDesignFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FormCustomDesignFragment newInstance(String param1, String param2) {
        FormCustomDesignFragment fragment = new FormCustomDesignFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    //TODO: Metodo on create para inicializar todos los valores como una activity
    ImageView imagen;
    TextView Txt_Description,Txt_EmailDesign,Txt_TelDesign;
    String fotoEnBase64="";
    String fotoNombre="";
    Button Btn_Send_CDesign;

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
        return inflater.inflate(R.layout.fragment_form_custom_design, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imagen=view.findViewById(R.id.imageViewCustomDesign);
        Txt_Description=view.findViewById(R.id.txt_Description_design);
        Txt_EmailDesign=view.findViewById(R.id.txt_EmailDesgin);
        Txt_TelDesign=view.findViewById(R.id.txt_TelDesgin);
        Btn_Send_CDesign=view.findViewById(R.id.button_send_Cdesign);


        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CargarImage();
                Toast.makeText(getContext(),"Se ha cargado la imagen",Toast.LENGTH_LONG).show();
            }
        });

        Btn_Send_CDesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_client=Txt_EmailDesign.getText().toString();
                String message=Txt_Description.getText().toString();
                String tel_client=Txt_TelDesign.getText().toString();
              SendForm("Marcos",email_client,tel_client,fotoNombre,fotoEnBase64,message);
                Toast.makeText(getContext(),"Diseño solicitado exitosamente",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void CargarImage()
    {
        Intent intent =new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione la aplicacion"),10);

    }

    public void SendForm(String _name,String _email, String _tel,String _nombre_archivo,String _ImgBase64,String _message)
    {


       CustomDesign_Request customDesign_requestObject=new CustomDesign_Request(_name,_email,_tel,_message,_nombre_archivo,_ImgBase64);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://veterinarialissette-vc170991-aa170621.000webhostapp.com/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CustomDesign_RequestAPI customDesign_requestAPI=retrofit.create(CustomDesign_RequestAPI.class);
        Call<CustomDesign_Request> call= customDesign_requestAPI.InsertCustomDesign_Request(customDesign_requestObject);

        call.enqueue(new Callback<CustomDesign_Request>() {
            @Override
            public void onResponse(Call<CustomDesign_Request> call, Response<CustomDesign_Request> response) {
                
                if(response.isSuccessful()) {

                    System.out.println("message: "+response.message());
                    System.out.println("codigo: "+response.code());
                    System.out.println("body: "+response.body());
                    Toast.makeText(FormCustomDesignFragment.this.getContext(), response.message(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(FormCustomDesignFragment.this.getContext(), "Codigo: " + response.code(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(FormCustomDesignFragment.this.getContext(), "headers: " + response.headers().toString(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(FormCustomDesignFragment.this.getContext(), "body: " + response.body(), Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(FormCustomDesignFragment.this.getContext(),"Ocurrió un error",Toast.LENGTH_LONG);
                }

            }

            @Override
            public void onFailure(Call<CustomDesign_Request> call, Throwable t) {

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK)
        {
            Uri path=data.getData();
            imagen.setImageURI(path);
            fotoNombre=String.valueOf(path.getLastPathSegment());


            Toast.makeText(FormCustomDesignFragment.this.getContext(),fotoNombre,Toast.LENGTH_LONG).show();



            Bitmap bitmap = ((BitmapDrawable) imagen.getDrawable()).getBitmap();
            if (bitmap != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                fotoEnBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);


                // Bitmap decodedImage = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                // img2.setImageBitmap(decodedImage);

            }

        }
    }
}