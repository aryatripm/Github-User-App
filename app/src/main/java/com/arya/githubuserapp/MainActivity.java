package com.arya.githubuserapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.arya.githubuserapp.adapter.UserAdapter;
import com.arya.githubuserapp.api.ApiClient;
import com.arya.githubuserapp.api.GithubAPI;
import com.arya.githubuserapp.databinding.ActivityMainBinding;
import com.arya.githubuserapp.entity.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private final ArrayList<User> users = new ArrayList<>();
    private static final String STATE_QUERY = "state_query";

    private UserAdapter adapter;

    private GithubAPI api;
    private String querySearch = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new UserAdapter(users, this::toDetailedUser);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        api = ApiClient.getClient().create(GithubAPI.class);

        binding.rvUser.setAdapter(adapter);
        binding.rvUser.setLayoutManager(manager);

        binding.progressBar.setVisibility(View.VISIBLE);

        if (savedInstanceState != null) {
            querySearch = savedInstanceState.getString(STATE_QUERY);
        }

        fetchUser();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_QUERY, querySearch);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_menu, menu);


        SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
        searchView.setQueryHint("Search...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                binding.text.setVisibility(View.GONE);
                binding.progressBar.setVisibility(View.VISIBLE);
                querySearch = query;
                searchView.clearFocus();
                users.removeAll(users);
                fetchUser();
                adapter.notifyDataSetChanged();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.favorite) {
            Intent intent = new Intent(this, FavoriteActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchUser() {
        callSearchUsers().enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body() != null) {
                    users.addAll(response.body().getItems());
                    adapter.notifyItemChanged(users.size());
                }
                binding.progressBar.setVisibility(View.INVISIBLE);
                checkResult();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void toDetailedUser(User user) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(User.EXTRA_USER, user.getLogin());
        startActivity(intent);
    }

    private Call<User> callSearchUsers() {
        return api.getSearchUser(querySearch);
    }

    private void checkResult() {
        if (users.size() == 0) {
            binding.text.setVisibility(View.VISIBLE);
        } else {
            binding.text.setVisibility(View.GONE);
        }
    }
}