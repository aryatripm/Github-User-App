package com.arya.githubuserapp.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.arya.githubuserapp.adapter.UserAdapter;
import com.arya.githubuserapp.api.ApiClient;
import com.arya.githubuserapp.api.GithubAPI;
import com.arya.githubuserapp.databinding.FragmentFollowersBinding;
import com.arya.githubuserapp.entity.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FollowersFragment extends Fragment {

    private static FollowersFragment followersFragment;
    private FragmentFollowersBinding binding;
    private UserAdapter adapter;
    private static final String STATE_USER = "state_user";

    private final ArrayList<User> followers = new ArrayList<>();

    private GithubAPI api;

    public FollowersFragment() {
        // Required empty public constructor
    }
    public static FollowersFragment newInstance(String login) {
        if (followersFragment == null){
            followersFragment = new FollowersFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString(User.EXTRA_USER, login);
        followersFragment.setArguments(bundle);
        return followersFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFollowersBinding.inflate(inflater, container, false);
        api = ApiClient.getClient().create(GithubAPI.class);
        return binding.getRoot();
    }

    @Override
    public void onStop() {
        super.onStop();
        followers.removeAll(followers);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.progressBar3.setVisibility(View.VISIBLE);

        adapter = new UserAdapter(followers, this::toDetailedUser);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        binding.rvFollowers.setAdapter(adapter);
        binding.rvFollowers.setLayoutManager(manager);

        String login = null;
        if (getArguments() != null) {
            login = getArguments().getString(User.EXTRA_USER);
        }

        fetchUser(login);
    }

    private void fetchUser(String username) {
        callFollowers(username).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.body() != null) {
                    followers.addAll(response.body());
                    adapter.notifyItemChanged(followers.size());
                    binding.progressBar3.setVisibility(View.INVISIBLE);
                    checkResult();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d("FOLLOWERS", t.getLocalizedMessage());
            }
        });
    }

    private void toDetailedUser(User user) {
        Toast.makeText(getActivity(), user.toString(), Toast.LENGTH_SHORT).show();
    }

    private Call<List<User>> callFollowers(String username) {
        return api.getUserFollowers(username);
    }

    private void checkResult() {
        if (followers.size() == 0) {
            binding.text1.setVisibility(View.VISIBLE);
        } else {
            binding.text1.setVisibility(View.INVISIBLE);
        }
    }
}