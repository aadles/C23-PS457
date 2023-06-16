package com.capstone.tastematch.presentation.home;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class MaskableFrameLayout extends FrameLayout {

    private final Path maskPath = new Path();
    private final RectF maskRect = new RectF();
    public MaskableFrameLayout(Context context) {
        super(context);
    }

    public MaskableFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MaskableFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        int count = canvas.save();
        canvas.clipPath(maskPath);
        super.dispatchDraw(canvas);
        canvas.restoreToCount(count);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        maskRect.set(0, 0, w, h);
        maskPath.reset();
        maskPath.addRoundRect(maskRect, 20f, 20f, Path.Direction.CW);
        maskPath.close();
    }
}

