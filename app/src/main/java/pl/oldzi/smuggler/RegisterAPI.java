package pl.oldzi.smuggler;
import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface RegisterAPI {
    @FormUrlEncoded
    @POST("/volleyRegister.php")
    void insertUser(
            @Field("item_id") String item_id,
            @Field("codename") String codename,
            @Field("name") String name,
            @Field("quantity") int quantity,
            Callback<Response> callback);
}