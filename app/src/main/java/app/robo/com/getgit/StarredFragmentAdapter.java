package app.robo.com.getgit;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class StarredFragmentAdapter extends RecyclerView.Adapter<StarredFragmentAdapter.ViewHolder>  {


    private List<String> mData;
    private LayoutInflater mInflater;
    String pic;
    private  List<String> mstarData;

    // data is passed into the constructor
    public StarredFragmentAdapter(Context context, ArrayList<String> data,ArrayList<String> starData) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mstarData = starData;

    }

    // inflates the row layout from xml when needed
    @Override
    public StarredFragmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.starred_list, parent, false);
        return new StarredFragmentAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(StarredFragmentAdapter.ViewHolder holder, int position) {
        holder.Starred_repos.setText(mData.get(position));
        holder.star_count.setText(mstarData.get(position));
    }

    // total number of rows
    @Override
    public int getItemCount() {

        return mData.size();


    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView Starred_repos,star_count;

        ViewHolder(View itemView) {
            super(itemView);
            Starred_repos = itemView.findViewById(R.id.starred_repo);
            star_count = itemView.findViewById(R.id.star_count);

        }
    }
}
