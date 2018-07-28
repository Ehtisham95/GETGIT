package app.robo.com.getgit;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class SearchActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    String url,company,  login, username, avatar_url, repos_url, followers_num, following_num, location, followers_url, following_url, starred;

    Bundle bundle1 = new Bundle();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


       Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp_2);



        final Intent intent = new Intent(getApplicationContext(), UserInfoActivity.class);
        final ProgressBar pd = findViewById(R.id.progress_search);
        pd.setVisibility(View.GONE);

        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        final EditText userNameEdTxt = findViewById(R.id.EntNameEditText);
        final ImageButton searchBtn = findViewById(R.id.searchButton);
        searchBtn.setImageResource(R.drawable.ic_search_black_24dp);

       mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.mynavlist);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                int id = item.getItemId();
                if(id == R.id.java){
                    Intent nav_intent = new Intent(getApplicationContext(),navRepos.class);
                    nav_intent.putExtra("name","java");
                    startActivity(nav_intent);
                }
                else if(id == R.id.cpp){

                    Intent nav_intent = new Intent(getApplicationContext(),navRepos.class);
                    nav_intent.putExtra("name","cpp");
                    startActivity(nav_intent);
                }
                else if(id == R.id.kotlin){
                    Intent nav_intent = new Intent(getApplicationContext(),navRepos.class);
                    nav_intent.putExtra("name","kotlin");
                    startActivity(nav_intent);
                }
                else if(id == R.id.lua){
                    Intent nav_intent = new Intent(getApplicationContext(),navRepos.class);
                    nav_intent.putExtra("name","lua");
                    startActivity(nav_intent);
                }
                DrawerLayout drawer =  findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);

                return true;
            }
        });





        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                imm.hideSoftInputFromWindow(searchBtn.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                pd.setVisibility(View.VISIBLE);

                url = "https://api.github.com/users/" + userNameEdTxt.getText().toString();

                Log.d("url_e",url);
                            intent.putExtra("url",url);

                            pd.setVisibility(View.GONE);
                            startActivity(intent);
                            overridePendingTransition(R.anim.push_up_in, android.R.anim.slide_out_right);

            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                overridePendingTransition(0, android.R.anim.slide_in_left);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
