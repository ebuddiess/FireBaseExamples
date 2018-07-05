package com.example.ebuddiess.authdemo.User;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ebuddiess.authdemo.Login.MainActivity;
import com.example.ebuddiess.authdemo.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class WelcomeScreen extends AppCompatActivity implements View.OnClickListener {
    Uri uri;
    FirebaseAuth mAuth;
    Task<Uri> imageurl;
     StorageReference mStorageRef;
    Button savedata,upload;
    DatabaseReference databaseReference;
    EditText firstname,lastname;
    ImageView imageView;
    String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        path = "profilepic/"+System.currentTimeMillis()+".jpeg";
        mStorageRef = FirebaseStorage.getInstance().getReference(path);
        savedata = (Button)findViewById(R.id.save);
        upload = (Button)findViewById(R.id.upload);
        firstname = (EditText)findViewById(R.id.firstname);
        lastname = (EditText)findViewById(R.id.lastname);
        imageView = (ImageView)findViewById(R.id.imageview);
        upload.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        savedata.setOnClickListener(this);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImagesChooser();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
           case R.id.save: saveUserData();break;
            case R.id.upload:uploadImage();break;
        }
    }

    private void uploadImage() {
        String displayname = firstname.getText().toString() + lastname.getText().toString();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null){
            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(displayname).setPhotoUri(Uri.parse(imageurl.toString())).build();
            user.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(WelcomeScreen.this,"Profile Updated",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(WelcomeScreen.this,task.getException().toString(),Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void saveUserData() {
        String uid = mAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(uid);
        String  fn =  firstname.getText().toString();
        String ln = lastname.getText().toString();
        String url = mAuth.getCurrentUser().getPhotoUrl().toString();
        String email = mAuth.getCurrentUser().getEmail();
        UserData userData = new UserData(email,fn,ln,uid,url);
        databaseReference.setValue(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(WelcomeScreen.this,"DATA UPDATED SUCESFULLY",Toast.LENGTH_LONG).show();
                loadImage();

            }
        });
     }

    public void loadImage(){
      if(mAuth.getCurrentUser().getPhotoUrl()!=null){
          Picasso.get().load(imageurl).into(imageView);
      }
    }

    private void showImagesChooser(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent,"CHOOSE A PROFILE PIC"),123);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==123&&resultCode==RESULT_OK && data!=null &&data.getData()!=null){
            uri = data.getData();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                uploadImageToFireBase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(WelcomeScreen.this,MainActivity.class));
        }
    }



    private void uploadImageToFireBase() {
        if (uri != null) {
            UploadTask uploadTask = mStorageRef.putFile(uri);
            Task<Task<Uri>> urltask = uploadTask.continueWith(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
               if(!task.isSuccessful()){
                   throw task.getException();
               }else{
                   Toast.makeText(WelcomeScreen.this,"Uploaded",Toast.LENGTH_LONG).show();
               }
               return mStorageRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Task<Uri>>() {
                @Override
                public void onComplete(@NonNull Task<Task<Uri>> task) {
                    if(task.isSuccessful()){
                        StorageReference url =  FirebaseStorage.getInstance().getReference().child(path);
                        imageurl = url.getDownloadUrl();
                    }else{
                        Toast.makeText(WelcomeScreen.this,task.getException().toString(),Toast.LENGTH_LONG).show();
                    }
                }
            });
    }}}


