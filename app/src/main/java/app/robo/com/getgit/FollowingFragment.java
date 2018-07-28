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

import java.util.ArrayList;

import static com.android.volley.VolleyLog.TAG;


public class FollowingFragment extends Fragment {


    private RecyclerView.Adapter mAdapter;
    String jsonResponse,following_url,follow1;
    ArrayList<String> list = new ArrayList<>();
    ArrayList<String> pic = new ArrayList<>();

    public FollowingFragment(){

    }

    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        follow1= getArguments().getString("following_url");
        String value = follow1.toString();
        String[] num1 = value.split("\\{");
        following_url = num1[0];
        Log.d("follow1",following_url.toString());


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.recycler_view, container, false);


        return rootView;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        final RecyclerView recyclerView = view.findViewById(R.id.ShowRepos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        JsonArrayRequest req = new JsonArrayRequest(following_url,
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

                                org.json.JSONObject person = (org.json.JSONObject) response
                                        .get(i);
                                String name = person.getString("login");
                                String av_url = person.getString("avatar_url");
                                list.add(name);
                                pic.add(av_url);


                            }

                            progressBar.setVisibility(view.GONE);
                            mAdapter = new FollowersRecyclerView(getContext(),list,pic);
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
