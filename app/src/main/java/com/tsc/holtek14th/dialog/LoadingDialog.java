package com.tsc.holtek14th.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tsc.holtek14th.R;

public class LoadingDialog extends Dialog {

    String mMessage;
    boolean mCancelable;
    private ProgressBar progressBar;
    private TextView loadTextView;

    public LoadingDialog(Context context, String mMessage){//, int mImageId) {
        this(context,R.style.LoadingDialog,mMessage,false);
    }

    public LoadingDialog(Context context, int themeResId, String mMessage, boolean mCancelable) {
        super(context, themeResId);
        this.mMessage = mMessage;
        this.mCancelable = mCancelable;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setContentView(R.layout.loading_screen);
        WindowManager windowManager = getWindow().getWindowManager();
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
//        attributes.alpha = 0.8f;
        attributes.width=screenWidth/2;
        attributes.height=attributes.width;
        getWindow().setAttributes(attributes);
        setCancelable(mCancelable);
        loadTextView = findViewById(R.id.loading);
        loadTextView.setText(mMessage);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return mCancelable;
        }
        return super.onKeyDown(keyCode,event);
    }
}
