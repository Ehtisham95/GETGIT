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

public class ReposActivity extends AppCompatActivity {


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



/*        Bundle extras = getIntent().getExtras();
        String pic = extras.getString("pic");
        String Name = extras.getString("full name");
        String size = extras.getString("size");
        String stars = extras.getString("stars");
        String watchers = extras.getString("watchers");
        String forks = extras.getString("forks");
        String lang = extras.getString("lang");
        String commit_url = extras.getString("commits_url");
        String contributors_url = extras.getString("contribute_url");
        url = extras.getString("url");*/
        Bundle myString=getIntent().getExtras();
   JSONObject object = myString.getParcelable("Repo_info");


        String Name = object.getFull_name();
        String pic = object.getPic();
        String size = object.getSize();
        String stars = object.getStar();
        String watchers = object.getWatchers_count();
        String forks =object.getForks_count();
        String lang = object.getLanguage();
        String commit_url = object.getCommits_url();
        String contributors_url =object.getContributors_url();
        url = object.getUrl();


        Picasso.with(getApplicationContext())
                .load(pic)
                .into(picIv);
        nameTv.setText(Name);
        starTv.setText(stars);
        sizeTv.setText(size + " KB");
        watchTv.setText(watchers);
        forkTv.setText(forks);
        langTv.setText(lang);

        Bundle repoBundle = new Bundle();
        repoBundle.putString("commit_url",commit_url);
        repoBundle.putString("contributors_url",contributors_url);

        ReposFragmentAdapter adapter = new ReposFragmentAdapter(getSupportFragmentManager(),repoBundle);
        mView.setAdapter(adapter);


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


