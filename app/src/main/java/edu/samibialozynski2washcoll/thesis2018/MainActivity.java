package edu.samibialozynski2washcoll.thesis2018;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static TextView schedule;
    InputStream stream = null;
    Button sami;
    Button court;
    Button liza;
    Button mich;
    Button sab;
    String thesis = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sami = findViewById(R.id.samiButton);
        court = findViewById(R.id.courtButton);
        liza = findViewById(R.id.lizaButton);
        mich = findViewById(R.id.michButton);
        sab = findViewById(R.id.sabButton);

        sami.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                schedule = findViewById(R.id.samiSchedule);
                //new Signin.DataGrabber().execute();

                stream = getResources().openRawResource(R.raw.samispringschedule);

                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                intent.putExtra("extra", getSchedule());
                startActivity(intent);
            }
        });

        court.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stream = getResources().openRawResource(R.raw.courtneyspringschedule);

                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                intent.putExtra("extra", getSchedule());
                startActivity(intent);
            }
        });

        liza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stream = getResources().openRawResource(R.raw.lizaspringschedule);

                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                intent.putExtra("extra", getSchedule());
                startActivity(intent);
            }
        });

        mich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stream = getResources().openRawResource(R.raw.michaelaspringschedule);

                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                intent.putExtra("extra", getSchedule());
                startActivity(intent);
            }
        });

        sab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stream = getResources().openRawResource(R.raw.sabrinaspringschedule);

                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                intent.putExtra("extra", getSchedule());
                startActivity(intent);
            }
        });
    }

    public ArrayList<String> getSchedule(){

        InputStreamReader inputreader = new InputStreamReader(stream);
        BufferedReader buffreader = new BufferedReader(inputreader);
        String line;
        StringBuilder text = new StringBuilder();

        String classHtmlSection = null;
        Elements numberOfClasses = null;

        ArrayList<String> chunks = new ArrayList<>();

        try {
            while((line = buffreader.readLine()) != null) {

                text.append(line);
                classHtmlSection = text.toString();
            }

            Document doc = Jsoup.parse(classHtmlSection);

            numberOfClasses = doc.select("li[class=esg-card schedule-listitem]");

            for(Element elements : numberOfClasses){
                if(elements.text().contains("Registered")) {

                    if(elements.text().contains("Senior Capstone Experience"))
                    {
                        thesis = elements.text();
                    }else {
                        //If the class has a lab section
                        if (elements.select("#planned-meetings-region > div > div:nth-child(2) > div:nth-child(3) > span:nth-child(4)")
                                .text().contains("Lab"))
                        {
                            //Lecture details class name, DOW, time of day, Meeting location
                            chunks.add(elements.getElementsByClass("schedule-listitem-header-title").text() + ": Lecture Details");
                            chunks.add(elements.select
                                    ("#planned-meetings-region > div > div:nth-child(1) > div:nth-child(1) > span:nth-child(2)").text());
                            chunks.add(elements.select
                                    ("#planned-meetings-region > div > div:nth-child(1) > div:nth-child(1) > span:nth-child(3)").text());
                            chunks.add(elements.select
                                    ("#planned-meetings-region > div > div:nth-child(1) > div:nth-child(3) > span:nth-child(3)").text());

                            //Lab details class name, DOW, time of day, Meeting location
                            chunks.add(elements.getElementsByClass("schedule-listitem-header-title").text() + ": Lab Details");
                            chunks.add(elements.select
                                    ("#planned-meetings-region > div > div:nth-child(2) > div:nth-child(1) > span:nth-child(2)").text());
                            chunks.add(elements.select
                                    ("#planned-meetings-region > div > div:nth-child(2) > div:nth-child(1) > span:nth-child(3)").text());
                            chunks.add(elements.select
                                    ("#planned-meetings-region > div > div:nth-child(2) > div:nth-child(3) > span:nth-child(3)").text());
                        }else {
                            //No lab section just normal class
                            chunks.add(elements.getElementsByClass("schedule-listitem-header-title").text());
                            chunks.add(elements.select
                                    ("span[data-bind=text: ko.utils.unwrapObservable(DaysOfWeek)]").text());
                            chunks.add(elements.select
                                    ("span[data-bind=text: ko.utils.unwrapObservable(FormattedTime)]").text());
                            chunks.add(elements.select
                                    ("span[data-bind=text: ko.utils.unwrapObservable(MeetingLocation)]").text());
                        }
                    }
                }
            }

        }catch (IOException e){
            Log.d("Error in getSchedule()", e.toString());
        }

        Log.d("Error in getSchedule()", chunks.toString());
        return chunks;
    }
}
