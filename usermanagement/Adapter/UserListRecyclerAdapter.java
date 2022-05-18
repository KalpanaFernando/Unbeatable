package com.example.usermanagement.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usermanagement.Activities.DetailsActivity;
import com.example.usermanagement.Helpers.DatabaseHelper;
import com.example.usermanagement.Model.User;
import com.example.usermanagement.R;

import java.util.List;

public class UserListRecyclerAdapter extends RecyclerView.Adapter<UserListRecyclerAdapter.viewHolder> {

    List<User> mdata;
    DatabaseHelper db;


    private Context context;
    int touched = 0;

    public UserListRecyclerAdapter(List<User> mdata, Context context) {
        this.mdata = mdata;
        this.context = context;
        db = new DatabaseHelper(context);

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_design,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, final int position) {
        //Glide.with(context)
          //      .asBitmap()
            //    .load(mdata.get(position).getImgUri())
              //  .into(holder.usr_img);
        holder.full_name.setText(mdata.get(position).getFname());
        holder.usr_name.setText(mdata.get(position).getUsrname());
        holder.bio.setText(mdata.get(position).getBio());
        //holder.date.setText(mdata.get(position).getDate().toString());
        //holder.views.setText(mdata.get(position).getViews());

        final String f= mdata.get(position).getUsrname().toString();



        holder.parent_layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                User itemLabel = mdata.get(position);
                String em= mdata.get(position).getUsrname();
                mdata.remove(itemLabel);
                notifyItemRemoved(position);
                removeFromDb(em);

                return false;
            }
        });

        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(touched == 0){
                    todetail(f);
                }
            }
        });



    }

    public void removeFromDb(String email){
        // invokes a method in DatabaseHelper that will delete the specified row
        db.remove(email);

    }



    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public void todetail(String username){
        Intent intent= new Intent(context, DetailsActivity.class);
        intent.putExtra("username",username);
        context.startActivity(intent);

    }

    public class viewHolder extends RecyclerView.ViewHolder{
        ImageView usr_img;
        TextView full_name, usr_name, bio, date,views;
        ConstraintLayout parent_layout;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            usr_img = itemView.findViewById(R.id.rv_userimg);
            full_name = itemView.findViewById(R.id.rv_fname);
            usr_name = itemView.findViewById(R.id.rv_usrname);
            bio = itemView.findViewById(R.id.rv_userbio);
            date = itemView.findViewById(R.id.rv_timestamp);
            views = itemView.findViewById(R.id.rv_view_number);
            parent_layout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
