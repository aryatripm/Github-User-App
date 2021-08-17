package com.arya.consumerapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arya.consumerapp.R;
import com.arya.consumerapp.databinding.ItemUserBinding;
import com.arya.consumerapp.entity.User;
import com.bumptech.glide.Glide;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {


    private List<User> users;
    private final UserItemListener listener;
    public UserAdapter(List<User> users, UserItemListener listener) {
        this.users = users;
        this.listener = listener;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        holder.setBinding(users.get(position));
        holder.itemView.setOnClickListener(view -> {listener.userClicked(users.get(position));});
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final ItemUserBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemUserBinding.bind(itemView);
        }

        public void setBinding(User user) {
            binding.itemName.setText(user.getLogin());
            if (user.getId() != 0 && user.getType() != null) {
                binding.itemDesc.setText(new StringBuilder(user.getType() + " ( " + user.getId() + " ) "));
            }
            Glide.with(this.itemView).load(user.getAvatar_url()).into(binding.itemImg);
        }
    }

    public interface UserItemListener{
        void userClicked(User user);
    }
}
