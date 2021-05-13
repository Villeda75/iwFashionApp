package sv.edu.udb.iwfashionapp.interfaces;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import sv.edu.udb.iwfashionapp.models.User;

public interface UserAPI {

    @POST("Users/register.php")
    Call<User> RegisterUser(@Body User user);


}
