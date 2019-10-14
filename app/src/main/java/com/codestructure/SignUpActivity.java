package com.codestructure;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends BaseActivity {

    private final Calendar myCalendar = Calendar.getInstance();
    private Activity activity = this;
    private ImageView iv_close;
    private EditText et_first_name, et_last_name, et_email, et_password, et_confirm_password, et_mobile, et_birth_date;
    private ProgressBar progress_pass_strength;
    private TextView tv_pass_strength, tv_termsCondition, tv_createUser;
    private RadioButton rb_iagree_to_the_terms;
    private ArrayList<SignUpBean> signUpBeanArrayList;
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.iv_close:
                    finish();
                    AnimUtils.activityExitAnim(activity);
                    break;

                case R.id.tv_createUser:
                    if (validation()) {
                        createUser();
                    }
                    break;

                case R.id.tv_termsCondition:
                    dialogTearmCondition(Config.PRIVACY_POLICY);
//                    dialogTearmCondition(termsConditionBean.content);
                    break;

                case R.id.et_birth_date:
                    Utils.closekeyboard(activity, et_birth_date);
                    datePicker();
                    break;
            }
        }
    };

    /**
     * Main method of activity
     *
     * @param savedInstanceState return state if instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.act_sign_up);
        init();
        setListener();
    }

    /**
     * View initialization
     */
    private void init() {
        iv_close = findViewById(R.id.iv_close);
        tv_termsCondition = findViewById(R.id.tv_termsCondition);

        et_first_name = findViewById(R.id.et_first_name);
        et_last_name = findViewById(R.id.et_last_name);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        et_confirm_password = findViewById(R.id.et_confirm_password);
        et_mobile = findViewById(R.id.et_mobile);
        et_birth_date = findViewById(R.id.et_birth_date);

        rb_iagree_to_the_terms = findViewById(R.id.rb_iagree_to_the_terms);

        tv_createUser = findViewById(R.id.tv_createUser);

        tv_pass_strength = findViewById(R.id.tv_pass_strength);
        progress_pass_strength = findViewById(R.id.progress_pass_strength);

        setFilter(et_password);
        setFilter(et_confirm_password);

        signUpBeanArrayList = new ArrayList<>();
    }

    /**
     * Not allow space in password
     *
     * @param editText
     */
    private void setFilter(EditText editText) {
        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (Character.isWhitespace(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        };

        editText.setFilters(new InputFilter[]{filter});
    }

    /**
     * Listener for performing click
     */
    private void setListener() {
        iv_close.setOnClickListener(onClickListener);
        tv_createUser.setOnClickListener(onClickListener);
        tv_termsCondition.setOnClickListener(onClickListener);
        et_birth_date.setOnClickListener(onClickListener);

        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                int strength = 0;
                Pattern pattern1, pattern2, pattern3, pattern4, pattern5;
                Matcher matcher1, matcher2, matcher3, matcher4, matcher5;

                String regex1 = ".*[a-z]+.*";
                pattern1 = Pattern.compile(regex1);
                String regex2 = ".*[0-9]+.*";
                pattern2 = Pattern.compile(regex2);
                String regex3 = ".*[A-Z]+.*";
                pattern3 = Pattern.compile(regex3);
                String regex4 = ".*[!&^%$#@()/]+.*";
                pattern4 = Pattern.compile(regex4);
                String regex5 = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[`~!@#$/%|^&*()_+=?><.,;:}{'x])(?=\\S+$).{8,}$";
                pattern5 = Pattern.compile(regex5);

                String concet = et_password.getText().toString();

                matcher1 = pattern1.matcher(concet);
                matcher2 = pattern2.matcher(concet);
                matcher3 = pattern3.matcher(concet);
                matcher4 = pattern4.matcher(concet);
                matcher5 = pattern5.matcher(concet);

                if (concet.length() < 6) {
                    strength = 0;
                } else {

                    if (concet.length() > 6 && matcher1.matches()) {
                        strength = strength + 1;
                    }
                    if (concet.length() > 6 && matcher2.matches()) {
                        strength = strength + 1;
                    }
                    if (concet.length() > 6 && matcher3.matches()) {
                        strength = strength + 1;
                    }
                    if (concet.length() > 6 && matcher4.matches()) {
                        strength = strength + 1;
                    }
                    if (concet.length() > 6 && matcher5.matches()) {
                        strength = strength + 1;
                    }
                }

                switch (strength) {
                    case 0:
                        tv_pass_strength.setVisibility(View.INVISIBLE);
                        progress_pass_strength.setVisibility(View.INVISIBLE);
                        progress_pass_strength.setProgress(0);
                        break;

                    case 1:
                    case 2:
                        tv_pass_strength.setVisibility(View.VISIBLE);
                        tv_pass_strength.setText(getResources().getString(R.string.week_pass));
                        tv_pass_strength.setTextColor(getResources().getColor(R.color.week_pass_color));
                        progress_pass_strength.setVisibility(View.VISIBLE);
                        progress_pass_strength.getProgressDrawable().setColorFilter(getResources().getColor(R.color.week_pass_color), PorterDuff.Mode.SRC_IN);
                        progress_pass_strength.setProgress(25);
                        break;

                    case 3:
                    case 4:
                        tv_pass_strength.setVisibility(View.VISIBLE);
                        tv_pass_strength.setText(getResources().getString(R.string.average_pass));
                        tv_pass_strength.setTextColor(getResources().getColor(R.color.average_pass_color));
                        progress_pass_strength.setVisibility(View.VISIBLE);
                        progress_pass_strength.getProgressDrawable().setColorFilter(getResources().getColor(R.color.average_pass_color), PorterDuff.Mode.SRC_IN);
                        progress_pass_strength.setProgress(50);
                        break;

                    default:
                        tv_pass_strength.setVisibility(View.VISIBLE);
                        tv_pass_strength.setText(getResources().getString(R.string.strong_pass));
                        tv_pass_strength.setTextColor(getResources().getColor(R.color.colorPrimary));
                        progress_pass_strength.setVisibility(View.VISIBLE);
                        progress_pass_strength.getProgressDrawable().setColorFilter(getResources().getColor(R.color.strong_pass_color), PorterDuff.Mode.SRC_IN);
                        progress_pass_strength.setProgress(100);
                        break;
                }
            }
        });
    }

    /**
     * Open date picker to select age
     */
    private void datePicker() {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(activity, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    /**
     * Update label(EditText) of age
     */
    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        int age = getAge(sdf.format(myCalendar.getTime()));
        Utils.showLog("Date", String.valueOf(age));

        if (age >= 18) {
            et_birth_date.setText(sdf.format(myCalendar.getTime()));
        } else {
            Utils.showToast(activity, getResources().getString(R.string.age_validation));
        }
    }

    /**
     * Check for the age is above 18
     *
     * @param date Date we select from date picker
     * @return age
     */
    public int getAge(String date) {
        int age = 0;
        try {
            String myFormat = "dd/MM/yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date date1 = sdf.parse(date);
            Calendar now = Calendar.getInstance();
            Calendar dob = Calendar.getInstance();
            dob.setTime(date1);
            if (dob.after(now)) {
                throw new IllegalArgumentException("Can't be born in the future");
            }
            int year1 = now.get(Calendar.YEAR);
            int year2 = dob.get(Calendar.YEAR);
            age = year1 - year2;
            int month1 = now.get(Calendar.MONTH);
            int month2 = dob.get(Calendar.MONTH);
            if (month2 > month1) {
                age--;
            } else if (month1 == month2) {
                int day1 = now.get(Calendar.DAY_OF_MONTH);
                int day2 = dob.get(Calendar.DAY_OF_MONTH);
                if (day2 > day1) {
                    age--;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return age;
    }

    /**
     * Showing terms and condition dialog
     *
     * @param content Url content
     */
    private void dialogTearmCondition(String content) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_terms_conditions);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);

        WebView webView = dialog.findViewById(R.id.webview);
        TextView tv_accept = dialog.findViewById(R.id.tv_accept);
        TextView tv_dontAccept = dialog.findViewById(R.id.tv_dontAccept);

        webView.setWebViewClient(new MyWebViewClient());
        webView.loadUrl(content);

        dialog.show();

        tv_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                rb_iagree_to_the_terms.setChecked(true);
            }
        });

        tv_dontAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (rb_iagree_to_the_terms.isChecked()) {
                    rb_iagree_to_the_terms.setChecked(false);
                }
            }
        });
    }

    /**
     * Load terms and condition
     */
    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(Config.PRIVACY_POLICY);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Utils.showProgress(activity);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Utils.hideProgress();
        }
    }

    /**
     * Check for validation
     *
     * @return true if success
     * false if fails
     */
    private boolean validation() {
        if (TextUtils.isEmpty(et_first_name.getText().toString())) {
            Utils.closekeyboard(activity, et_first_name);
            Utils.showToast(activity, getString(R.string.error_firstname));
            return false;
        } else if (TextUtils.isEmpty(et_last_name.getText().toString())) {
            Utils.closekeyboard(activity, et_last_name);
            Utils.showToast(activity, getString(R.string.error_lastname));
            return false;
        } else if (TextUtils.isEmpty(et_email.getText().toString())) {
            Utils.closekeyboard(activity, et_email);
            Utils.showToast(activity, getString(R.string.error_enter_email));
            return false;
        } else if (!Validator.isValidEmail(et_email.getText().toString())) {
            Utils.closekeyboard(activity, et_email);
            Utils.showToast(activity, getString(R.string.error_enter_email_forgot));
            return false;
        } else if (TextUtils.isEmpty(et_birth_date.getText().toString())) {
            Utils.closekeyboard(activity, et_birth_date);
            Utils.showToast(activity, getString(R.string.error_enter_birth_date));
            return false;
        } else if (TextUtils.isEmpty(et_password.getText().toString())) {
            Utils.closekeyboard(activity, et_password);
            Utils.showToast(activity, getString(R.string.error_enterpw));
            return false;
        } else if (!Validator.isValidPasswordStrength(et_password)) {
            Utils.closekeyboard(activity, et_password);
            Utils.showToast(activity, getString(R.string.error_enter_password_strength));
            return false;
        } else if (TextUtils.isEmpty(et_confirm_password.getText().toString())) {
            Utils.closekeyboard(activity, et_confirm_password);
            Utils.showToast(activity, getString(R.string.error_enterpw_confirm));
            return false;
        } else if (!Validator.isEqualPassword(et_password, et_confirm_password, activity)) {
            Utils.closekeyboard(activity, et_confirm_password);
            Utils.showToast(activity, getString(R.string.error_enter_valid_password));
            return false;
        } else if (TextUtils.isEmpty(et_mobile.getText().toString())) {
            Utils.closekeyboard(activity, et_mobile);
            Utils.showToast(activity, getString(R.string.error_enter_number));
            return false;
        } else if (!Validator.isValidNumber(et_mobile, activity)) {
            Utils.closekeyboard(activity, et_mobile);
            Utils.showToast(activity, getString(R.string.error_enter_valid_number));
            return false;
        } else if (!Validator.isTermsConditionChecked(rb_iagree_to_the_terms, activity)) {
            Utils.showToast(activity, getString(R.string.error_terms_condition));
            return false;
        } else {
            return true;
        }
    }

    /**
     * User registration in the app
     */
    private void createUser() {
        Gson gson = new Gson();

        String signUpArrayList = Pref.getValue(activity, Config.PREF_SIGN_UP_ARRAY_LIST, "");
        Type type = new TypeToken<ArrayList<SignUpBean>>() {
        }.getType();
        ArrayList<SignUpBean> arrayList = null;
        if (gson.fromJson(signUpArrayList, type) != null) {
            arrayList = gson.fromJson(signUpArrayList, type);
        }

        boolean isEmaiFound = false;
        if (arrayList != null && arrayList.size() > 0) {
            for (int i = 0; i < arrayList.size(); i++) {
                if (arrayList.get(i).email.equals(et_email.getText().toString())) {
                    isEmaiFound = true;
                    break;
                }
            }
        }

        if (!isEmaiFound) {
            SignUpBean signUpBean = new SignUpBean();

            signUpBean.name = et_first_name.getText().toString();
            signUpBean.surName = et_last_name.getText().toString();
            signUpBean.email = et_email.getText().toString();
            signUpBean.birthDate = et_birth_date.getText().toString();
            signUpBean.password = et_password.getText().toString();
            signUpBean.phoneNumber = et_mobile.getText().toString();

            signUpBeanArrayList.add(signUpBean);

            if (arrayList != null && arrayList.size() > 0) {
                arrayList.addAll(signUpBeanArrayList);
            } else {
                arrayList = new ArrayList<>();
                arrayList.addAll(signUpBeanArrayList);
            }

            String json = gson.toJson(arrayList);

            Pref.setValue(activity, Config.PREF_SIGN_UP_ARRAY_LIST, json);
            Pref.setValue(activity, Config.PREF_IS_LOGGED_IN, true);

            Intent intent = new Intent(activity, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            AnimUtils.activityEnterAnim(activity);
        } else {
            Utils.showToast(activity, getResources().getString(R.string.sign_up_error));
        }
    }

}