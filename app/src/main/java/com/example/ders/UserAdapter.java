package com.example.ders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    UsersData mUsersData;
    LayoutInflater inflater;

    public UserAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        mUsersData = UsersData.getInstance();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User selectedUser = mUsersData.users.get(position);
        holder.setData(selectedUser);
    }

    @Override
    public int getItemCount() {
        return mUsersData.users.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView userName;
        TextView password;
        Switch switchPassword;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userName);
            password = itemView.findViewById(R.id.password);
            switchPassword = itemView.findViewById(R.id.switchPassword);
            imageView = itemView.findViewById(R.id.imageView);
        }

        public void setData(final User selectedUser) {
            this.userName.setText(selectedUser.name);
            this.password.setText("******");
            imageView.setImageResource(R.drawable.blank_profile_picture_973460_640);

            switchPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        password.setText(selectedUser.password);
                    }
                    else {
                        password.setText("******");
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
        }
    }
}