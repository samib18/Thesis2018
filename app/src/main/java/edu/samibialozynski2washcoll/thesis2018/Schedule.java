package edu.samibialozynski2washcoll.thesis2018;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Schedule extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        TextView scheduleView = findViewById(R.id.scheduleView);
        scheduleView.setText(getIntent().getExtras().getString("Schedule"));

    }
}
