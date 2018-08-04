package com.example.ebuddiess.customtoast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1=findViewById(R.id.show);
       b1.setOnClickListener(this);
    }

    public void show(View view) {
        LayoutInflater li=getLayoutInflater();
        View custom_toast = li.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.layout_toast));
        Toast t1 = new Toast(MainActivity.this);
        t1.setGravity(Gravity.CENTER,0,0);
        t1.setView(custom_toast);
        t1.show();
    }

    @Override
    public void onClick(View view) {
        show(view);
    }
}
