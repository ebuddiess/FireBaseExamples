package com.example.ebuddiess.authdemo.Login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ebuddiess.authdemo.R;
import com.example.ebuddiess.authdemo.Signup.Signup;
import com.example.ebuddiess.authdemo.User.WelcomeScreen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
  Button login_btn,signup_btn;
  EditText email,pwd;
  ProgressBar progressBar;
  FirebaseAuth firebaseAuth;
  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login_btn = (Button)findViewById(R.id.login_btn);
        firebaseAuth =  FirebaseAuth.getInstance();
        signup_btn = (Button)findViewById(R.id.btn_signup_from_login);
        email = (EditText)findViewById(R.id.email_edt);
        pwd=(EditText)findViewById(R.id.pwd_edt);
        progressBar = (ProgressBar)findViewById(R.id.progressabr);
        login_btn.setOnClickListener(this);
        signup_btn.setOnClickListener(this);
  }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(MainActivity.this,WelcomeScreen.class));
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.login_btn:loginuser();
                break;
            case R.id.btn_signup_from_login:finish(); startActivity(new Intent(MainActivity.this,Signup.class));
                break;
        }
    }

    private void loginuser() {
        String emailtxt,pwdtxt;
        emailtxt = email.getText().toString();
        pwdtxt =  pwd.getText().toString();
        if(emailtxt.isEmpty()||pwdtxt.isEmpty()){
            if(emailtxt.isEmpty()){
                email.setError("Empty Email Adresss");
            }else if(pwdtxt.isEmpty()){
                pwd.setError("Empty Password");
            }

        } else{
          progressBar.setVisibility(View.VISIBLE);
          firebaseAuth.signInWithEmailAndPassword(emailtxt,pwdtxt).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
             @Override
             public void onComplete(@NonNull Task<AuthResult> task) {
             if(task.isSuccessful()){
                 finish();
                 Intent i1 = new Intent(MainActivity.this, WelcomeScreen.class);
                 i1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                 startActivity(i1);
                 progressBar.setVisibility(View.INVISIBLE);
             }else{
                 Toast.makeText(MainActivity.this,task.getException().toString(), LENGTH_LONG).show();
             }
             }
         });
        }

  }
}
