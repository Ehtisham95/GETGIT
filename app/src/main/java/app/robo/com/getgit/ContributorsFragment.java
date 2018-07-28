package app.robo.com.getgit;

import android.app.ProgressDialog;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.android.volley.VolleyLog.TAG;


public class ContributorsFragment extends Fragment {

    Bundle bundle;
    String contribute;

    ArrayList<String> loginList = new ArrayList<>();
    ArrayList<String> avatarList= new ArrayList<>();
    ArrayList<String> urlList = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contribute = getArguments().getString("contributors_url");

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

        JsonArrayRequest req = new JsonArrayRequest(contribute,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ProgressBar progressBar = view.findViewById(R.id.progress);
                        progressBar.setVisibility(view.VISIBLE);

                        try {
                            // Parsing json array response
                            // loop through each json object
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject contribute_object = (JSONObject) response.get(i);

                                String login = contribute_object.getString("login");
                                String avatar = contribute_object.getString("avatar_url");
                                String url = contribute_object.getString("url");

                                loginList.add(login);
                                avatarList.add(avatar);
                                urlList.add(url);

                            }
                            progressBar.setVisibility(view.GONE);
                            Log.d("mList",loginList.toString());
                            mAdapter = new ContributorFragmentAdapter(getContext(), loginList, avatarList,urlList);
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
