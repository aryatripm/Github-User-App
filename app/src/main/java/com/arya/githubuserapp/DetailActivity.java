package com.arya.githubuserapp;


import static com.arya.githubuserapp.db.DatabaseContract.FavoriteUserColumns.CONTENT_URI;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import com.arya.githubuserapp.adapter.PagerAdapter;
import com.arya.githubuserapp.api.ApiClient;
import com.arya.githubuserapp.api.GithubAPI;
import com.arya.githubuserapp.databinding.ActivityDetailBinding;
import com.arya.githubuserapp.entity.User;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayoutMediator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    private User user;
    private GithubAPI api;
//    private UserHelper userHelper = UserHelper.getInstance(this);
    private Uri uriWithLogin;

    private Boolean statusFav = false;

    @StringRes
    private final int[] TAB_TITLES = new int[]{
            R.string.tab_text_1,
            R.string.tab_text_2
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String user_login = getIntent().getStringExtra(User.EXTRA_USER);

        if (user_login != null) {
            uriWithLogin = Uri.parse(CONTENT_URI + "/" + user_login);
        }

        PagerAdapter pagerAdapter = new PagerAdapter(this);
        pagerAdapter.setLogin_user(user_login);
        binding.viewPager.setAdapter(pagerAdapter);
        new TabLayoutMediator(binding.tabs, binding.viewPager,
                (tab, position) -> tab.setText(getResources().getString(TAB_TITLES[position]))
        ).attach();

        if(getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.progressBar2.setVisibility(View.VISIBLE);
        api = ApiClient.getClient().create(GithubAPI.class);

        Cursor result = getContentResolver().query(uriWithLogin, null, null, null, null);
        if (result.getCount() > 0) { statusFav = true; }
        checkFavorite(statusFav);

        binding.fabFavorite.setOnClickListener(view -> {
            statusFav = !statusFav;

            if (statusFav) {
                ContentValues values = new ContentValues();
                values.put("login", user.getLogin());
                values.put("name", user.getName());
                values.put("company", user.getCompany());
                values.put("location", user.getLocation());
                values.put("avatar_url", user.getAvatar_url());
                values.put("followers", user.getFollowers());
                values.put("following", user.getFollowing());
                values.put("public_repos", user.getPublic_repos());
                getContentResolver().insert(CONTENT_URI, values);
                showSnackbarMessage("Added to Favorites!");
            } else {
                getContentResolver().delete(uriWithLogin, null, null);
                showSnackbarMessage("Deleted from favorites!");
            }

            checkFavorite(statusFav);
        });

        fetchDetailUser(user_login);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void fetchDetailUser (String login) {
        callDetailUser(login).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                user = response.body();
                binding.progressBar2.setVisibility(View.INVISIBLE);
                setDataUser(user);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(DetailActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Call<User> callDetailUser(String login) {
        return api.getUserDetail(login);
    }

    private void setDataUser(User user) {
        Glide.with(this).load(user.getAvatar_url()).into(binding.userImg);
        binding.userName.setText(user.getName());
        binding.userUsername.setText(user.getLogin());
        String profile = null;
        if (user.getCompany() == null && user.getLocation() == null) {
            profile = "";
        } else if (user.getLocation() == null) {
            profile = user.getCompany();
        } else if (user.getCompany() == null) {
            profile = user.getLocation();
        } else {
            profile = user.getCompany() + " " + user.getLocation();
        }
        binding.userProfile.setText(profile);
        binding.userStats.setText(new StringBuilder("Followers : " + user.getFollowers() + " | Following : " + user.getFollowing() + " | Repository : " + user.getPublic_repos()));
    }

    private void checkFavorite(boolean statusFav) {
        if (statusFav) { binding.fabFavorite.setImageResource(R.drawable.ic_baseline_favorite_24); }
        else { binding.fabFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24); }
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(findViewById(R.id.coordinator), message, Snackbar.LENGTH_SHORT)
                .setAction(R.string.show, view -> {
                    Intent intent = new Intent(this, FavoriteActivity.class);
                    startActivity(intent);
                })
                .show();
    }
}