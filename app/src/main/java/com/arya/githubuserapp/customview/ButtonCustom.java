package com.arya.githubuserapp.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import com.arya.githubuserapp.R;

public class ButtonCustom extends AppCompatButton {

    private Drawable bg_button, bg_button_disable;
    private int text_color;

    public ButtonCustom(@NonNull Context context) {
        super(context);
        init();
    }

    public ButtonCustom(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ButtonCustom(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        setBackground(isEnabled() ? bg_button : bg_button_disable);
        setTextColor(text_color);
        setTextSize(12.f);
        setGravity(Gravity.CENTER);
//        setText(isEnabled() ? "Share" : "Isi Dulu");
    }

    private void init() {
        bg_button = getResources().getDrawable(R.drawable.bg_button);
        bg_button_disable = getResources().getDrawable(R.drawable.bg_button_disable);
        text_color = ContextCompat.getColor(getContext(), R.color.design_default_color_background);
    }
}
