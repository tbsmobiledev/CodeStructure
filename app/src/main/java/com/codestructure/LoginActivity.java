package com.codestructure;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.codestructure.bean.SignUpBean;
import com.codestructure.util.AnimUtils;
import com.codestructure.util.Config;
import com.codestructure.util.Pref;
import com.codestructure.util.Utils;
import com.codestructure.util.Validator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class LoginActivity extends BaseActivity {

    private Activity activity = this;
    private EditText et_email, et_password;
    private TextView tv_login, tv_sign_up;
    private boolean isChecked = false;
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_login:
                    if (validation()) {
                        Gson gson = new Gson();

                        String signUpArrayList = Pref.getValue(activity, Config.PREF_SIGN_UP_ARRAY_LIST, "");
                        Type type = new TypeToken<ArrayList<SignUpBean>>() {
                        }.getType();
                        ArrayList<SignUpBean> arrayList = gson.fromJson(signUpArrayList, type);

                        boolean isEmaiFound = false;
                        if (arrayList != null && arrayList.size() > 0) {
                            for (int i = 0; i < arrayList.size(); i++) {
                                if (arrayList.get(i).email.equals(et_email.getText().toString())) {
                                    isEmaiFound = true;
                                    break;
                                }
                            }
                        }

                        if (isEmaiFound) {
                            Pref.setValue(activity, Config.PREF_IS_LOGGED_IN, true);
                            Intent intent = new Intent(activity, HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                            AnimUtils.activityEnterAnim(activity);
                        } else {
                            Utils.showToast(activity, getResources().getString(R.string.login_error));
                        }
                    }
                    break;

                case R.id.tv_sign_up:
                    Intent signUpIntent = new Intent(activity, SignUpActivity.class);
                    startActivity(signUpIntent);
                    AnimUtils.activityEnterAnim(activity);
                    break;
            }
        }
    };

    /**
     * Initial method of activity to load Views
     *
     * @param savedInstanceState Maintain the state of the app
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.act_login);
        init();
        setListener();
    }

    /**
     * Handle back event
     */
    @Override
    public void onBackPressed() {
        finish();
        AnimUtils.activityExitAnim(activity);
    }

    /**
     * Listener for performing click event
     */
    private void setListener() {
        tv_login.setOnClickListener(onClickListener);
        tv_sign_up.setOnClickListener(onClickListener);
    }

    /**
     * View initialization
     */
    private void init() {
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        tv_login = findViewById(R.id.tv_login);
        tv_sign_up = findViewById(R.id.tv_sign_up);

        et_password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (et_password.getRight() - et_password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        getPasswordVisibility();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    /**
     * To show and hide password
     */
    private void getPasswordVisibility() {
        if (isChecked) {
            isChecked = false;
            et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            et_password.setSelection(et_password.length());
        } else {
            isChecked = true;
            et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            et_password.setSelection(et_password.length());
        }
    }

    /**
     * Check validation if email and password
     *
     * @return true if all fields are filled
     */
    private boolean validation() {
        if (TextUtils.isEmpty(et_email.getText().toString())) {
            Utils.closekeyboard(activity, et_email);
            Utils.showToast(activity, getString(R.string.error_enter_email));
            return false;
        } else if (TextUtils.isEmpty(et_password.getText().toString())) {
            Utils.closekeyboard(activity, et_email);
            Utils.showToast(activity, getString(R.string.error_enter_email));
            return false;
        } else if (!Validator.isValidEmail(et_email.getText().toString())) {
            Utils.closekeyboard(activity, et_email);
            Utils.showToast(activity, getString(R.string.error_enter_email_forgot));
            return false;
        } else {
            return true;
        }
    }

}