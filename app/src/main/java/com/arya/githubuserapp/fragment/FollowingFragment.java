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
import com.arya.githubuserapp.databinding.FragmentFollowingBinding;
import com.arya.githubuserapp.entity.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FollowingFragment extends Fragment {

    private static FollowingFragment followingFragment;
    private FragmentFollowingBinding binding;
    private UserAdapter adapter;

    private final ArrayList<User> followings = new ArrayList<>();

    private GithubAPI api;

    public FollowingFragment() {
        // Required empty public constructor
    }
    public static FollowingFragment newInstance(String login) {
        if (followingFragment == null){
            followingFragment = new FollowingFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString(User.EXTRA_USER, login);
        followingFragment.setArguments(bundle);
        return followingFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStop() {
        super.onStop();
        followings.removeAll(followings);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFollowingBinding.inflate(inflater, container, false);
        api = ApiClient.getClient().create(GithubAPI.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.progressBar4.setVisibility(View.VISIBLE);

        adapter = new UserAdapter(followings, this::toDetailedUser);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        binding.rvFollowing.setAdapter(adapter);
        binding.rvFollowing.setLayoutManager(manager);

        String login = null;
        if (getArguments() != null) {
            login = getArguments().getString(User.EXTRA_USER);
        }
        fetchUser(login);
    }

    private void fetchUser(String username) {
        callFollowing(username).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.body() != null) {
                    followings.addAll(response.body());
                    adapter.notifyItemChanged(followings.size());
                    binding.progressBar4.setVisibility(View.INVISIBLE);
                    checkResult();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d("FOLLOWINGS", t.getLocalizedMessage());
            }
        });
    }

    private void toDetailedUser(User user) {
        Toast.makeText(getActivity(), user.toString(), Toast.LENGTH_SHORT).show();
    }

    private Call<List<User>> callFollowing(String username) {
        return api.getUsersFollowing(username);
    }

    private void checkResult() {
        if (followings.size() == 0) {
            binding.text2.setVisibility(View.VISIBLE);
        } else {
            binding.text2.setVisibility(View.GONE);
        }
    }
}