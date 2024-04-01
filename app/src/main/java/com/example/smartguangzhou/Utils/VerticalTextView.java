package com.example.smartguangzhou.Utils;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class VerticalTextView extends androidx.appcompat.widget.AppCompatTextView{
    public VerticalTextView(Context context) {
        super(context);
    }

    public VerticalTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(0, getHeight());
        canvas.rotate(-90);
        super.onDraw(canvas);
        canvas.restore();
    }
}
