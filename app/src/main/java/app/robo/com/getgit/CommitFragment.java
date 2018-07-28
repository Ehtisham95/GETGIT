package app.robo.com.getgit;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

import static com.android.volley.VolleyLog.TAG;


public class CommitFragment extends Fragment {

String commit,commit_url,jsonResponse;
ArrayList<String> author_nameList = new ArrayList<>();
ArrayList<String> commit_dateList = new ArrayList<>();
ArrayList<String> messageList = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        commit = getArguments().getString("commit_url");
        String[] num1 = commit.split("\\{");
        commit_url = num1[0];
        Log.d("commit",commit_url);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.recycler_view, container, false);

    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        final RecyclerView recyclerView = view.findViewById(R.id.ShowRepos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        JsonArrayRequest req = new JsonArrayRequest(commit_url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        ProgressBar progressBar = view.findViewById(R.id.progress);
                        progressBar.setVisibility(view.VISIBLE);

                        try {
                            // Parsing json array response
                            // loop through each json object
                            for (int i = 0; i < response.length(); i++) {
                            JSONObject commit_object = (JSONObject) response.get(i);

                            JSONObject commit = commit_object.getJSONObject("commit");
                            String message = commit.getString("message");
                            JSONObject author = commit.getJSONObject("author");
                            String author_name = author.getString("name");
                            String commit_date = author.getString("date");

                            messageList.add(message);
                            author_nameList.add(author_name);
                            commit_dateList.add(commit_date);

                        }
                            progressBar.setVisibility(view.GONE);
                        Log.d("mList",messageList.toString());
                            mAdapter = new CommitFragmentAdapter(getContext(), messageList, author_nameList,commit_dateList);
                            recyclerView.setAdapter(mAdapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        // Adding request to request queue
        JSONArrayRequest.getInstance().addToRequestQueue(req);



    }
}
