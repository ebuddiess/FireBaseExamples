package com.example.ebuddiess.introslider;

import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
ViewPager sliderViewPager;
LinearLayout dotLayer;
MPagerAdapter mPagerAdapter;
LinearLayout dotslayout;
ImageView[] dots;
Button btn_next,btn_skip;
public int[] layout = {
  R.layout.slide_layout_first,R.layout.slide_layout_second,R.layout.slide_layout_third
};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(Build.VERSION.SDK_INT>=19){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }else{
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        if(new PrefrenceManager(this).checkPrefrence()){

        }
        sliderViewPager = (ViewPager)findViewById(R.id.viewPager);
        mPagerAdapter = new MPagerAdapter(layout,this);
        sliderViewPager.setAdapter(mPagerAdapter);
        dotslayout = (LinearLayout)findViewById(R.id.dotslayout);
        createdot(0);
        btn_next=(Button)findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);
        btn_skip=(Button)findViewById(R.id.btn_skip);
        btn_skip.setOnClickListener(this);
        sliderViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
             createdot(position);
             if(position==(layout.length-1)){
                 btn_next.setText("START");
                 btn_skip.setVisibility(View.INVISIBLE);
             }else{
                 btn_next.setText("NEXT");
                 btn_skip.setVisibility(View.VISIBLE);
             }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    void createdot(int currentpos){
        if(dotslayout!=null){
            dotslayout.removeAllViews();
        }
        dots=new ImageView[layout.length];
        for(int i = 0;i<layout.length;i++){
            dots[i] = new ImageView(this);
            if(i==currentpos){
                dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.active_dots));
            }else{
                dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.inactive_dots));
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(4,0,6,0);
            dotslayout.addView(dots[i],params);
        }

    }

    @Override
    public void onClick(View view) {
switch (view.getId()){
    case R.id.btn_next:loadNextSLide();
        break;
    case R.id.btn_skip:loadHome();
        break;
}
    }

   void loadHome(){
        startActivity(new Intent(MainActivity.this,Welcome.class));
        new PrefrenceManager(this).WritePrefrence();
        finish();
    }

    void loadNextSLide(){
        int nextslide = sliderViewPager.getCurrentItem();
        if(nextslide>layout.length){
            sliderViewPager.setCurrentItem(nextslide);
        }else{
            loadHome();
            new PrefrenceManager(this).WritePrefrence();
        }
    }
}
