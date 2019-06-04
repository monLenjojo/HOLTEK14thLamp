package com.tsc.holtek14th;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tsc.holtek14th.javaBean.UserDataFormat;

public class RegisterActivity extends AppCompatActivity {

    private EditText edName;
    private EditText edEmail;
    private EditText edPasswd;
    private CheckBox ckPersonal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edName = findViewById(R.id.edName);
        edEmail = findViewById(R.id.edEmail);
        edPasswd = findViewById(R.id.edPasswd);
        ckPersonal = findViewById(R.id.ckPersonal);
    }

    public void createUser(View view) {
        ckPersonal.setError(null);
        if (ckPersonal.isChecked()) {
            String email = edEmail.getText().toString();
            String passwd = edPasswd.getText().toString();
            final AlertDialog dialog = new AlertDialog.Builder(this).setMessage("registering...").setCancelable(false).show();
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, passwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        String userName = edName.getText().toString();
                        final FirebaseUser user = task.getResult().getUser();
                        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                                .setDisplayName(userName).build();
                        user.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                        DocumentReference firestore = FirebaseFirestore.getInstance()
                                .collection("userData")
                                .document(user.getUid());
                        firestore.set(new UserDataFormat(user.getDisplayName(),user.getEmail()))
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            user.sendEmailVerification();
                                            dialog.cancel();
                                            new AlertDialog.Builder(RegisterActivity.this).setMessage("註冊成功，自動登入中...").setCancelable(false).show();
                                            finish();
                                            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_in_left);
                                        }
                                    }
                                });
                            }
                        });
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dialog.cancel();
                    new AlertDialog.Builder(RegisterActivity.this).setMessage("User repeat, sign up fail").setPositiveButton("OK",null).show();
                }
            });
        }else{
            ckPersonal.setError("need agree this");
        }
    }
}
