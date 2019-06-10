package com.tsc.holtek14th.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.tsc.holtek14th.R;

public class LoadingDialog extends Dialog {

    String mMessage;
    boolean mCancelable;
    private ImageView loadImageView;
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

        loadImageView = findViewById(R.id.star);
        loadTextView = findViewById(R.id.loading);

        loadTextView.setText(mMessage);

//        loadImageView.measure(0,0);

        AnimationSet mImageAni = new AnimationSet(true), mTextAni = new AnimationSet(true);
        TranslateAnimation ta = new TranslateAnimation(0,0,0,attributes.height*0.4f);
        ta.setDuration(5000);
        ta.setRepeatCount(Animation.INFINITE);

        RotateAnimation ra = new RotateAnimation(0,360*5,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        ra.setDuration(5000);
        ra.setRepeatCount(Animation.INFINITE);

        ScaleAnimation saImg = new ScaleAnimation(0,1.5f,0,1.5f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        saImg.setDuration(5000);
        saImg.setRepeatCount(Animation.INFINITE);

        ScaleAnimation saText = new ScaleAnimation(1,1.5f,1,1.5f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        saText.setDuration(5000);
//        saText.setRepeatCount(Animation.INFINITE);

        AlphaAnimation aa = new AlphaAnimation(0,1);
        aa.setDuration(5000);
        aa.setRepeatCount(-1);

        mImageAni.addAnimation(ta);
        mImageAni.addAnimation(ra);
//        mImageAni.addAnimation(aa);
        mImageAni.addAnimation(saImg);

        mTextAni.addAnimation(saText);
//        mTextAni.addAnimation(aa);
        loadImageView.startAnimation(mImageAni);
        loadTextView.startAnimation(mTextAni);
    }

    @Override
    public void dismiss() {
//        mRotateAnimation.cancel();
        loadImageView.clearAnimation();
        loadTextView.clearAnimation();
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
