package com.arya.githubuserapp;

import static com.arya.githubuserapp.db.DatabaseContract.FavoriteUserColumns.CONTENT_URI;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.arya.githubuserapp.adapter.UserAdapter;
import com.arya.githubuserapp.databinding.ActivityFavoriteBinding;
import com.arya.githubuserapp.db.MappingHelper;
import com.arya.githubuserapp.entity.User;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {

    private ActivityFavoriteBinding binding;
    private UserAdapter adapter;
    private ArrayList<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavoriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if(getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.progressBar.setVisibility(View.VISIBLE);

        adapter = new UserAdapter(users, this::toDetailedUser);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        binding.rvUser.setAdapter(adapter);
        binding.rvUser.setLayoutManager(manager);

        fetchFavoriteUsers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchFavoriteUsers();
    }

    private void fetchFavoriteUsers() {

        Cursor results = getContentResolver().query(CONTENT_URI, null, null, null, null);
        users = MappingHelper.mapCursorToArrayList(results);
        adapter.setUsers(users);
        adapter.notifyItemChanged(users.size());

        binding.progressBar.setVisibility(View.GONE);
        checkResult();
    }

    private void toDetailedUser(User user) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(User.EXTRA_USER, user.getLogin());
        startActivity(intent);
    }

    private void checkResult() {
        if (users.size() == 0) {
            binding.text.setVisibility(View.VISIBLE);
        } else {
            binding.text.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}