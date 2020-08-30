package com.example.formula1.util;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;


public class FlagImageRatio extends AppCompatImageView {

    public FlagImageRatio(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        // The rule is that the ratio of the width of the flag to the height must be 19:10
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int desiredHeight = width * 19 / 10;

        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(desiredHeight, MeasureSpec.EXACTLY));
    }
}
