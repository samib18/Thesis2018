package edu.samibialozynski2washcoll.thesis2018;

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
                String data = null;
                new Signin.DataGrabber().execute();


                /*Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                intent.putExtra("extra", getSchedule());
                startActivity(intent);*/
            }
        });

        elle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                schedule = findViewById(R.id.samiSchedule);
                //schedule.setText(getSchedule());
            }
        });

        court.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                schedule = findViewById(R.id.samiSchedule);
                //schedule.setText(getSchedule());
            }
        });

        liza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                schedule = findViewById(R.id.samiSchedule);
                //schedule.setText(getSchedule());
            }
        });
    }

    public ArrayList<String> getSchedule(){
        InputStream stream = null;

        if(sami.isEnabled()){
            stream = getResources().openRawResource(R.raw.samispringschedule);
        }

        /*if(elle.isEnabled()){
            stream = getResources().openRawResource(R.raw.ellespringschedule);
        }*/

        /*if(court.isEnabled()){
            stream = getResources().openRawResource(R.raw.courtneyspringschedule);
        }*/

        /*if (liza.isEnabled()){
            stream = getResources().openRawResource(R.raw.lizaspringschedule);
        }*/

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

        String classesStr = null;
        String dayOfWeekStr = null;
        String placesStr = null;

        ArrayList<String> data = new ArrayList<>();

        ArrayList<String> classTitleS = new ArrayList<>();
        ArrayList<String> dayOfWeekS = new ArrayList<>();
        ArrayList<String> timeS = new ArrayList<>();
        ArrayList<String> buildingS = new ArrayList<>();
        ArrayList<String> classTypeS = new ArrayList<>();

        ArrayList<String> chunks = new ArrayList<>();

        String schedule = "esg-card schedule-listitem";
        int count = 0;

        try {
            while((line = buffreader.readLine()) != null) {

                text.append(line);
                classHtmlSection = text.toString();
            }

            Document doc = Jsoup.parse(classHtmlSection);


            numberOfClasses = doc.select("li[class=esg-card schedule-listitem]");

            for(Element elements : numberOfClasses){
                if(elements.text().contains("Registered")) {

                    chunks.add(elements.getElementsByClass("schedule-listitem-header-title").text());
                    chunks.add(elements.select
                            ("span[data-bind=text: ko.utils.unwrapObservable(DaysOfWeek)]").text());
                    chunks.add(elements.select
                            ("span[data-bind=text: ko.utils.unwrapObservable(FormattedTime)]").text());
                    chunks.add(elements.select
                            ("span[data-bind=text: ko.utils.unwrapObservable(MeetingLocation)]").text());

                }
            }

            classTitle = doc.getElementsByClass("schedule-listitem-header-title");
            dayOfWeek = doc.select
                    ("span[data-bind=text: ko.utils.unwrapObservable(DaysOfWeek)]");
            time  = doc.select
                    ("span[data-bind=text: ko.utils.unwrapObservable(FormattedTime)]");
            building = doc.select
                    ("span[data-bind=text: ko.utils.unwrapObservable(MeetingLocation)]");
            classType = doc.select
                    ("div[aria-label=Meeting Location]");


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

            data.add(thesis);

        }catch (IOException e){
            Log.d("Error", "someOtherMethod()");
        }

        return data;
    }
}
