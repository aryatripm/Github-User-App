package com.arya.githubuserapp.adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.arya.githubuserapp.fragment.FollowersFragment;
import com.arya.githubuserapp.fragment.FollowingFragment;

public class PagerAdapter extends FragmentStateAdapter {

    public PagerAdapter(AppCompatActivity activity) {
        super(activity);
    }
    private String login_user;

    public String getLogin_user() {
        return login_user;
    }
    public void setLogin_user(String login_user) {
        this.login_user = login_user;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = FollowersFragment.newInstance(login_user);
                break;
            case 1:
                fragment = FollowingFragment.newInstance(login_user);
                break;
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
