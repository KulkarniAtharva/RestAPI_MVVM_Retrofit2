package dev.atharvakulkarni.rest_api_with_mvvm_and_retrofit2.repositories;


import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;


import java.util.List;

import dev.atharvakulkarni.rest_api_with_mvvm_and_retrofit2.models.Recipe;
import dev.atharvakulkarni.rest_api_with_mvvm_and_retrofit2.requests.RecipeApiClient;

public class RecipeRepository {

    private static RecipeRepository instance;
    private RecipeApiClient mRecipeApiClient;
    private String mQuery;
    private int mPageNumber;
    private MutableLiveData<Boolean> mIsQueryExhausted = new MutableLiveData<>();
    private MediatorLiveData<List<Recipe>> mRecipes = new MediatorLiveData<>();

    public static RecipeRepository getInstance(){
        if(instance == null){
            instance = new RecipeRepository();
        }
        return instance;
    }

    private RecipeRepository(){
        mRecipeApiClient = RecipeApiClient.getInstance();
        initMediators();
    }

    private void initMediators(){
        LiveData<List<Recipe>> recipeListApiSource = mRecipeApiClient.getRecipes();
        mRecipes.addSource(recipeListApiSource, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {

                if(recipes != null){
                    mRecipes.setValue(recipes);
                    doneQuery(recipes);
                }
                else{
                    // search database cache
                    doneQuery(null);
                }
            }
        });
    }

    private void doneQuery(List<Recipe> list){
        if(list != null){
            if (list.size() % 30 != 0) {
                mIsQueryExhausted.setValue(true);
            }
        }
        else{
            mIsQueryExhausted.setValue(true);
        }
    }

    public LiveData<Boolean> isQueryExhausted(){
        return mIsQueryExhausted;
    }

    public LiveData<List<Recipe>> getRecipes(){
        return mRecipes;
    }

    public LiveData<Recipe> getRecipe(){
        return mRecipeApiClient.getRecipe();
    }

    public void searchRecipeById(String recipeId){
        mRecipeApiClient.searchRecipeById(recipeId);
    }


    public void searchRecipesApi(String query, int pageNumber){
        if(pageNumber == 0){
            pageNumber = 1;
        }
        mQuery = query;
        mPageNumber = pageNumber;
        mIsQueryExhausted.setValue(false);
        mRecipeApiClient.searchRecipesApi(query, pageNumber);
    }

    public void searchNextPage(){
        searchRecipesApi(mQuery, mPageNumber + 1);
    }

    public void cancelRequest(){
        mRecipeApiClient.cancelRequest();
    }

    public LiveData<Boolean> isRecipeRequestTimedOut(){
        return mRecipeApiClient.isRecipeRequestTimedOut();
    }
}




