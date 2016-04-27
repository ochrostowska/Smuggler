package pl.oldzi.smuggler.database;


import java.util.List;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import pl.oldzi.smuggler.Item;

public interface ItemsAPI {

    @GET("/listItems.php")
    void getItems(Callback<List<Item>> response);

    @FormUrlEncoded
    @POST("/volleyRegister.php")
    void insertUser(
            @Field("item_id") int item_id,
            @Field("codename") String codename,
            @Field("name") String name,
            @Field("quantity") int quantity,
            Callback<Response> callback);

}