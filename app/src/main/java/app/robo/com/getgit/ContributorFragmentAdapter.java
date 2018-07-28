package app.robo.com.getgit;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

public class ContributorFragmentAdapter extends RecyclerView.Adapter<ContributorFragmentAdapter.ViewHolder> {

    Context context;
    LayoutInflater mInflater;
    ArrayList<String> loginData = new ArrayList<String>();
    ArrayList<String> avatarData = new ArrayList<>();
    ArrayList<String> urlData = new ArrayList<>();

    String url, username,name, avatar_url, repos_url, followers_num, following_num, location, followers_url, following_url, starred;
    public ContributorFragmentAdapter(Context context, ArrayList<String> loginData,ArrayList<String> avatarData,ArrayList<String> urlData){
        this.mInflater= LayoutInflater.from(context);
        this.loginData = loginData;
        this.avatarData = avatarData;
        this.urlData = urlData;
        this.context = context;

    }


    @Override
    public ContributorFragmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.fragment_contributors, parent, false);

        return new ContributorFragmentAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ContributorFragmentAdapter.ViewHolder holder, final int position) {
        holder.loginIv.setText(loginData.get(position));
        Picasso.with(context).load(avatarData.get(position)).into(holder.conIv);

        holder.conCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                url = urlData.get(position);

                Response.Listener<org.json.JSONObject> listenerResponse = new Response.Listener<org.json.JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            username = response.getString("name");
                            name = response.getString("login");
                            avatar_url = response.getString("avatar_url");
                            repos_url = response.getString("repos_url");
                            followers_url = response.getString("followers_url");
                            following_url = response.getString("following_url");
                            starred = response.getString("starred_url");
                            followers_num = response.getString("followers");
                            following_num = response.getString("following");
                            location = response.getString("location");

                            Bundle bundle3 = new Bundle();
                            bundle3.putString("username", username);
                            bundle3.putString("name",name);
                            bundle3.putString("avatar", avatar_url);
                            bundle3.putString("repos_url", repos_url);
                            bundle3.putString("followers_url", followers_url);
                            bundle3.putString("following_url", following_url);
                            bundle3.putString("starred_url", starred);
                            bundle3.putString("followers", followers_num);
                            bundle3.putString("following", following_num);
                            bundle3.putString("location", location);

                            Intent intent = new Intent(context,UserInfoActivity.class);
                            intent.putExtras(bundle3);
                            Log.d("info", intent.getExtras().toString());
                            context.startActivity(intent);

                        } catch (Exception e) {

                        }
                    }
                };

                Response.ErrorListener listenerError = new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(context, "Error " + error, Toast.LENGTH_LONG).show();

                    }
                };

                JSONObjRequest.getData(Request.Method.GET, context, url, listenerResponse, listenerError);



            }
        });

    }

    // total number of rows
    @Override
    public int getItemCount() {

        Log.d("datasize",loginData.size()+"");
        return loginData.size();


    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView loginIv;
        CardView conCard;
        ImageView conIv;

        ViewHolder(View itemView) {
            super(itemView);
            loginIv = itemView.findViewById(R.id.con_name);
            conIv = itemView.findViewById(R.id.con_image);
            conCard =itemView.findViewById(R.id.con_card);


        }
    }
}
