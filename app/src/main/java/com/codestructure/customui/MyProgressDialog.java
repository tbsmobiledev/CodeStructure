package com.codestructure.customui;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;

import com.codestructure.R;


public class MyProgressDialog  extends AlertDialog {
    public MyProgressDialog(Context context) {
        super(context);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    @Override
    public void show() {
        super.show();
        setContentView(R.layout.progress_dialog);
    }
}