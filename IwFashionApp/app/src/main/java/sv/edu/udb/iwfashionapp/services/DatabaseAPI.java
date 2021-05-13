package sv.edu.udb.iwfashionapp.services;
import android.content.Context;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import sv.edu.udb.iwfashionapp.interfaces.OrderPurchaseAPI;
import sv.edu.udb.iwfashionapp.models.OrderPurchase;
import sv.edu.udb.iwfashionapp.models.User;
import sv.edu.udb.iwfashionapp.interfaces.UserAPI;

public class DatabaseAPI {

    public void RegisterUser(Context context, String _name, String _email)
    {


        User _user=new User(_name,_email);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://veterinarialissette-vc170991-aa170621.000webhostapp.com/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserAPI userAPI=retrofit.create(UserAPI.class);
        Call<User> call= userAPI.RegisterUser(_user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if(response.isSuccessful()) {


                  Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                  /*
                    Toast.makeText(context, "Codigo: " + response.code(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, "headers: " + response.headers().toString(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, "body: " + response.body(), Toast.LENGTH_SHORT).show();
*/
                }
                else
                {
                    Toast.makeText(context,"No funciona",Toast.LENGTH_LONG);
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }


    public void FinishShopping(Context context, OrderPurchase _orderPurchase) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://veterinarialissette-vc170991-aa170621.000webhostapp.com/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrderPurchaseAPI orderPurchaseAPI=retrofit.create(OrderPurchaseAPI.class);
        Call<OrderPurchase> call= orderPurchaseAPI.SendOrderPurchase(_orderPurchase);

        call.enqueue(new Callback<OrderPurchase>() {
            @Override
            public void onResponse(Call<OrderPurchase> call, Response<OrderPurchase> response) {

                if(response.isSuccessful()) {


                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                   /* Toast.makeText(context, "Codigo: " + response.code(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, "headers: " + response.headers().toString(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, "body: " + response.body(), Toast.LENGTH_SHORT).show();
*/
                }
                else
                {
                    Toast.makeText(context,"No funciona",Toast.LENGTH_LONG);
                }

            }

            @Override
            public void onFailure(Call<OrderPurchase> call, Throwable t) {

            }
        });
    }

    }
