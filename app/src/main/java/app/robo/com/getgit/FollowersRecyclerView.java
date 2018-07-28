package app.robo.com.getgit;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FollowersRecyclerView extends RecyclerView.Adapter<FollowersRecyclerView.ViewHolder>  {


    private List<String> mData;
    private LayoutInflater mInflater;
    String pic;
    private List<String> avatarData;
    ImageView UserImg;
    private Context context;
    Bundle bundle3 = new Bundle();


    String url, username,name, avatar_url, repos_url, followers_num, following_num, location, followers_url, following_url, starred;
    // data is passed into the constructor
    public FollowersRecyclerView(@Nullable Context context,@Nullable ArrayList<String> data,@Nullable ArrayList<String> avData) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.avatarData = avData;


    }

    // inflates the row layout from xml when needed
    @Override
    public FollowersRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.follow_list, parent, false);
        context = parent.getContext();
        return new FollowersRecyclerView.ViewHolder(view);

    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(FollowersRecyclerView.ViewHolder holder, final int position) {

        holder.myTextView.setText(mData.get(position));
        Log.d("avatar_url",avatarData.get(position));
        Picasso.with(context).load(avatarData.get(position)).into(holder.myImgView);

        holder.myCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Intent intent = new Intent(context, UserInfoActivity.class);
                 url = "https://api.github.com/users/" + mData.get(position);
                            intent.putExtra("url",url);
                            context.startActivity(intent);

            }
        });

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();

    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView myTextView;
        ImageView myImgView;
        CardView myCard;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.user_name);
            myImgView = itemView.findViewById(R.id.user_image);
            myCard = itemView.findViewById(R.id.user_card);

        }

    }
}
