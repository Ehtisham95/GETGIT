package app.robo.com.getgit;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.android.volley.VolleyLog.TAG;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<String> mData;
    private LayoutInflater mInflater;
    String owner;
    private Context context;

    Bundle bundle4 = new Bundle();
Bundle repoInfo;

    String url,name;

    // data is passed into the constructor
    public MyRecyclerViewAdapter(Context context, ArrayList<String> data,String owner) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
this.owner= owner;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.show_list, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.myTextView.setText(mData.get(position));

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                url = "https://api.github.com/repos/" + owner+"/"+mData.get(position);

                Log.d("url_repo",url.toString());
                Response.Listener<JSONObject> listenerResponse = new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {

                        try{
                            String name = response.getString("full_name");
                            String desc = response.getString("description");
                            String size = response.getString("size");
                            String stars = response.getString("stargazers_count");
                            String watchers = response.getString("watchers_count");
                            String forks = response.getString("forks_count");
                            String lang = response.getString("language");
                            String commits = response.getString("commits_url");
                            String contribute = response.getString("contributors_url");


                            JSONObject avatar = response.getJSONObject("owner");
                            String pic = avatar.getString("avatar_url");

                          /*  Bundle b1=new Bundle();
                            b1.putString("url",url);
                            b1.putString("full name",name);
                            b1.putString("desc",desc);
                            b1.putString("size",size);
                            b1.putString("stars",stars);
                            b1.putString("watchers",watchers);
                            b1.putString("forks",forks);
                            b1.putString("lang",lang);
                            b1.putString("pic",pic);
                            b1.putString("commits_url",commits);
                            b1.putString("contribute_url",contribute);*/

                            app.robo.com.getgit.JSONObject RepoObject = new app.robo.com.getgit.JSONObject(name,pic,size,stars,watchers,forks,lang,commits,contribute,url);

                            Intent intent = new Intent(context,ReposActivity.class);
                           /* Gson gson = new Gson();
                            String myString = gson.toJson(RepoObject);*/
                            intent.putExtra("Repo_info",RepoObject);

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

                JSONObjRequest.getData(Request.Method.GET,context, url, listenerResponse, listenerError);

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
        CardView mCardView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.RepName);
            mCardView = itemView.findViewById(R.id.card_view1);

        }

    }

}