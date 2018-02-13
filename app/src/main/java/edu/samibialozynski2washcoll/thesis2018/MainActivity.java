package edu.samibialozynski2washcoll.thesis2018;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    TextView schedule;
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

                schedule = findViewById(R.id.samiSchedule);
                schedule.setText(getSchedule());

                startActivity(new Intent(MainActivity.this, MapsActivity.class));
            }
        });

        elle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                schedule = findViewById(R.id.samiSchedule);
                schedule.setText(getSchedule());
            }
        });

        court.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                schedule = findViewById(R.id.samiSchedule);
                schedule.setText(getSchedule());
            }
        });

        liza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                schedule = findViewById(R.id.samiSchedule);
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

        String classHtmlSection = null;
        Elements classes = null;
        Element places = null;

        try {
            while((line = buffreader.readLine()) != null) {

                text.append(line);
                classHtmlSection = text.toString();
            }

            Document doc = Jsoup.parse(classHtmlSection);
            classes = doc.getElementsByClass("schedule-listitem-header-title");
            places = doc.getElementById("text: ko.utils.unwrapObservable(MeetingLocation)");

        }catch (IOException e){
            Log.d("Error", "someOtherMethod()");
        }
   
        return classes.text();
    }
}
