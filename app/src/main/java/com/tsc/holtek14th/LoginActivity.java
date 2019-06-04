package com.tsc.holtek14th;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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
import com.tsc.holtek14th.Facebook.FacebookLogin;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
//    private static final String FBTAG = LoginActivity.class.getSimpleName()+" Facebook-auth";
    private FirebaseAuth auth;
    private EditText edUserId;
    private EditText edPasswd;
    private Boolean state;
    private FacebookLogin facebookLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();

        edUserId = findViewById(R.id.edUserId);
        edPasswd = findViewById(R.id.edPasswd);

        LoginButton loginButton = findViewById(R.id.login_button);
        facebookLogin = new FacebookLogin(this,auth,loginButton);
        facebookLogin.start();

        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        facebookLogin.callbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void login(View view){
        state = true;
        edPasswd.setError(null);
        String userId = edUserId.getText().toString();
        String passwd = edPasswd.getText().toString();

        if (passwd.length() <6) {
            edPasswd.setError("密碼需大於6位");
            state = false;
        }

        if (state) {
            auth.signInWithEmailAndPassword(userId, passwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isComplete()) {
                        if (task.isSuccessful()) {
                            FirebaseUser userAuth = task.getResult().getUser();
                            setUserDataSharedPreferences(userAuth);
//                            Log.d(TAG, "signInWithEmailAndPassword: "+ task.getResult().getUser().getEmail() + "--Successful");
                        } else {
                            new AlertDialog.Builder(LoginActivity.this)
                                    .setTitle("登入狀態")
                                    .setMessage("錯誤")
                                    .setNeutralButton("加入會員", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent registerPage = new Intent(LoginActivity.this, RegisterActivity.class);
                                            startActivity(registerPage);
                                        }
                                    })
                                    .setPositiveButton("好", null)
                                    .show();
                        }
                    }
                }
            });
        }
    }

    public void register(View view){
        Intent registerPage = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(registerPage);
    }

    public void setUserDataSharedPreferences(FirebaseUser userAuth){
        getSharedPreferences("userData", MODE_PRIVATE).edit()
                .putString("NAME", userAuth.getDisplayName())
                .putString("EMAIL", userAuth.getEmail())
//                .putString("PHOTO", userAuth.getPhotoUrl().toString())
                .commit();
    }
}
