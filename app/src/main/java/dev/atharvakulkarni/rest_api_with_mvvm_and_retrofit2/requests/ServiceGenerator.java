package dev.atharvakulkarni.rest_api_with_mvvm_and_retrofit2.requests;

import dev.atharvakulkarni.rest_api_with_mvvm_and_retrofit2.util.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Modeled after @brittbarak's example on Github
 * https://github.com/brittBarak/NetworkingDemo
 * https://twitter.com/brittbarak
 */
public class ServiceGenerator
{
    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static RecipeApi recipeApi = retrofit.create(RecipeApi.class);

    public static RecipeApi getRecipeApi()
    {
        return recipeApi;
    }
}