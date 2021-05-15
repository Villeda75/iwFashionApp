package sv.edu.udb.iwfashionapp.services;

import android.app.Application;
import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import sv.edu.udb.iwfashionapp.CartFragment;
import sv.edu.udb.iwfashionapp.R;
import sv.edu.udb.iwfashionapp.models.Cliente;
import sv.edu.udb.iwfashionapp.models.CustomDesign;
import sv.edu.udb.iwfashionapp.models.Producto;

public class CustomAdapterItemCart extends BaseAdapter{
    public CustomAdapterItemCart(Context context, List<Producto.item> list) {
        this.context = context;
        this.list = list;
    }

    Context context;
    List<Producto.item> list;
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
        //Se asocian los controles en la vista con el codigo para poder asginarle datos


        ImageView imageItemCar;
        TextView TxtNombre;
        TextView TxtDescription;
        TextView TxtCantidad;
        FloatingActionButton BtnPlus,BtnMinus;
        TextView Txtprice;


        Producto.item item=list.get(i);
        if(view==null)
            view= LayoutInflater.from(context).inflate(R.layout.item_cart,null);

        imageItemCar=view.findViewById(R.id.img_product_cart);
        TxtNombre=view.findViewById(R.id.name_product_cart);
        TxtDescription=view.findViewById(R.id.descrip_product_cart);
        TxtCantidad=view.findViewById(R.id.CantidadItemCart);
        BtnPlus=view.findViewById(R.id.FloatButtonPlus);
        BtnMinus=view.findViewById(R.id.floatingButtonMinus);
        Txtprice=view.findViewById(R.id.txt_price_item_cart);

        DataBaseUtilities dataBaseUtilities =new DataBaseUtilities();
        Cliente _cliente=dataBaseUtilities.GetActiveClient(view.getContext());



        TxtCantidad.setText(String.valueOf(item.getStock()));

        //Ahora se asigna los valores que se mostraran

        //se asigna la imagen
        Glide.with(view)
                .load(item.getUrl_img().toString())
                .into(imageItemCar);
        //Nombre
        TxtNombre.setText(item.getSlug());
        TxtDescription.setText(item.getDescription());
        Txtprice.setText(String.valueOf(item.getSales_price()));


//Aumentar Cantidad
        BtnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentTotal=0;
                currentTotal=Integer.parseInt(TxtCantidad.getText().toString());
                if(currentTotal<=99)
                {
                    TxtCantidad.setText(String.valueOf(currentTotal+1));


                    dataBaseUtilities.UpdateQuantity(context, _cliente.getId_cliente(),Integer.parseInt(item.getId_product()),currentTotal+1);
                }
                else
                {

                }

            }
        });
//Restar Cantidad
        BtnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentTotal=0;
                currentTotal=Integer.parseInt(TxtCantidad.getText().toString());
                if(currentTotal>0)
                {
                    TxtCantidad.setText(String.valueOf(currentTotal-1));

                    dataBaseUtilities.UpdateQuantity(context, _cliente.getId_cliente(),Integer.parseInt(item.getId_product()),currentTotal-1);

                }
                else
                {

                }
            }
        });

        return view;
    }
}