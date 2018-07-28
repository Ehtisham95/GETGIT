package app.robo.com.getgit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

public class UserInfoActivity extends AppCompatActivity {

    String username, url, login, avatar_url,name,company, repos_url, followers_num, following_num, location, followers_url, following_url, starred_url;
    MyFragmentPagerAdapter adapter;
    Bundle bundle1= new Bundle();

    Response.Listener<org.json.JSONObject> listenerResponse;
    Response.ErrorListener listenerError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        final ProgressBar pg = findViewById(R.id.progress_user);
        pg.setVisibility(View.VISIBLE);

        final Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");



        //init views
        final ImageView ProfPicImVi = findViewById(R.id.user_image);
        //TextView ProfNameTv = findViewById(R.id.user_name1);
        final TextView ProfDetailsTv = findViewById(R.id.user_info);
        final ViewPager tabViewPager = findViewById(R.id.viewpager);
        final TabLayout tabLayout = findViewById(R.id.tab);
        tabLayout.setupWithViewPager(tabViewPager);
        tabViewPager.setOffscreenPageLimit(4);
        final ImageButton retryBtn = findViewById(R.id.retryBtn);
        retryBtn.setImageResource(R.drawable.retry_24dp);
        retryBtn.setVisibility(View.GONE);
        final TextView errorText = findViewById(R.id.errorText);
        errorText.setVisibility(View.GONE);

        final String url = getIntent().getStringExtra("url");



        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pg.setVisibility(View.VISIBLE);
                JSONObjRequest.getData(Request.Method.GET, getApplicationContext(), url, listenerResponse, listenerError);
                retryBtn.setVisibility(View.GONE);
                errorText.setVisibility(View.GONE);
            }
        });

       listenerResponse = new Response.Listener<org.json.JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {


                try {


                    name = response.getString("name");
                    login = response.getString("login");
                    avatar_url = response.getString("avatar_url");
                    repos_url = response.getString("repos_url");
                    followers_url = response.getString("followers_url");
                    following_url = response.getString("following_url");
                    starred_url = response.getString("starred_url");
                    followers_num = response.getString("followers");
                    following_num = response.getString("following");
                    location = response.getString("location");

                    pg.setVisibility(View.GONE);

                    toolbar.setTitle(name.toString());

                    ProfDetailsTv.setText("User Name: "+login+"\n"+
                            "Followers: " + followers_num + "\n" + "Following: " + following_num + "\n"
                            +"Works At: " +company+"\n"+ "Location: " + location);
                    Picasso.with(getApplicationContext())
                            .load(avatar_url)
                            .into(ProfPicImVi);


                    Bundle bundle1 = getIntent().getExtras();
                    bundle1.putString("username",name);
                    bundle1.putString("name", login);
                    bundle1.putString("avatar", avatar_url);
                    bundle1.putString("repos_url", repos_url);
                    bundle1.putString("followers_url", followers_url);
                    bundle1.putString("following_url", following_url);
                    bundle1.putString("starred_url", starred_url);
                    bundle1.putString("followers", followers_num);
                    bundle1.putString("following", following_num);
                    bundle1.putString("location", location);
                    bundle1.putString("company",company);


                    adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), bundle1);
                    Log.d("bundle1",bundle1.toString());
                    tabViewPager.setAdapter(adapter);


                } catch (Exception e) {
                    pg.setVisibility(View.GONE);
                }
            }
        };

        listenerError = new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                pg.setVisibility(View.GONE);
                errorText.setVisibility(View.VISIBLE);
                retryBtn.setVisibility(View.VISIBLE);
            }
        };

        JSONObjRequest.getData(Request.Method.GET, getApplicationContext(), url, listenerResponse, listenerError);



    }

    public class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {

        public Bundle bundle2;


        public MyFragmentPagerAdapter(FragmentManager fm, Bundle bundle1) {
            super(fm);
            bundle2 = bundle1;


        }

        // This determines the fragment for each tab
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                Fragment fragment = new ReposFragment();
                fragment.setArguments(bundle2);
                return fragment;
            } else if (position == 1) {
                Fragment fragment2 = new FollowersFragment();
                fragment2.setArguments(bundle2);
                return fragment2;
            } else if (position == 2) {
                Fragment fragment3 = new FollowingFragment();
                fragment3.setArguments(bundle2);
                return fragment3;
            } else {
                Fragment fragment4 = new StarredFragment();
                fragment4.setArguments(bundle2);
                return fragment4;
            }
        }


        // This determines the number of tabs
        @Override
        public int getCount() {
            return 4;
        }

        // This determines the title for each tab
        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            switch (position) {
                case 0:
                    return "Repos";
                case 1:
                    return "Followers";
                case 2:
                    return "Following";
                case 3:
                    return "Starred";
                default:
                    return null;
            }
        }

    }

public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
return super.onOptionsItemSelected(item);
}

}
