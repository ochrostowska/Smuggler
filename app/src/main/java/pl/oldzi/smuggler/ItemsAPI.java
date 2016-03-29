package pl.oldzi.smuggler;


import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

public interface ItemsAPI {

    /*Retrofit get annotation with our URL
       And our method that will return us the list ob Book
    */
    @GET("/listItems.php")
    void getItems(Callback<List<Item>> response);
}