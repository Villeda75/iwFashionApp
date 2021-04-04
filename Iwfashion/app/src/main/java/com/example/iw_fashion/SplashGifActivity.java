package com.example.iw_fashion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;





public class SplashGifActivity extends AppCompatActivity {
    ImageView gif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_gif);

        gif = findViewById(R.id.gif);

        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/gifiwfashion.appspot.com/o/gifprueba.gif?alt=media&token=281a1958-db90-4a68-a928-304d86f3c3af").asGif().diskCacheStrategy(DiskCacheStrategy.ALL).fitCenter().into(gif);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashGifActivity.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                //Finish
                finish();

            }
        }, 2000);

    }

}