package sv.edu.udb.iwfashionapp;

import android.app.Application;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nex3z.notificationbadge.NotificationBadge;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sv.edu.udb.iwfashionapp.interfaces.productoAPI;
import sv.edu.udb.iwfashionapp.models.Cliente;
import sv.edu.udb.iwfashionapp.models.OrderPurchase;
import sv.edu.udb.iwfashionapp.models.Producto;
import sv.edu.udb.iwfashionapp.services.CustomAdapter;
import sv.edu.udb.iwfashionapp.services.CustomAdapterItemCart;
import sv.edu.udb.iwfashionapp.services.DataBaseUtilities;
import sv.edu.udb.iwfashionapp.services.DatabaseAPI;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    ListView listviewCart;
    TextView total_cart;
    Button btn_update;
    Button btn_pay;

    NotificationBadge notificationBadge;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DataBaseUtilities dataBaseUtilities = new DataBaseUtilities();
        listviewCart=view.findViewById(R.id.ListViewCart);
        total_cart=view.findViewById(R.id.txt_total_cart);
        btn_update=view.findViewById(R.id.btn_update_cart);
        btn_pay=view.findViewById(R.id.btn_payment);



        Cliente _cliente = dataBaseUtilities.GetActiveClient(this.getContext());
        if (_cliente != null) {
//Aqui se carga la informacion del carrito


         List<Producto.item> lista=dataBaseUtilities.ListIdsFromActiveUser(this.getContext(),_cliente.getId_cliente());



    CustomAdapterItemCart adapter = new CustomAdapterItemCart(CartFragment.this.getContext(), lista);
    listviewCart.setAdapter(adapter);

    if(lista.size()==0)
    {
        btn_pay.setVisibility(View.INVISIBLE);
    }
    else
    {
        btn_pay.setVisibility(View.VISIBLE);
    }



    total_cart.setText("Total: $"+String.valueOf(dataBaseUtilities.CalculateTotal(CartFragment.this.getContext(),_cliente.getId_cliente())));




        } else {
            Toast.makeText(this.getContext(), "Registrarse para agregar al carrito", Toast.LENGTH_LONG).show();
        }

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                total_cart.setText("Total: $"+String.valueOf(dataBaseUtilities.CalculateTotal(CartFragment.this.getContext(),_cliente.getId_cliente())));
            }
        });

        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String title = "Consulta";
                String message = "¿Esta seguro/a de realizar el pago?";
                String strbt1 = "ACEPTAR"; String strbt2 = "CANCELAR";
                AlertDialog.Builder ad = new AlertDialog.Builder(v.getContext());
                ad.setTitle(title);	ad.setIcon(android.R.drawable.ic_dialog_info);
                ad.setMessage(message);
                ad.setPositiveButton(strbt1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        Toast.makeText(v.getContext(), "Pago realizado",Toast.LENGTH_LONG).show();
                        double total=0;
                        total=dataBaseUtilities.CalculateTotal(CartFragment.this.getContext(),_cliente.getId_cliente());
                        String email="";
                        email=dataBaseUtilities.GetActiveClient(CartFragment.this.getContext()).getCorreo();
                        ArrayList<OrderPurchase.item> itemsCart=new ArrayList<>();
                        List<Producto.item> listaActual=dataBaseUtilities.ListIdsFromActiveUser(CartFragment.this.getContext(),_cliente.getId_cliente());

                        for (int i=0;i<listaActual.size();i++) {

                            int id=Integer.parseInt(listaActual.get(i).getId_product());
                            int cant=listaActual.get(i).getStock();

                            OrderPurchase.item item=new OrderPurchase.item(id,cant);

                            itemsCart.add(item);



                        }

                        OrderPurchase orderPurchase=new OrderPurchase(_cliente.getCorreo(),total,itemsCart);

                        DatabaseAPI databaseAPI=new DatabaseAPI();
                        databaseAPI.FinishShopping(CartFragment.this.getContext(),orderPurchase);

                        dataBaseUtilities.ClearCart(CartFragment.this.getContext(),_cliente.getId_cliente());
                        Toast.makeText(CartFragment.this.getContext(),"Pago exitoso se ha borrado el carrito",Toast.LENGTH_SHORT).show();

                        List<Producto.item> lista=dataBaseUtilities.ListIdsFromActiveUser(CartFragment.this.getContext(),_cliente.getId_cliente());






                        CustomAdapterItemCart adapter = new CustomAdapterItemCart(CartFragment.this.getContext(), lista);
                        listviewCart.setAdapter(adapter);
                        total_cart.setText("");


                    }
                });
                ad.setNegativeButton(strbt2, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        Toast.makeText(v.getContext(), "Pago cancelado",Toast.LENGTH_LONG).show();
                    }
                }).setIcon(android.R.drawable.ic_menu_help);

                ad.show();


                //Se envia la informacion a la API
/*

   */

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }



}