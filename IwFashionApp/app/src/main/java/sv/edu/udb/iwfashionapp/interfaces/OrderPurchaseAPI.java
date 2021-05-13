package sv.edu.udb.iwfashionapp.interfaces;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import sv.edu.udb.iwfashionapp.models.OrderPurchase;


public interface OrderPurchaseAPI {

    @POST("Users/register.php")
    Call<OrderPurchase> SendOrderPurchase(@Body OrderPurchase orderPurchase);

}
