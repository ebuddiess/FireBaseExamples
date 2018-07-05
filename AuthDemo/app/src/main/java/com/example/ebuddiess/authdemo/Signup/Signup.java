package com.example.ebuddiess.authdemo.Signup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ebuddiess.authdemo.Login.MainActivity;
import com.example.ebuddiess.authdemo.R;
import com.example.ebuddiess.authdemo.User.UserData;
import com.example.ebuddiess.authdemo.User.WelcomeScreen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.util.Patterns.*;
import static android.widget.Toast.LENGTH_LONG;

public class Signup extends AppCompatActivity implements View.OnClickListener {
    Button signup_btn,login_btn;
    EditText email,pwd;
    UserData userData;
    DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_signup);
        signup_btn = (Button)findViewById(R.id.signup_btn);
        login_btn = (Button)findViewById(R.id.login_btn_from_signup);
        email = (EditText)findViewById(R.id.email_edt_signup);
        pwd=(EditText)findViewById(R.id.pwd_edt_signup);
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        signup_btn.setOnClickListener(this);
        login_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.login_btn_from_signup:startActivity(new Intent(Signup.this,MainActivity.class));
                break;
            case R.id.signup_btn:registerUser();
                break;
        }
    }

    private void registerUser() {
        String emailtxt,pwdtxt;
        emailtxt = email.getText().toString();
        pwdtxt =  pwd.getText().toString();
        if(emailtxt.isEmpty()||pwdtxt.isEmpty()){
            if(emailtxt.isEmpty()){
                email.setError("Empty Email Adresss");
            }else if(pwdtxt.isEmpty()){
                pwd.setError("Empty Password");
            }

        } else
            {
                firebaseAuth.createUserWithEmailAndPassword(emailtxt,pwdtxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                     if(task.isSuccessful()){
                         FirebaseUser user = firebaseAuth.getCurrentUser();
                         String email = user.getEmail();
                         String uid   = user.getUid();
                         userData = new UserData(email,uid);
                         databaseReference.child(uid).setValue(userData);
                         Toast.makeText(Signup.this,"user Registered Sucessfull", LENGTH_LONG).show();
                         finish();
                         startActivity(new Intent(Signup.this, WelcomeScreen.class));
                     }else {
                         if(task.getException() instanceof FirebaseAuthUserCollisionException){
                         Toast.makeText(Signup.this,"Failed To Register Duplicate Account", LENGTH_LONG).show();
                             }else {
                             Toast.makeText(Signup.this,task.getException().toString(), LENGTH_LONG).show();

                         }
                     }
                    }
                });
             }
    }
}
