package com.arya.consumerapp;

import static com.arya.consumerapp.db.DatabaseContract.FavoriteUserColumns.CONTENT_URI;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.arya.consumerapp.adapter.UserAdapter;
import com.arya.consumerapp.databinding.ActivityMainBinding;
import com.arya.consumerapp.db.MappingHelper;
import com.arya.consumerapp.entity.User;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private UserAdapter adapter;
    private ArrayList<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.progressBar.setVisibility(View.VISIBLE);

        adapter = new UserAdapter(users, user -> {});
        LinearLayoutManager manager = new LinearLayoutManager(this);
        getUsers();

        binding.rvUser.setAdapter(adapter);
        binding.rvUser.setLayoutManager(manager);


    }

    @Override
    protected void onResume() {
        super.onResume();
        getUsers();
    }

    private void getUsers() {
        Cursor results = getContentResolver().query(CONTENT_URI, null, null, null, null);
        users = MappingHelper.mapCursorToArrayList(results);
        adapter.setUsers(users);
        adapter.notifyItemChanged(users.size());

        binding.progressBar.setVisibility(View.GONE);
        checkResult();
    }
    private void checkResult() {
        if (users.size() == 0) {
            binding.text.setVisibility(View.VISIBLE);
        } else {
            binding.text.setVisibility(View.GONE);
        }
    }
}