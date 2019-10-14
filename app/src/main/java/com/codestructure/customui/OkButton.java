package com.codestructure.customui;

import android.content.Context;
import android.util.AttributeSet;


public class OkButton extends android.support.v7.widget.AppCompatButton {

	public OkButton(Context context) {
		super(context);
	}

	public OkButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

	}

	public OkButton(Context context, AttributeSet attrs) {
		super(context, attrs);

		if(this.getTag()!=null) {

	//		this.setTypeface(UtilsFunctions.getFont(context, this.getTag().toString()));
//			this.setTypeface(UtilsFunctions.getFont(context,attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "tag")));
		}
	}
}
