package com.codestructure;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.codestructure.util.AnimUtils;
import com.codestructure.util.Config;
import com.codestructure.util.Pref;

public class SplashActivity extends BaseActivity {

    private Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splash();
    }

    private void splash() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Pref.getValue(activity, Config.PREF_IS_LOGGED_IN, false)) {
                    Intent intent = new Intent(activity, HomeActivity.class);
                    startActivity(intent);
                    AnimUtils.activityEnterAnim(activity);
                    finish();
                } else {
                    Intent intent = new Intent(activity, LoginActivity.class);
                    startActivity(intent);
                    AnimUtils.activityEnterAnim(activity);
                    finish();
                }
            }
        }, 3000);
    }

}