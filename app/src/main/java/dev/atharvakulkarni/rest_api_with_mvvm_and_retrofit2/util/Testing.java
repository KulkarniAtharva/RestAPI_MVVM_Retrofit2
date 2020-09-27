package dev.atharvakulkarni.rest_api_with_mvvm_and_retrofit2.util;

import android.util.Log;



import java.util.List;

import dev.atharvakulkarni.rest_api_with_mvvm_and_retrofit2.models.Recipe;

public class Testing {

    public static void printRecipes(List<Recipe>list, String tag){
        for(Recipe recipe: list){
            Log.d(tag, "onChanged: " + recipe.getTitle());
        }
    }
}
