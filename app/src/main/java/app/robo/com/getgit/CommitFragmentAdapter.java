package app.robo.com.getgit;

import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CommitFragmentAdapter extends RecyclerView.Adapter<CommitFragmentAdapter.ViewHolder> {

    LayoutInflater mInflater;
    ArrayList<String> mData = new ArrayList<String>();
    ArrayList<String> nData = new ArrayList<>();
    ArrayList<String> dData = new ArrayList<>();


    public CommitFragmentAdapter(Context context, ArrayList<String> mData,ArrayList<String> nData,ArrayList<String> dData){
        this.mInflater= LayoutInflater.from(context);
        this.mData = mData;
        this.nData = nData;
        this.dData = dData;

    }


    @Override
    public CommitFragmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.fragment_commit, parent, false);
        return new CommitFragmentAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(CommitFragmentAdapter.ViewHolder holder, int position) {
        String[] datearray = dData.get(position).split("T");
        String date=datearray[0];
        holder.messageTv.setText(mData.get(position));
        holder.dateTv.setText(nData.get(position)+" "+date);

    }

    // total number of rows
    @Override
    public int getItemCount() {

        Log.d("datasize",mData.size()+"");
        return mData.size();


    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView messageTv,dateTv;

        ViewHolder(View itemView) {
            super(itemView);
            messageTv = itemView.findViewById(R.id.message1);
            dateTv = itemView.findViewById(R.id.date);



        }
    }
}
