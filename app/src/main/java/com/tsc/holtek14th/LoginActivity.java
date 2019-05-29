package com.tsc.holtek14th;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText edUserId;
    private EditText edPasswd;
    private Boolean state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        edUserId = findViewById(R.id.edUserId);
        edPasswd = findViewById(R.id.edPasswd);
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
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            new AlertDialog.Builder(LoginActivity.this)
                                    .setTitle("登入狀態")
                                    .setMessage("錯誤")
                                    .setNeutralButton("加入會員", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
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

    public void quit(View view){
        finish();
    }
}
