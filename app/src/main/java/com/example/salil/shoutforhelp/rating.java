package com.example.salil.shoutforhelp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

public class rating extends AppCompatActivity {
    public void getrating(View view)
    {
        RatingBar r1=(RatingBar)findViewById( R.id.ratingBar);
        String rating=String.valueOf(r1.getRating());
        Toast.makeText(this, rating, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
    }
}
