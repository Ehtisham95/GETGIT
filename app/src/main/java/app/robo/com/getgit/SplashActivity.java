package app.robo.com.getgit;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final int SPLASH_DURATION = 2000;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(getApplicationContext(),SearchActivity.class);
                SplashActivity.this.startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                SplashActivity.this.finish();
            }
        },SPLASH_DURATION);

    }
}
