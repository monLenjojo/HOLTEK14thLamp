package com.tsc.holtek14th;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tsc.holtek14th.dialog.LoadingDialog;
import com.tsc.holtek14th.facebook.FacebookLogin;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
//    private static final String FBTAG = LoginActivity.class.getSimpleName()+" Facebook-auth";
    private FirebaseAuth auth;
    private EditText edUserId;
    private EditText edPasswd;
    private Boolean state;
    private FacebookLogin facebookLogin;
    private TextInputLayout edPasswdLayout;
    private TextInputLayout edUserIdLayout;
    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();

        edUserIdLayout = findViewById(R.id.edUserIdLayout);
        edPasswdLayout = findViewById(R.id.edPasswdLayout);

        edUserId = findViewById(R.id.edUserId);
        edPasswd = findViewById(R.id.edPasswd);

        LoginButton loginButton = findViewById(R.id.login_button);
        facebookLogin = new FacebookLogin(this,auth,loginButton);
        facebookLogin.start();
        constraintLayout = findViewById(R.id.loginConstraint);

        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    setResult(RESULT_OK);
                    finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);
                }
            }
        });

        new LoadingDialog(this,R.style.LoadingDialog,"loading...",true).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        facebookLogin.callbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void login(View view){
        state = true;
        View focusView = null;
        String userId = edUserId.getText().toString();
        String passwd = edPasswd.getText().toString();
        if (!Pattern.compile("^(?=.*\\d)(?=.*[a-zA-Z]).{6,12}$").matcher(passwd).matches()) {
            edPasswd.setError("Password need use english and digital in 6 to 12");
            focusView = edPasswd;
            state = false;
        }

        if (!Pattern.compile("[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.+[a-zA-Z]{2,4}$").matcher(userId).matches()) {
            edUserId.setError("Invalid email");
            focusView = edUserId;
            state=false;
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
                                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);
                                        }
                                    })
                                    .setPositiveButton("好", null)
                                    .show();
                        }
                    }
//                    loading.setVisibility(View.GONE);
                }
            });
        }else{
            focusView.requestFocus();
        }
    }

    public void register(View view){
        Intent registerPage = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(registerPage);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);
    }

    public void setUserDataSharedPreferences(FirebaseUser userAuth){
        getSharedPreferences("userData", MODE_PRIVATE).edit()
                .putString("NAME", userAuth.getDisplayName())
                .putString("EMAIL", userAuth.getEmail())
//                .putString("PHOTO", userAuth.getPhotoUrl().toString())
                .commit();
    }

}
