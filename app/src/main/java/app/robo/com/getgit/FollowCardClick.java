package app.robo.com.getgit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;


public class FollowCardClick extends AppCompatActivity {

    String username,url, login, avatar_url,name,company, repos_url, followers_num, following_num, location, followers_url, following_url, starred;
    Bundle bundle3 = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);


        final Intent intent = new Intent(getApplicationContext(), UserInfoActivity.class);
        String name1 = getIntent().getStringExtra("url");

        url = "https://api.github.com/users/" + name1;

        Response.Listener<org.json.JSONObject> listenerResponse = new Response.Listener<org.json.JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {

                try {

                    username = response.getString("name");
                    name = response.getString("login");
                    avatar_url = response.getString("avatar_url");
                    repos_url = response.getString("repos_url");
                    followers_url = response.getString("followers_url");
                    following_url = response.getString("following_url");
                    starred = response.getString("starred_url");
                    followers_num = response.getString("followers");
                    following_num = response.getString("following");
                    location = response.getString("location");


                    bundle3.putString("username", username);
                    bundle3.putString("name", name);
                    bundle3.putString("avatar", avatar_url);
                    bundle3.putString("repos_url", repos_url);
                    bundle3.putString("followers_url", followers_url);
                    bundle3.putString("following_url", following_url);
                    bundle3.putString("starred_url", starred);
                    bundle3.putString("followers", followers_num);
                    bundle3.putString("following", following_num);
                    bundle3.putString("location", location);


                    intent.putExtras(bundle3);
                    Log.d("info", intent.getExtras().toString());
                    getApplicationContext().startActivity(intent);

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

        JSONObjRequest.getData(Request.Method.GET, getApplicationContext(), url, listenerResponse, listenerError);


    }
}

