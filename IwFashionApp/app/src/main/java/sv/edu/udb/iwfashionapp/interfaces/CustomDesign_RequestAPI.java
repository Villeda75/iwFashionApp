package sv.edu.udb.iwfashionapp.interfaces;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import sv.edu.udb.iwfashionapp.models.CustomDesign_Request;

public interface CustomDesign_RequestAPI {


    @POST("phpMailer/sendEmailDesignCustom.php")
    Call<CustomDesign_Request> InsertCustomDesign_Request(@Body CustomDesign_Request customDesign_request);


}
