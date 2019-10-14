package com.codestructure;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codestructure.fragment.HomeFragment;
import com.codestructure.fragment.ListFragment;
import com.codestructure.util.AnimUtils;
import com.codestructure.util.Config;
import com.codestructure.util.Pref;
import com.codestructure.util.Utils;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    private Activity activity = this;
    private boolean doubleBackToExitPressedOnce = false;
    private TextView tv_logout, tv_home_label, tv_list;
    private LinearLayout ll_home, ll_list;
    private ImageView iv_home, iv_list;
    private FragmentManager fragmentManager;
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
            switch (v.getId()) {
                case R.id.tv_logout:
                    dialogLogout();
                    break;

                case R.id.ll_home:
                    if (!(fragment instanceof HomeFragment)) {
                        setHomeFragment();
                    }
                    break;

                case R.id.ll_list:
                    if (!(fragment instanceof ListFragment)) {
                        setListFragment();
                    }
                    break;
            }
        }
    };

    /**
     * @param savedInstanceState Maintain instance of app
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home);
        init();
        setListener();
    }

    /**
     * Handle back event
     */
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finish();
        }
        this.doubleBackToExitPressedOnce = true;
        Utils.showToast(activity, getString(R.string.press_back_again));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 3000);
    }

    /**
     * View initialization
     */
    private void init() {
        tv_logout = findViewById(R.id.tv_logout);
        tv_home_label = findViewById(R.id.tv_home_label);
        tv_list = findViewById(R.id.tv_list);
        ll_home = findViewById(R.id.ll_home);
        ll_list = findViewById(R.id.ll_list);
        iv_home = findViewById(R.id.iv_home);
        iv_list = findViewById(R.id.iv_list);

        fragmentManager = getSupportFragmentManager();

        setHomeFragment();
    }

    /**
     * Handle click event
     */
    private void setListener() {
        tv_logout.setOnClickListener(onClickListener);
        ll_home.setOnClickListener(onClickListener);
        ll_list.setOnClickListener(onClickListener);
    }

    /**
     * Logout confirmation dialog
     */
    private void dialogLogout() {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_logout);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);

        TextView tv_yes = dialog.findViewById(R.id.tv_yes);
        TextView tv_no = dialog.findViewById(R.id.tv_no);

        dialog.show();

        tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Pref.setValue(activity, Config.PREF_IS_LOGGED_IN, false);
                Intent intent = new Intent(activity, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                AnimUtils.activityExitAnim(activity);
            }
        });

        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    /**
     * Push home fragment in stack
     */
    private void setHomeFragment() {
        tv_home_label.setTextColor(getResources().getColor(R.color.colorPrimary));
        tv_list.setTextColor(getResources().getColor(R.color.black));
        iv_home.setImageResource(R.mipmap.ic_home);
        iv_list.setImageResource(R.mipmap.ic_list_unselected);


        pushFragment(new HomeFragment());
    }

    /**
     * Push home fragment in stack
     */
    private void setListFragment() {
        tv_home_label.setTextColor(getResources().getColor(R.color.black));
        tv_list.setTextColor(getResources().getColor(R.color.colorPrimary));
        iv_home.setImageResource(R.mipmap.ic_home);
        iv_list.setImageResource(R.mipmap.ic_list_selected);

        pushFragment(new ListFragment());
    }

    /**
     * Replace fragment in stack
     *
     * @param fragment Fragment to be loaded in stack
     */
    private void pushFragment(Fragment fragment) {
        if (fragmentManager != null) {
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
        }
    }

}