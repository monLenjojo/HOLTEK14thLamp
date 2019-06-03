package com.tsc.holtek14th.Facebook;
//implementation 'com.facebook.android:facebook-android-sdk:[4,5)' 要先引入SDK與其他設定

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FacebookLogin {

    private final String TAG = FacebookLogin.class.getSimpleName();
    Context context;
    FirebaseAuth auth;
    LoginButton loginButton;
    public CallbackManager callbackManager;

    public FacebookLogin(Context context, FirebaseAuth auth, LoginButton loginButton) {
        this.context = context;
        this.auth = auth;
        this.loginButton = loginButton;
//        FacebookSdk.sdkInitialize(FacebookSdk.getApplicationContext());
//        AppEventsLogger.activateApp(context);
    }


    public void start() {
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken token = loginResult.getAccessToken();
                AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
                auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            Log.d(TAG, "onComplete: " + user.getUid() + "\\" + user.getDisplayName() + "\\" + user.getEmail());

                        }else{
                            Log.d(TAG, "onComplete: " + "login is Fail");
                        }

                    }
                });
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        Profile profile = Profile.getCurrentProfile();
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    auth.signOut();
                    Log.d(TAG, "onCurrentAccessTokenChanged: is sign out");
                }
            }
        };
    }


    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }*/
}
