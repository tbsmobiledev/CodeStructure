package com.codestructure.util;

import android.content.Context;
import android.util.Patterns;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Kaushik
 */

public class Validator {
    public static boolean isValidPasswordStrength(EditText editText) {
        boolean valid = true;
        Pattern pattern;
        Matcher matcher;
        String password = editText.getText().toString();

//        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[`~!@#$/%|^&*()_+=?><.,;:}{'x])(?=\\S+$).{8,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        if (!matcher.matches()) {
            valid = false;
        }
        return valid;
    }

//////-----------Validate By String------------------------------------------------------------

    public static boolean isValidEmail(String email) {
        boolean valid = true;
        if (email.isEmpty() || email.trim().length() == 0 || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            valid = false;
        }
        return valid;
    }

    public static boolean isEqualPassword(EditText edtPassword, EditText edtConfirmPassword, Context context) {
        boolean valid = true;
        String password = edtPassword.getText().toString();
        String confirmPassword = edtConfirmPassword.getText().toString();

        if (!password.equals(confirmPassword)) {
            valid = false;
        }
        return valid;
    }

    public static boolean isValidNumber(EditText editText, Context context) {
        boolean valid = true;
        String password = editText.getText().toString();
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=\\S+$).{8,15}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        if (!matcher.matches()) {
            valid = false;
        } else {
            editText.setError(null);
        }
        return valid;
    }

    public static boolean isTermsConditionChecked(RadioButton radioButton, Context context) {
        boolean valid = true;
        if (!radioButton.isChecked()) {
            valid = false;
        }
        return valid;
    }
}