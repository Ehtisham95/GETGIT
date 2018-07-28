package app.robo.com.getgit;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

public class NavRecyclerViewAdapter extends RecyclerView.Adapter<NavRecyclerViewAdapter.ViewHolder> {
    private List<String> mData;
    private LayoutInflater mInflater;
    String pic,url;
    Context context ;
    String owner;

    // data is passed into the constructor
    public NavRecyclerViewAdapter(Context context, ArrayList<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context=context;


    }

    // inflates the row layout from xml when needed
    @Override
    public NavRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.nav_layout, parent, false);
        return new NavRecyclerViewAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(NavRecyclerViewAdapter.ViewHolder holder, final int position) {
        holder.myTextView.setText(mData.get(position));


        holder.mCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = "https://api.github.com/repos/"+mData.get(position);
                Intent intent =new Intent(context,NavRepoActivity.class);
                intent.putExtra("url",url);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
        CardView mCard;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.RepName);
            mCard = itemView.findViewById(R.id.navCard);

        }

    }
}
