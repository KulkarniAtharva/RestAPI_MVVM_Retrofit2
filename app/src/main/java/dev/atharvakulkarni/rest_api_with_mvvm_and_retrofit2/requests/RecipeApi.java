package dev.atharvakulkarni.rest_api_with_mvvm_and_retrofit2.requests;



import dev.atharvakulkarni.rest_api_with_mvvm_and_retrofit2.requests.responses.RecipeResponse;
import dev.atharvakulkarni.rest_api_with_mvvm_and_retrofit2.requests.responses.RecipeSearchResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecipeApi {

    // SEARCH
    @GET("api/search")
    Call<RecipeSearchResponse> searchRecipe(
            @Query("key") String key,
            @Query("q") String query,
            @Query("page") String page
    );

    // GET RECIPE REQUEST
    @GET("api/get")
    Call<RecipeResponse> getRecipe(
            @Query("key") String key,
            @Query("rId") String recipe_id
    );
}
