package dev.atharvakulkarni.rest_api_with_mvvm_and_retorfit2.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import dev.atharvakulkarni.rest_api_with_mvvm_and_retrofit2.models.Recipe;
import dev.atharvakulkarni.rest_api_with_mvvm_and_retrofit2.repositories.RecipeRepository;

public class RecipeViewModel extends ViewModel
{
    private RecipeRepository mRecipeRepository;
    private String mRecipeId;
    private boolean mDidRetrieveRecipe;

    public RecipeViewModel()
    {
        mRecipeRepository = RecipeRepository.getInstance();
        mDidRetrieveRecipe = false;
    }

    public LiveData<Recipe> getRecipe()
    {
        return mRecipeRepository.getRecipe();
    }

    public LiveData<Boolean> isRecipeRequestTimedOut()
    {
        return mRecipeRepository.isRecipeRequestTimedOut();
    }

    public void searchRecipeById(String recipeId)
    {
        mRecipeId = recipeId;
        mRecipeRepository.searchRecipeById(recipeId);
    }

    public String getRecipeId()
    {
        return mRecipeId;
    }

    public void setRetrievedRecipe(boolean retrievedRecipe)
    {
        mDidRetrieveRecipe = retrievedRecipe;
    }

    public boolean didRetrieveRecipe()
    {
        return mDidRetrieveRecipe;
    }
}