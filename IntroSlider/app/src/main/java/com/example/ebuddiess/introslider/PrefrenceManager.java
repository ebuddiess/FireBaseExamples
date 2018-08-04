package com.example.ebuddiess.introslider;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefrenceManager  {
    Context context;
    SharedPreferences sharedPreferences;
    PrefrenceManager(Context context){
        this.context  = context;
        getSharedPreference();
    }
    private  void getSharedPreference(){
        sharedPreferences = context.getSharedPreferences("login_details",context.MODE_PRIVATE);
    }
    public void WritePrefrence(){
        sharedPreferences.edit().putString("initok","initok").commit();
    }
    public boolean checkPrefrence(){
        boolean status =false;
        if(sharedPreferences.getString("initok","").equals("null")){
            status = false;
        }else{
            status = true;
        }
        return status;
    }

    void clearPreference (){
        sharedPreferences.edit().clear().commit();
    }
}
