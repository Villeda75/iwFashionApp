package sv.edu.udb.iwfashionapp.interfaces;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import sv.edu.udb.iwfashionapp.models.CustomDesign;


public interface CustomDesignAPI {

    @GET("customDesigns")
    Call<CustomDesign> getCustomDesigns();

 @POST("/CustomDesigns/newCD.php")
    Call<CustomDesign.item> InsertCustomDesign(@Body CustomDesign.item customDesign);


 @POST("/CustomDesigns/deleteCD.php/{id}")
    Call<CustomDesign.item> DeleteCustomDesign(@Path("id") String id);
}
