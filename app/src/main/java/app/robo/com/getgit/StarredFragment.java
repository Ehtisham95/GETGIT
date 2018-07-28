package app.robo.com.getgit;

import android.content.Context;
import android.graphics.ColorSpace;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.BoringLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import java.util.Collections;

import static com.android.volley.VolleyLog.TAG;


public class StarredFragment extends Fragment {


    private RecyclerView.Adapter mAdapter;
    String jsonResponse, starred_url, starred1;
    ArrayList<String> list = new ArrayList<>();
    ArrayList<String> starList = new ArrayList<>();


    Boolean toggle = false;
    Boolean count;

    public StarredFragment() {


    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        starred1 = getArguments().getString("starred_url");
        String value = starred1;
        String[] num1 = value.split("\\{");
        starred_url = num1[0];
        starred_url = starred_url + "?sort=stars";
        Log.d("starred1", starred_url);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.starred_recycler_view, container, false);


        return rootView;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final RecyclerView recyclerView = view.findViewById(R.id.showstarred);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        final FloatingActionButton order_btn = view.findViewById(R.id.order_btn);

        order_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toggle == false) {
                    order_btn.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                    Collections.reverse(list);
                    Collections.reverse(starList);

                    mAdapter = new StarredFragmentAdapter(getContext(), list, starList);
                    recyclerView.setAdapter(mAdapter);
                    toggle = true;

                } else {
                    order_btn.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                    Collections.reverse(list);
                    Collections.reverse(starList);

                    mAdapter = new StarredFragmentAdapter(getContext(), list, starList);
                    recyclerView.setAdapter(mAdapter);
                        toggle = false;

                }

            }
        });


            JsonArrayRequest req = new JsonArrayRequest(starred_url,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            ProgressBar progressBar = view.findViewById(R.id.progress1);
                            progressBar.setVisibility(view.VISIBLE);

                            try {
                                // Parsing json array response
                                // loop through each json object
                                jsonResponse = "";
                                for (int i = 0; i < response.length(); i++) {

                                    JSONObject login = (JSONObject) response
                                            .get(i);

                                    String name = login.getString("name");
                                    int stars = login.getInt("stargazers_count");
                                    Log.d("stars", String.valueOf(stars));

                                    String star_count = String.valueOf(stars);
                                    list.add(name);
                                    starList.add(star_count);
                                }

                                progressBar.setVisibility(view.GONE);
                                mAdapter = new StarredFragmentAdapter(getContext(), list, starList);
                                recyclerView.setAdapter(mAdapter);
                                count = false;

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

