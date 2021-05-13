package sv.edu.udb.iwfashionapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SqlLiteOpenHelperAdmin extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "iwfashion_database";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_CLIENTES = "clientes";
    private static final String TABLE_CARRITO = "carrito";
    private static final String TABLE_PRODUCTOS = "productos";
    // Columnas tabla clientes
    private static final String KEY_CLIENTE_ID = "id_cliente";
    private static final String KEY_CLIENTE_CODE = "code_cliente";
    private static final String KEY_CLIENTE_NOMBRES = "nombres";
    private static final String KEY_CLIENTE_APELLIDOS = "apellidos";
    private static final String KEY_CLIENTE_CORREO = "correo";
    private static final String KEY_CLIENTE_ESTADO = "estado";

    // Columnas tabla carrito
    private static final String KEY_CARRITO_ID = "id_item";
    private static final String KEY_CARRITO_ID_CLIENTE = "id_cliente";
    private static final String KEY_CARRITO_ID_PRODUCTO = "id_producto";
    private static final String KEY_CARRITO_CANTIDAD = "cantidad";


    //Columnas tabla productos

    private static final String KEY_PRODUCTO_ID = "id_producto";
    private static final String KEY_PRODUCTO_NOMBRE = "nombre_producto";
    private static final String KEY_PRODUCTO_URL_IMG = "url_img";
    private static final String KEY_PRODUCTO_PRECIO = "precio";



    // Columnas tabla carrito


    public SqlLiteOpenHelperAdmin(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_CLIENTES_TABLE = "CREATE TABLE " + TABLE_CLIENTES +
                "(" +
                KEY_CLIENTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + // Define a primary key
                KEY_CLIENTE_CODE + " TEXT,"+
                KEY_CLIENTE_NOMBRES + " TEXT,"+
                KEY_CLIENTE_APELLIDOS +" TEXT,"+
                KEY_CLIENTE_CORREO +" TEXT,"+
                KEY_CLIENTE_ESTADO+" INTEGER"+
                ")";
        String CREATE_PRODUCTOS_TABLE = "CREATE TABLE " + TABLE_PRODUCTOS +
                "(" +
                KEY_PRODUCTO_ID + " INTEGER PRIMARY KEY ," + // Define a primary key
                KEY_PRODUCTO_NOMBRE + " TEXT,"+
                KEY_PRODUCTO_URL_IMG +" TEXT,"+
                KEY_PRODUCTO_PRECIO +" REAL"+
                ")";
        String CREATE_CARRITO_TABLE = "CREATE TABLE " + TABLE_CARRITO +
                "(" +
                KEY_CARRITO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + // Define a primary key
                KEY_CARRITO_ID_CLIENTE + " INTEGER,"+
                KEY_CARRITO_ID_PRODUCTO +" INTEGER,"+
                KEY_CARRITO_CANTIDAD +" INTEGER,"+
                "FOREIGN KEY("+KEY_CARRITO_ID_CLIENTE+") references "+TABLE_CLIENTES+""+"("+KEY_CLIENTE_ID+"),"+
                "FOREIGN KEY("+KEY_CARRITO_ID_PRODUCTO+") references "+TABLE_PRODUCTOS+""+"("+KEY_PRODUCTO_ID+")"+
                ")";

        db.execSQL(CREATE_CLIENTES_TABLE);
        db.execSQL(CREATE_PRODUCTOS_TABLE);
        db.execSQL(CREATE_CARRITO_TABLE);

        // Ese producto se ingresa aca porque el url_img no se logra escapar ya que lleva un comilla simple
        final String insert_Product_1="INSERT INTO productos VALUES(2,'Camiseta deportiva','https://images.puma.com/image/upload/f_auto,q_auto,b_rgb:fafafa,w_1500,h_1500/global/757279/03/fnd/PNA/fmt/png/AC-Milan-Men"+"\'"+"'s-Third-Replica-Jersey',25.0)";
        db.execSQL(insert_Product_1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARRITO);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTES);

            onCreate(db);
        }
    }
}
