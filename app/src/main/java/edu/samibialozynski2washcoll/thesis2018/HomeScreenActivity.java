 package edu.samibialozynski2washcoll.thesis2018;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;


public class HomeScreenActivity extends AppCompatActivity {

    TextView title;
    ImageView logo;
    Typeface type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        title = findViewById(R.id.title);
        logo = findViewById(R.id.imageDisplay);


        title.setText(R.string.app_name);
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 60f);
        title.setTextColor(getResources().getColor(R.color.textColor));

        logo.setImageResource(R.drawable.mapgoose);

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent intent = new Intent(HomeScreenActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }, 2000L);
    }
}
