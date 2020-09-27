package dev.atharvakulkarni.rest_api_with_mvvm_and_retrofit2.requests.responses;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import dev.atharvakulkarni.rest_api_with_mvvm_and_retrofit2.models.Recipe;

public class RecipeResponse {

    @SerializedName("recipe")
    @Expose()
    private Recipe recipe;

    public Recipe getRecipe(){
        return recipe;
    }

    @Override
    public String toString() {
        return "RecipeResponse{" +
                "recipe=" + recipe +
                '}';
    }
}
