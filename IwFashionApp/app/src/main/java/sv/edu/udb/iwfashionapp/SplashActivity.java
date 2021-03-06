package sv.edu.udb.iwfashionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class SplashActivity extends AppCompatActivity {

    ImageView gif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        gif = findViewById(R.id.gif);

        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/gifiwfashion.appspot.com/o/gifprueba.gif?alt=media&token=281a1958-db90-4a68-a928-304d86f3c3af").diskCacheStrategy(DiskCacheStrategy.ALL).fitCenter().into(gif);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, Login.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                //Finish
                finish();

            }
        }, 2000);

    }
}