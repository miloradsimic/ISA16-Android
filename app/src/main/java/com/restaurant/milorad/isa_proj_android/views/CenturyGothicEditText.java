package com.restaurant.milorad.isa_proj_android.views;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

import com.restaurant.milorad.isa_proj_android.App;


/**
 * Created by Rade on 5/10/2015.
 */
public class CenturyGothicEditText extends EditText {

    public CenturyGothicEditText(Context context) {
        super(context);
        init();
    }

    public CenturyGothicEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CenturyGothicEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (isInEditMode()) {
            return;
        }

        int style = Typeface.NORMAL;

        if (getTypeface() != null) {
            style = getTypeface().getStyle();
        }

        setTypeface(App.CENTURY_GOTHIC_TYPEFACE, style);
        setPaintFlags(getPaintFlags() | Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG | Paint.SUBPIXEL_TEXT_FLAG);
    }
}

