package app.robo.com.getgit;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.support.v7.widget.OrientationHelper.HORIZONTAL;

public class navRepos extends AppCompatActivity {

    private RecyclerView.Adapter mAdapter;
    String items,repo_name, repo_url;
    ArrayList<String> list = new ArrayList<>();
    ArrayList<String> name = new ArrayList<>();


Bundle bundle = new Bundle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_recyclerview);

/*Toolbar toolbar = findViewById(R.id.toolbar3);
toolbar.setTitle("Repositories");*/

        final ProgressBar pd = findViewById(R.id.progress_nav);
        pd.setVisibility(View.VISIBLE);



        // set up the RecyclerView
        final RecyclerView recyclerView = findViewById(R.id.ShowRepos1);



        Toolbar toolbar2 = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar2);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Repositories");

        bundle= getIntent().getExtras();
        repo_name = bundle.getString("name");

        repo_url = "https://api.github.com/search/repositories?q="+repo_name;
        Response.Listener<JSONObject> listenerResponse = new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {

                try{

                    JSONArray arr = response.getJSONArray("items");

                    for (int i = 0; i < 20; i++)
                    {
                        String fname = arr.getJSONObject(i).getString("full_name");
                        list.add(fname);
                        Log.d("list",list.toString());
                    }

                    pd.setVisibility(View.GONE);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    mAdapter = new NavRecyclerViewAdapter(getApplicationContext(),list);
                    recyclerView.setAdapter(mAdapter);







            } catch (Exception e) {
                    pd.setVisibility(View.GONE);
            }
        }
    };

    Response.ErrorListener listenerError = new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {
            pd.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "Error " + error, Toast.LENGTH_LONG).show();

        }
    };

                JSONObjRequest.getData(Request.Method.GET,getApplicationContext(), repo_url, listenerResponse, listenerError);

};


    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    }

