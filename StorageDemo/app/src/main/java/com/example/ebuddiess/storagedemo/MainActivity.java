package com.example.ebuddiess.storagedemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
ImageView img;
Uri uri;
Uri downloadUrl;
File localFile;
DatabaseReference databaseReference;
StorageReference storageReference;
TextView url;
String path;
SharedPreferences sp;
String imageName;
Button media,upload;

    @Override
    protected void onStart() {
        super.onStart();
        String path = getSharedPreferences("Imageurl",MODE_PRIVATE).getString("path","");
        if(path!=""){
            Toast.makeText(MainActivity.this,"DTA EXIST",Toast.LENGTH_LONG).show();
            Glide.with(MainActivity.this).load(path).into(img);
        }else{
            Toast.makeText(MainActivity.this,"DTA NOT EXIST",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==123&&resultCode==RESULT_OK && data!=null &&data.getData()!=null) {
            uri = data.getData();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img= (ImageView) findViewById(R.id.imgview);
        url = (TextView)findViewById(R.id.url);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"CHOOSE A PROFILE PIC"),123);
            }
        });



        imageName = System.currentTimeMillis()+".jpeg";
        upload= (Button)findViewById(R.id.uploadandretrive);
        databaseReference = FirebaseDatabase.getInstance().getReference("Images");
        storageReference = FirebaseStorage.getInstance().getReference("Images/"+imageName);
        upload.setOnClickListener(this);
        }


    @Override
    public void onClick(View view) {

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                      downloadUrl = uri;
//                        try {
//                            localFile = File.createTempFile("images","jpg");
//                            storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                                @Override
//                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                                    Toast.makeText(MainActivity.this, "File Saved Into Memory", Toast.LENGTH_SHORT).show();
//                                    Glide.with(MainActivity.this).load(localFile).into(img);
//                                    path = localFile.getPath();
//                                    sp = getSharedPreferences("Imageurl",MODE_PRIVATE);
//                                    sp.edit().putString("path",path).apply();
//                                 }
//                            });
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
                      Glide.with(MainActivity.this).load(uri).into(img);
                    }
                });
                Toast.makeText(MainActivity.this,"Uploading is Done",Toast.LENGTH_LONG).show();
             }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,e.getMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
