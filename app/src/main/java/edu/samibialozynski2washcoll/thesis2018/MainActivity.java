package edu.samibialozynski2washcoll.thesis2018;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView schedule;
    ArrayList<String> classList;
    Button sami;
    Button elle;
    Button court;
    Button liza;
    ArrayAdapter<String> listAdapter ;

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

            }
        });

        elle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                schedule = findViewById(R.id.samiSchedule);
                //schedule.setAdapter(getSchedule());
            }
        });

        court.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                schedule = findViewById(R.id.samiSchedule);
                //schedule.setAdapter(getSchedule());
            }
        });

        liza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                schedule = findViewById(R.id.samiSchedule);
                //schedule.setAdapter(getSchedule());
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
        String htmlSch = null;



       classList = new ArrayList<>();
       String classHtmlSection = null;

       Document doc = null;

        try {
            while((line = buffreader.readLine()) != null) {

                text.append(line);
                classHtmlSection = text.toString();

                //doc = Jsoup.parse(htmlSch);
                //classHtmlSection = doc.text();
            }

        }catch (IOException e){
            Log.d("Error", "someOtherMethod()");
        }

        return classHtmlSection;
    }
}
