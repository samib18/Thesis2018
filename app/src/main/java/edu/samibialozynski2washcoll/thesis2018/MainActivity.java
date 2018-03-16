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
    Button elle;
    Button court;
    Button liza;
    Button mich;
    Button sab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sami = findViewById(R.id.samiButton);
        elle = findViewById(R.id.elleButton);
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

        elle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                schedule = findViewById(R.id.samiSchedule);
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
        Elements classTitle = null;
        Elements building = null;
        Elements dayOfWeek = null;
        Elements time = null;
        Elements classType = null;
        Elements numberOfClasses = null;

        ArrayList<String> data = new ArrayList<>();

        ArrayList<String> classTitleS = new ArrayList<>();
        ArrayList<String> dayOfWeekS = new ArrayList<>();
        ArrayList<String> timeS = new ArrayList<>();
        ArrayList<String> buildingS = new ArrayList<>();
        ArrayList<String> classTypeS = new ArrayList<>();

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

                    }else {
                        if (elements.select("#planned-meetings-region > div > div:nth-child(2) > div:nth-child(3) > span:nth-child(4)").contains("Lab"))
                        {
                            chunks.add(elements.getElementsByClass("schedule-listitem-header-title" + " Lab").text());
                            chunks.add(elements.select
                                    ("span[data-bind=text: ko.utils.unwrapObservable(DaysOfWeek)]").text());
                            chunks.add(elements.select
                                    ("span[data-bind=text: ko.utils.unwrapObservable(FormattedTime)]").text());
                            chunks.add(elements.select
                                    ("span[data-bind=text: ko.utils.unwrapObservable(MeetingLocation)]").text());
                        }else {
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

            /*classTitle = doc.getElementsByClass("schedule-listitem-header-title");
            dayOfWeek = doc.select
                    ("span[data-bind=text: ko.utils.unwrapObservable(DaysOfWeek)]");
            time  = doc.select
                    ("span[data-bind=text: ko.utils.unwrapObservable(FormattedTime)]");
            building = doc.select
                    ("span[data-bind=text: ko.utils.unwrapObservable(MeetingLocation)]");
            classType = doc.select
                    ("#planned-meetings-region > div > div:nth-child(2) > div:nth-child(3) > span:nth-child(4)");


            String thesis = null;

            for(Element element : classTitle) {
                if (element.text().contains("Senior Capstone Experience")) {
                    thesis = element.text();

                }else if(!element.text().contains("Senior Capstone Experience")) {

                    classTitleS.add(element.text());
                }
            }

            for(Element element2 : dayOfWeek) {
                dayOfWeekS.add(element2.text());
            }

            for(Element element3 : building) {
                buildingS.add(element3.text());
            }

            for(Element element4 : time){
                timeS.add(element4.text());
            }

            for(int i = 0; i < classTitleS.size(); i++){
                data.add(classTitleS.get(i));
                data.add(dayOfWeekS.get(i));
                data.add(timeS.get(i));
                data.add(buildingS.get(i));
            }

            data.add(thesis);*/

        }catch (IOException e){
            Log.d("Error", "someOtherMethod()");
        }
        return chunks;
    }
}
