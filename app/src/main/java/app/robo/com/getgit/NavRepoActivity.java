package app.robo.com.getgit;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.support.v7.widget.ShareActionProvider;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class NavRepoActivity extends AppCompatActivity {


    private ShareActionProvider shareActionProvider;

    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repos);




        Toolbar toolbar = findViewById(R.id.RepToolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_home_black_24dp);

        final ImageView picIv = findViewById(R.id.ownerIv);
        final TextView nameTv = findViewById(R.id.repoName);
        final TextView starTv = findViewById(R.id.starsTv);
        final TextView watchTv = findViewById(R.id.watcherTv);
        final TextView forkTv = findViewById(R.id.forksTv);
        final TextView sizeTv = findViewById(R.id.sizeTv);
        final TextView langTv = findViewById(R.id.langTv);
        final ImageButton sharebtn = findViewById(R.id.shareBtn);



        final ViewPager mView = findViewById(R.id.RepoViewpager);
        final TabLayout mTab = findViewById(R.id.repoTab);
        mTab.setupWithViewPager(mView);
        mView.setOffscreenPageLimit(2);


        sharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (url != null) {
                    Intent share = new Intent();
                    share.setAction(Intent.ACTION_SEND);
                    share.putExtra(Intent.EXTRA_TEXT, String.valueOf(url));
                    share.setType("text/plain");
                    startActivity(Intent.createChooser(share,"Share With "));
                }
            }
        });


        String url = getIntent().getStringExtra("url");
        Log.d("url_repo",url.toString());

        Response.Listener<org.json.JSONObject> listenerResponse = new Response.Listener<org.json.JSONObject>() {


            @Override
            public void onResponse(org.json.JSONObject response) {

                try{

                    String name = response.getString("full_name");
                    String size = response.getString("size");
                    String stars = response.getString("stargazers_count");
                    String watchers = response.getString("watchers_count");
                    String forks = response.getString("forks_count");
                    String lang = response.getString("language");
                    String commits = response.getString("commits_url");
                    String contribute = response.getString("contributors_url");


                    org.json.JSONObject avatar = response.getJSONObject("owner");
                    String pic = avatar.getString("avatar_url");

                    Picasso.with(getApplicationContext())
                            .load(pic)
                            .into(picIv);
                    nameTv.setText(name);
                    starTv.setText(stars);
                    sizeTv.setText(size + " KB");
                    watchTv.setText(watchers);
                    forkTv.setText(forks);
                    langTv.setText(lang);

                    Bundle repoBundle = new Bundle();
                    repoBundle.putString("commit_url",commits);
                    repoBundle.putString("contributors_url",contribute);


                    ReposFragmentAdapter adapter = new ReposFragmentAdapter(getSupportFragmentManager(),repoBundle);
                    mView.setAdapter(adapter);



                } catch (Exception e) {
                }
            }
        };

        Response.ErrorListener listenerError = new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Error " + error, Toast.LENGTH_LONG).show();

            }
        };

        JSONObjRequest.getData(Request.Method.GET,getApplicationContext(), url, listenerResponse, listenerError);




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class ReposFragmentAdapter extends FragmentStatePagerAdapter {

        Bundle info;

        public ReposFragmentAdapter(FragmentManager fm,Bundle repoBundle) {
            super(fm);
            info = repoBundle;
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                Fragment f1 = new CommitFragment();
                f1.setArguments(info);
                return f1;

            } else {
                Fragment f2 = new ContributorsFragment();
                f2.setArguments(info);
                return f2;
            }
        }


        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Commits";
                case 1:
                    return "Contributors";
                default:
                    return null;

            }
        }
    }



    private void setShareActionProvider(Intent shareIntent){
        if (shareActionProvider != null) {
            shareActionProvider.setShareIntent(shareIntent);
        }


    }

}


