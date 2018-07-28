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
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.android.volley.VolleyLog.TAG;


public class ReposFragment extends Fragment {


    private RecyclerView.Adapter mAdapter;
    String jsonResponse,rep_url;
    ArrayList<String> list = new ArrayList<>();
    String owner_name;

    public ReposFragment(){

    }

    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        rep_url = getArguments().getString("repos_url");
        Log.d("rep",rep_url.toString());


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.recycler_view, container, false);


        return rootView;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



// set up the RecyclerView
        final RecyclerView recyclerView = view.findViewById(R.id.ShowRepos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        JsonArrayRequest req = new JsonArrayRequest(rep_url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        ProgressBar progressBar = view.findViewById(R.id.progress);
                        progressBar.setVisibility(view.VISIBLE);

                        try {
                            // Parsing json array response
                            // loop through each json object
                            jsonResponse = "";
                            for (int i = 0; i < response.length(); i++) {

                                org.json.JSONObject repos = (org.json.JSONObject) response
                                        .get(i);
                                String name = repos.getString("name");

                                JSONObject owner=repos.getJSONObject("owner");
                                owner_name = owner.getString("login");

                                list.add(name);




                            }
                            progressBar.setVisibility(view.GONE);
                            Log.d("owner",owner_name+"");
                            mAdapter = new MyRecyclerViewAdapter(getContext(),list,owner_name);
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
