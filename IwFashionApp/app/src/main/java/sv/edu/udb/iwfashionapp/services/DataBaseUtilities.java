package sv.edu.udb.iwfashionapp.services;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import sv.edu.udb.iwfashionapp.SqlLiteOpenHelperAdmin;
import sv.edu.udb.iwfashionapp.models.Cliente;
import sv.edu.udb.iwfashionapp.models.Producto;

public class DataBaseUtilities {


    public Cliente GetActiveClient(Context context)
    {



        SqlLiteOpenHelperAdmin admin = new SqlLiteOpenHelperAdmin(context,"iwfashion_database",null,1);

        SQLiteDatabase database = admin.getReadableDatabase();

        //Se busca por codigo unico el cliente
        Cursor fila0 = database.rawQuery("SELECT id_cliente,code_cliente,nombres,apellidos,correo FROM clientes WHERE estado=1",null);


        //si existe se lee el valor que devuelve el select
        if(fila0.moveToFirst())
        {

            Cliente _cliente=new Cliente(fila0.getInt(0),fila0.getString(1),fila0.getString(2),fila0.getString(3),fila0.getString(4));
            return  _cliente;
        }
        else
        {
            return null;
        }


    }

    public void InsertProduct(Context context,int id_producto,String nombre,String descripcion,String url_img,Double precio)
    {
        int _id_prodcuto_exist=0;
        SqlLiteOpenHelperAdmin admin = new SqlLiteOpenHelperAdmin(context,"iwfashion_database",null,1);
        SQLiteDatabase database = admin.getReadableDatabase();
        Cursor fila0 = database.rawQuery("SELECT id_producto FROM productos WHERE id_producto="+id_producto+"",null);


        //si existe se lee el valor que devuelve el select
        if(fila0.moveToFirst())
        {
            _id_prodcuto_exist=fila0.getInt(0);
        }

        if(_id_prodcuto_exist==0)
        {
            CharSequence char_url=url_img;

            final String Add_producto="INSERT INTO productos VALUES("+id_producto+",'"+nombre+"','"+char_url+"',"+precio+")";
            database.execSQL(Add_producto);
        }
    }

    public void AddToCart(Context context,int id_cliente,int id_product)
    {

        int cantidad=0,id_item=0;
        SqlLiteOpenHelperAdmin admin = new SqlLiteOpenHelperAdmin(context,"iwfashion_database",null,1);

        SQLiteDatabase database = admin.getReadableDatabase();
        Cursor fila0 = database.rawQuery("SELECT cantidad,id_item FROM carrito WHERE id_cliente="+id_cliente+" AND id_producto="+id_product+"",null);


        //si existe se lee el valor que devuelve el select
        if(fila0.moveToFirst())
        {
            cantidad = fila0.getInt(0);
            id_item=fila0.getInt(1);
        }


        if(cantidad==0)
        {
            int lastID=0;
            Cursor fila1 = database.rawQuery("SELECT MAX(id_item) FROM carrito ",null);
            if(fila1.moveToFirst())
            {
                lastID = fila1.getInt(0);
            }


            final String Add_item="INSERT INTO carrito VALUES("+String.valueOf(lastID+1)+","+id_cliente+","+id_product+","+1+")";
            database.execSQL(Add_item);
        }
        else
        {
            if(id_item>0)
            {
                int Newcantidad=cantidad+1;
                final String Add_item="UPDATE carrito SET cantidad="+Newcantidad+" WHERE id_item="+id_item+"";
                database.execSQL(Add_item);
            }
        }

    }


    public int GetQuantityByItem(Context context,int id_cliente,int id_producto)
    {
        int cantidad=0;
        SqlLiteOpenHelperAdmin admin = new SqlLiteOpenHelperAdmin(context,"iwfashion_database",null,1);

        SQLiteDatabase database = admin.getReadableDatabase();
        Cursor fila0 = database.rawQuery("SELECT cantidad FROM carrito WHERE id_cliente="+id_cliente+" AND id_producto="+id_producto+"",null);


        //si existe se lee el valor que devuelve el select
        if(fila0.moveToFirst())
        {
            cantidad = fila0.getInt(0);
            return cantidad;
        }
        else
        {
            return 0;
        }

    }

    public List<Producto.item> ListIdsFromActiveUser(Context context, int id_cliente)
    {
        final ArrayList<Producto.item> lista=new ArrayList<Producto.item>();
        SqlLiteOpenHelperAdmin admin = new SqlLiteOpenHelperAdmin(context,"iwfashion_database",null,1);

        SQLiteDatabase database = admin.getReadableDatabase();
        Cursor fila0 = database.rawQuery("SELECT C.id_producto,nombre_producto,url_img,precio,cantidad FROM carrito AS C INNER JOIN productos AS P ON C.id_producto=P.id_producto WHERE id_cliente="+id_cliente+"",null);


        //si existe se lee el valor que devuelve el select
        while(fila0.moveToNext())
        {
            Producto.item _producto=new Producto.item();
            _producto.setId_product(String.valueOf(fila0.getInt(0)));
            _producto.setSlug(fila0.getString(1));
            _producto.setUrl_img(fila0.getString(2));
            _producto.setSales_price(fila0.getDouble(3));
            _producto.setStock(fila0.getInt(4));
         lista.add(_producto);
        }

        return lista;
    }


    public double CalculateTotal(Context context,int id_cliente)
    {

        double totalCarrito=0;
        SqlLiteOpenHelperAdmin admin = new SqlLiteOpenHelperAdmin(context,"iwfashion_database",null,1);


        SQLiteDatabase database = admin.getReadableDatabase();
        Cursor fila0 = database.rawQuery("SELECT cantidad,P.precio FROM carrito AS C INNER JOIN productos AS P ON C.id_producto=P.id_producto WHERE id_cliente="+id_cliente+"",null);


        //si existe se lee el valor que devuelve el select
        while(fila0.moveToNext())
        {
        double cantidad=0;
           cantidad=fila0.getDouble(0);
           double precio=0;
           precio=fila0.getDouble(1);
           double total=0;
           total=cantidad*precio;

           totalCarrito=totalCarrito+total;


            System.out.println("Cantidad: "+fila0.getInt(0));
            System.out.println("Precio: "+fila0.getFloat(1));

        }

        return totalCarrito;
    }

    public void VerifyUser(Context context,Cliente cliente)
    {

        int clienteExists=0,lastID=0,id_cliente_global=0;
        SqlLiteOpenHelperAdmin admin = new SqlLiteOpenHelperAdmin(context,"iwfashion_database",null,1);

        SQLiteDatabase database = admin.getReadableDatabase();

        //Se busca por codigo unico el cliente
        Cursor fila0 = database.rawQuery("SELECT id_cliente FROM clientes WHERE code_cliente='"+String.valueOf(cliente.getCode_cliente())+"'",null);


        //si existe se lee el valor que devuelve el select
        if(fila0.moveToFirst())
        {
            clienteExists = fila0.getInt(0);
        }

        //si no se encuentra el id entonces se agrega dentro de la base de datos local en el dispositivo
        if(clienteExists==0)
        {

            //se selecciona el id maximo que se encuentre ya que el autoincrementable no sirve xd
            Cursor fila1 = database.rawQuery("SELECT MAX(id_cliente) FROM clientes ",null);
            if(fila1.moveToFirst())
            {
                lastID = fila1.getInt(0);
            }
            //finalmente se inserta el nuevo registro de cliente junto con el id cliente que es un numero y el code cliente que es un string que proviene de firebase
            final String Insert_User="INSERT INTO clientes VALUES("+String.valueOf(lastID+1)+",'"+cliente.getCode_cliente()+"','"+cliente.getNombres()+"','"+cliente.getApellidos()+"','"+cliente.getCorreo()+"',"+1+")";
            database.execSQL(Insert_User);


            //y se se asigna el id_cliente a la variable que se tiene como global

        }
        else
        {
            final String SetActive="UPDATE clientes SET estado=1 WHERE id_cliente="+String.valueOf(clienteExists)+"";
            database.execSQL(SetActive);
        }


    }


    public void LogOutSqlLiteSession(Context context)
    {
        SqlLiteOpenHelperAdmin admin = new SqlLiteOpenHelperAdmin(context,"iwfashion_database",null,1);

        SQLiteDatabase database = admin.getReadableDatabase();


        final String Update_Users="UPDATE clientes SET estado=0";
        database.execSQL(Update_Users);


    }

    public void UpdateQuantity(Context context,int id_cliente,int id_producto,int new_quantity)
    {
        SqlLiteOpenHelperAdmin admin = new SqlLiteOpenHelperAdmin(context,"iwfashion_database",null,1);

        SQLiteDatabase database = admin.getReadableDatabase();


        final String Update_cart_item="UPDATE carrito SET cantidad="+new_quantity+" WHERE id_producto="+id_producto+" AND id_cliente="+id_cliente+"";
        database.execSQL(Update_cart_item);
    }


    public void ClearCart(Context context,int id_cliente)
    {
        SqlLiteOpenHelperAdmin admin = new SqlLiteOpenHelperAdmin(context,"iwfashion_database",null,1);

        SQLiteDatabase database = admin.getReadableDatabase();


        final String Clear_cart="DELETE FROM carrito WHERE id_cliente="+id_cliente+"";
        database.execSQL(Clear_cart);
    }

}
