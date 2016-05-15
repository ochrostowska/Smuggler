package pl.oldzi.smuggler.database;


import java.util.List;

import okhttp3.ResponseBody;
import pl.oldzi.smuggler.Item;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ItemsAPI {

    @GET("/listItems.php")
    Call<List<Item>> getItems();

    @FormUrlEncoded
    @POST("/volleyRegister.php")
    Call<ResponseBody> insertUser(
            @Field("item_id") int item_id,
            @Field("codename") String codename,
            @Field("name") String name,
            @Field("quantity") int quantity);

}