package com.example.administrator.helloandroid.pkg_location;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.example.administrator.helloandroid.R;

/**
 * Created by Administrator on 2015-05-12.
 */
public class GPS_exam3_CompassView extends View {

    private Drawable mCompass;
    private float mAzimuth = 0;
    private int PADDING = 2;
    private int T_PADDING = 20;

    public GPS_exam3_CompassView(Context context) {
        super(context);

        this.mCompass = context.getResources().getDrawable(R.drawable.arrow_n);
    }

    protected void onDraw(Canvas canvas) {
        canvas.save();

        canvas.rotate(360 - mAzimuth, PADDING + mCompass.getMinimumWidth()
                / 2, PADDING + mCompass.getMinimumHeight() / 2);
        mCompass.setBounds(PADDING, PADDING, PADDING
                + mCompass.getMinimumWidth(), PADDING
                + mCompass.getMinimumHeight());

        mCompass.draw(canvas);
        canvas.restore();

        super.onDraw(canvas);
    }

    public void setAzimuth(float aAzimuth) {
        mAzimuth = aAzimuth;
    }
}
