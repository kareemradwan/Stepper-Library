package com.kradwan.stepeer.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;

/**
 * This Class From Stackoverflow Website
 */
public class RoundedCornerLayout extends FrameLayout {


    public RoundedCornerLayout(Context context) {
        this(context, null, 0);
    }

    public RoundedCornerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundedCornerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    public double getCornerRadius() {
        return 20;
    }

    @Override
    public void draw(Canvas canvas) {
        int count = canvas.save();

        final Path path = new Path();
        path.addRoundRect(new RectF(0, 0, getWidth(), getHeight()), (float) getCornerRadius(), (float) getCornerRadius(), Path.Direction.CW);
        canvas.clipPath(path);

        canvas.clipPath(path);
        super.draw(canvas);
        canvas.restoreToCount(count);
    }
}