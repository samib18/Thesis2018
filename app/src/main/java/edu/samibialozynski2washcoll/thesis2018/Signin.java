package edu.samibialozynski2washcoll.thesis2018;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;

public class Signin {

    final static String USER_AGENT = "Mozilla/5.0";
    static String loginFormUrl = "https://colleague-ss.washcoll.edu/Student/Account/Login?ReturnUrl=%2fStudent%2f";
    static String loginActionUrl = "https://colleague-ss.washcoll.edu/Student/Account/Login?ReturnUrl=%2fStudent%2f";
    static String afterloginURL = "https://colleague-ss.washcoll.edu/Student/Planning/DegreePlans";
    static String returnUrl = "/Student/";
    static String username = "sbialozynski2";
    static String password = "j0hns@mi18!";

    static Document doc = null;
    static Elements elements = null;

    static HashMap<String, String> cookies = new HashMap<>();
    static HashMap<String, String> formData = new HashMap<>();

    public static class DataGrabber extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            // NO CHANGES TO UI TO BE DONE HERE
            try {
                Connection.Response loginForm = Jsoup.connect(loginFormUrl)
                        .method(Connection.Method.GET)
                        .userAgent(USER_AGENT)
                        .execute();

                Document loginDoc = loginForm.parse(); // this is the document that contains response html

                cookies.putAll(loginForm.cookies()); // save the cookies, this will be passed on to next request
                /**
                 * Get the value of authenticity_token with the CSS selector we saved before
                 **/

                String authToken = loginDoc.select("#loginForm > input[type=\"hidden\"]:nth-child(1)")
                        .first()
                        .attr("value");

                formData.put("__RequestVerificationToken", authToken);
                formData.put("returnUrl", returnUrl);
                formData.put("UserName", username);
                formData.put("Password", password);

                Connection.Response homePage = Jsoup.connect(loginActionUrl)
                        .cookies(cookies)
                        .data(formData)
                        .method(Connection.Method.POST)
                        .userAgent(USER_AGENT)
                        .execute();

                Connection.Response schedule = Jsoup
                        .connect(afterloginURL)
                        .cookies(homePage.cookies())
                        .method(Connection.Method.GET)
                        .userAgent(USER_AGENT)
                        .execute();

                doc = Jsoup
                        .connect(afterloginURL)
                        .header("Accept-Encoding","gzip, deflate, ")
                        .header("Content-Type", "text/html; charset=utf-8")
                        .cookies(schedule.cookies())
                        .maxBodySize(0)
                        .timeout(600000)
                        .get();


            } catch (IOException e) {
                Log.e("Error in doInBackgroud()", e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            MainActivity.schedule.setText(doc.text());
        }
    }

}
