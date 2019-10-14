package com.codestructure.util;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.codestructure.BuildConfig;
import com.codestructure.R;

import java.util.Objects;

public class Utils {

    private static final boolean IS_LOG = true;
    private static Dialog dialogProgress;

    @SuppressLint("InflateParams")
    public static void showToast(Context context, Object obj) {

        if (obj != null) {
            // create instance
            try {
                Toast toast = new Toast(context);
                // inflate custom view
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = null;
                if (inflater != null) {
                    view = inflater.inflate(R.layout.custom_toast, null);
                }

                TextView txtMessage = null;
                if (view != null) {
                    txtMessage = view.findViewById(R.id.txtMessage);
                }

                if (txtMessage != null) {
                    txtMessage.setText(obj.toString());
                }

                // set custom view
                toast.setView(view);

                // set duration
                toast.setDuration(Toast.LENGTH_SHORT);

                // show toast
                toast.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void closekeyboard(Context context, View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void showkeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    public static void showLog(String tag, String message) {
        if (IS_LOG) {
            if (BuildConfig.DEBUG) {
                Log.e(tag, message);
            }
        }
    }

    public static void showProgress(Context context) {
        dialogProgress = new Dialog(context);
        Objects.requireNonNull(dialogProgress.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogProgress.setContentView(R.layout.custom_progressbar_layout);
        dialogProgress.setCanceledOnTouchOutside(false);
        dialogProgress.setCancelable(false);
        if (!dialogProgress.isShowing()) {
            dialogProgress.show();
        }
    }

    public static void hideProgress() {
        if (dialogProgress != null && dialogProgress.isShowing()) {
            dialogProgress.dismiss();
            dialogProgress.cancel();
            dialogProgress = null;
        }
    }
}