package com.codestructure.util;

import android.app.Activity;

import com.codestructure.R;

public class AnimUtils {

    public static void activityEnterAnim(Activity context) {
        context.overridePendingTransition(R.anim.act_slide_in_left, R.anim.act_slide_out_left);
    }

    public static void activityExitAnim(Activity context) {
        context.overridePendingTransition(R.anim.act_slide_in_right, R.anim.act_slide_out_right);
    }

    public static void activityBottomToUpAnimNoChange(Activity context) {
        context.overridePendingTransition(R.anim.no_change, R.anim.act_slide_up);
    }

}