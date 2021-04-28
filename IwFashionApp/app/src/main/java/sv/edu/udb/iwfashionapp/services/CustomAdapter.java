package sv.edu.udb.iwfashionapp.services;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import sv.edu.udb.iwfashionapp.CustomDesignsFragment;
import sv.edu.udb.iwfashionapp.DetalleProductoActivity;
import sv.edu.udb.iwfashionapp.R;
import sv.edu.udb.iwfashionapp.models.Producto;

public class CustomAdapter extends BaseAdapter {
    Context context;
    List<Producto.item> list;
    public CustomAdapter(Context context, java.util.List<Producto.item> list) {
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
        ImageView imageProducto;
        TextView TxtNombre;
        TextView TxtPrecioNormal;
        TextView TxtPrecioDiscount;
        Button btn_ver_mas1;

        Producto.item item=list.get(i);

        if(view==null)
            view= LayoutInflater.from(context).inflate(R.layout.item_list_view,null);

       imageProducto=view.findViewById(R.id.imageViewCdesign);
        TxtNombre=view.findViewById(R.id.nombre_Cdesign);
        TxtPrecioNormal=view.findViewById(R.id.precio_product_normal);
        TxtPrecioDiscount=view.findViewById(R.id.precio_product_discount);
        btn_ver_mas1=view.findViewById(R.id.btn_reservarCdesign);
        Glide.with(view)
                .load(item.getUrl_img().toString())
                .into(imageProducto);

        TxtNombre.setText(item.getProduct_type());
        TxtPrecioNormal.setText( "$ " +  String.valueOf(item.getSales_price()));
        TxtPrecioDiscount.setPaintFlags(TxtPrecioDiscount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        TxtPrecioDiscount.setText( "$ " +  String.valueOf(item.getDiscount_price()));


        btn_ver_mas1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent detalle_producto=new Intent(context, DetalleProductoActivity.class);
                detalle_producto.putExtra("id_product",item.getId_product());
                detalle_producto.putExtra("slug",item.getProduct_type());
                detalle_producto.putExtra("stock",item.getStock());
                detalle_producto.putExtra("visible",item.getVisible());
                detalle_producto.putExtra("color",item.getColor());
                detalle_producto.putExtra("size",item.getSize());
                detalle_producto.putExtra("gender",item.getGender());
                detalle_producto.putExtra("brand",item.getBrand());
                detalle_producto.putExtra("product_type",item.getProduct_type());
                detalle_producto.putExtra("description",item.getDescription());
                detalle_producto.putExtra("sales_price",item.getSales_price());
                detalle_producto.putExtra("discount_price",item.getDiscount_price());
                detalle_producto.putExtra("sub_category",item.getSub_category());
                detalle_producto.putExtra("url_img",item.getUrl_img());

                context.startActivity(detalle_producto);
            }
        });
        return view;
    }
}
