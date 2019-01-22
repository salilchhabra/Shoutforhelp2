package com.example.salil.shoutforhelp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class splash extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView i1=(ImageView)findViewById(R.id.imageView1);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
              Intent intent=new Intent(getApplicationContext(),MainActivity.class);
              startActivity(intent);

            }
        },2000);

    }
}
