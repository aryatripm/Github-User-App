package com.arya.githubuserapp.api;

import com.arya.githubuserapp.entity.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GithubAPI {

    @GET("search/users")
    Call<User> getSearchUser(
            @Query("q") String name
    );

    @GET("users/{username}")
    Call<User> getUserDetail(
            @Path("username") String username
    );

    @GET("users/{username}/followers")
    Call<List<User>> getUserFollowers(
            @Path("username") String username
    );

    @GET("users/{username}/following")
    Call<List<User>> getUsersFollowing(
            @Path("username") String username
    );


}
