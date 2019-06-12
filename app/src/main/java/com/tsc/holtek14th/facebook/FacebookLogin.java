package com.tsc.holtek14th.facebook;
//implementation 'com.facebook.android:facebook-android-sdk:[4,5)' 要先引入SDK與其他設定

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tsc.holtek14th.R;
import com.tsc.holtek14th.dialog.LoadingDialog;
import com.tsc.holtek14th.javaBean.UserDataFormat;

import java.util.regex.Pattern;

import static android.content.Context.MODE_PRIVATE;

public class FacebookLogin {

    private final String TAG = FacebookLogin.class.getSimpleName();
    Context context;
    FirebaseAuth auth;
    LoginButton loginButton;
    public CallbackManager callbackManager;
    private final LoadingDialog loadingDialog;

    public FacebookLogin(Context context, FirebaseAuth auth, LoginButton loginButton) {
        this.context = context;
        this.auth = auth;
        this.loginButton = loginButton;
        loadingDialog = new LoadingDialog(context, R.style.LoadingDialog,"Loading...",false);
    }


    public void start() {
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                loadingDialog.show();
                AccessToken token = loginResult.getAccessToken();
                AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
                auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            if (task.getResult().getAdditionalUserInfo().isNewUser()) {
                                addUserData(user);
                            }else{
                                setUserDataSharedPreferences(auth.getCurrentUser());
                            }
                            Log.d(TAG, user.getUid() + "\\" + user.getDisplayName() + "\\" + user.getEmail());
                        }else{
                            Log.d(TAG,  "login is Fail"+ task.getException().toString());
                        }
                        loadingDialog.dismiss();
                    }
                });
            }

            @Override
            public void onCancel() {
                loadingDialog.dismiss();
            }

            @Override
            public void onError(FacebookException error) {
                loadingDialog.dismiss();
                new AlertDialog.Builder(context).setTitle("Error Message").setMessage(error.getMessage()).show();
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

    public void addUserData(FirebaseUser user) {
        DocumentReference base = FirebaseFirestore.getInstance().collection("userData").document(user.getUid());
        base.set(new UserDataFormat(user.getDisplayName(), user.getEmail(), user.getPhotoUrl().toString()))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            setUserDataSharedPreferences(auth.getCurrentUser());
                        }
                    }
                });
    }

    public void setUserDataSharedPreferences(FirebaseUser userAuth){
        context.getSharedPreferences("userData", MODE_PRIVATE).edit()
                .putString("NAME", userAuth.getDisplayName())
                .putString("EMAIL", userAuth.getEmail())
                .putString("PHOTO", userAuth.getPhotoUrl().toString())
//                .putString("PHOTO", Profile.getCurrentProfile().getProfilePictureUri(300,300).toString())
                .commit();
    }
}
