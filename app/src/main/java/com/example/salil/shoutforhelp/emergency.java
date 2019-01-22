package com.example.salil.shoutforhelp;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class emergency extends AppCompatActivity {
    Button b1,b2,b3,b4,b5,b6,b7,b8;
public void phone(View view)
{
    int tag=Integer.parseInt(view.getTag().toString());
    Intent intent = new Intent(Intent.ACTION_CALL);
    if(tag==0)
    intent.setData(Uri.parse("tel:" + "112"));
    if(tag==1)
        intent.setData(Uri.parse("tel:" + "100"));
    if(tag==2)
        intent.setData(Uri.parse("tel:" + "101"));
    if(tag==3)
        intent.setData(Uri.parse("tel:" + "102"));
    if(tag==4)
        intent.setData(Uri.parse("tel:" + "1906"));
    if(tag==5)
        intent.setData(Uri.parse("tel:" + "181"));
    if(tag==6)
        intent.setData(Uri.parse("tel:" + "1322"));
    if(tag==7)
        intent.setData(Uri.parse("tel:" + "108"));

    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        return;
    }
    startActivity(intent);
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
         b1=(Button)findViewById(R.id.b1);
         b2=(Button)findViewById(R.id.button_2);
         b3=(Button)findViewById(R.id.button_3);
         b4=(Button)findViewById(R.id.button);
         b5=(Button)findViewById(R.id.image);
         b6=(Button)findViewById(R.id.custom);
        b7=(Button)findViewById(R.id.imageView);
        b8=(Button)findViewById(R.id.text);
    }
}
