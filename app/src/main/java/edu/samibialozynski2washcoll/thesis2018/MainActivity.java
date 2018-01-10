package edu.samibialozynski2washcoll.thesis2018;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    Button sami;
    Button elle;
    Button court;
    Button liza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sami = findViewById(R.id.samiButton);
        elle = findViewById(R.id.elleButton);
        court = findViewById(R.id.courtButton);
        liza = findViewById(R.id.lizaButton);

        sami.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*InputStream stream = getResources().openRawResource(R.raw.samispringschedule);
                InputStreamReader inputreader = new InputStreamReader(stream);
                BufferedReader buffreader = new BufferedReader(inputreader);
                String line;
                StringBuilder text = new StringBuilder();

                try {
                    while((line = buffreader.readLine()) != null) {
                        text.append(line);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }*/

                TextView schedule = findViewById(R.id.samiSchedule);
                schedule.setText(getSchedule());

                //Intent myIntent = new Intent(MainActivity.this,
                        //Schedule.class);
                //myIntent.putExtra("Schedule", text.toString());
                //startActivity(myIntent);

            }
        });

        elle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView schedule = findViewById(R.id.samiSchedule);
                schedule.setText(getSchedule());
            }
        });

        court.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView schedule = findViewById(R.id.samiSchedule);
                schedule.setText(getSchedule());
            }
        });

        liza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView schedule = findViewById(R.id.samiSchedule);
                schedule.setText(getSchedule());
            }
        });

    }
    public String getSchedule(){
        InputStream stream = null;

        if(sami.isEnabled()){
            stream = getResources().openRawResource(R.raw.samispringschedule);
        } else if (elle.isEnabled()){
            stream = getResources().openRawResource(R.raw.ellespringschedule);
        } else if (court.isEnabled()){
            stream = getResources().openRawResource(R.raw.courtneyspringschedule);
        }else if (liza.isEnabled()){
            stream = getResources().openRawResource(R.raw.lizaspringschedule);
        }else{
            Toast toast = Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG);
            toast.show();
        }

        InputStreamReader inputreader = new InputStreamReader(stream);
        BufferedReader buffreader = new BufferedReader(inputreader);
        String line;
        StringBuilder text = new StringBuilder();

        try {
            while((line = buffreader.readLine()) != null) {
                text.append(line);

            }
        }catch (IOException e){
            e.printStackTrace();
        }

        return text.toString();
    }
}
