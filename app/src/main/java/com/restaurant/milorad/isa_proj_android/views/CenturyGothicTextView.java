package com.restaurant.milorad.isa_proj_android.views;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.restaurant.milorad.isa_proj_android.App;

/**
 * Created by Rade on 5/10/2015.
 */
public class CenturyGothicTextView extends TextView {

    public CenturyGothicTextView(Context context) {
        super(context);
        init(context);
    }

    public CenturyGothicTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CenturyGothicTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
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

