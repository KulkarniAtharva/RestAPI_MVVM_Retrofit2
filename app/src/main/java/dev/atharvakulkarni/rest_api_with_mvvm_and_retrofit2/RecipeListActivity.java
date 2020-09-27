package dev.atharvakulkarni.rest_api_with_mvvm_and_retrofit2;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.atharvakulkarni.rest_api_with_mvvm_and_retrofit2.adapters.OnRecipeListener;
import dev.atharvakulkarni.rest_api_with_mvvm_and_retrofit2.adapters.RecipeRecyclerAdapter;
import dev.atharvakulkarni.rest_api_with_mvvm_and_retrofit2.models.Recipe;
import dev.atharvakulkarni.rest_api_with_mvvm_and_retrofit2.util.Testing;
import dev.atharvakulkarni.rest_api_with_mvvm_and_retrofit2.util.VerticalSpacingItemDecorator;
import dev.atharvakulkarni.rest_api_with_mvvm_and_retorfit2.viewmodels.RecipeListViewModel;

public class RecipeListActivity extends BaseActivity implements OnRecipeListener
{
    private static final String TAG = "RecipeListActivity";

    private RecipeListViewModel mRecipeListViewModel;
    private RecyclerView mRecyclerView;
    private RecipeRecyclerAdapter mAdapter;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        mRecyclerView = findViewById(R.id.recipe_list);
        mSearchView = findViewById(R.id.search_view);

        mRecipeListViewModel = ViewModelProviders.of(this).get(RecipeListViewModel.class);

        initRecyclerView();
        subscribeObservers();
        initSearchView();
        if(!mRecipeListViewModel.isViewingRecipes())
            // display search categories
            displaySearchCategories();

        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
    }

    private void subscribeObservers()
    {
        mRecipeListViewModel.getRecipes().observe(this, new Observer<List<Recipe>>()
        {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes)
            {
                if(recipes != null)
                {
                    if(mRecipeListViewModel.isViewingRecipes())
                    {
                        Testing.printRecipes(recipes, "recipes test");
                        mRecipeListViewModel.setIsPerformingQuery(false);
                        mAdapter.setRecipes(recipes);
                    }
                }
            }
        });

        mRecipeListViewModel.isQueryExhausted().observe(this, new Observer<Boolean>()
        {
            @Override
            public void onChanged(@Nullable Boolean aBoolean)
            {
                Log.d(TAG, "onChanged: the query is exhausted..." + aBoolean);
                if(aBoolean)
                    mAdapter.setQueryExhausted();
            }
        });
    }

    private void initRecyclerView()
    {
        mAdapter = new RecipeRecyclerAdapter(this);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(30);
        mRecyclerView.addItemDecoration(itemDecorator);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState)
            {
                if(!mRecyclerView.canScrollVertically(1))
                    // search the next page
                    mRecipeListViewModel.searchNextPage();
            }
        });
    }

    private void initSearchView()
    {
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String s)
            {
                mAdapter.displayLoading();
                mRecipeListViewModel.searchRecipesApi(s, 1);
                mSearchView.clearFocus();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s)
            {
                return false;
            }
        });
    }

    @Override
    public void onRecipeClick(int position)
    {
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra("recipe", mAdapter.getSelectedRecipe(position));
        startActivity(intent);
    }

    @Override
    public void onCategoryClick(String category)
    {
        mAdapter.displayLoading();
        mRecipeListViewModel.searchRecipesApi(category, 1);
        mSearchView.clearFocus();
    }

    private void displaySearchCategories()
    {
        mRecipeListViewModel.setIsViewingRecipes(false);
        mAdapter.displaySearchCategories();
    }

    @Override
    public void onBackPressed()
    {
        if(mRecipeListViewModel.onBackPressed())
            super.onBackPressed();
        else
            displaySearchCategories();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == R.id.action_categories)
            displaySearchCategories();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.recipe_search_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}