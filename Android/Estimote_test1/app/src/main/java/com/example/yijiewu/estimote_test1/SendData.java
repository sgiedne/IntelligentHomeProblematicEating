package com.example.yijiewu.estimote_test1;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.Date;
import 	java.text.SimpleDateFormat;
import java.util.Locale;

import static android.R.attr.format;

/**
 * Created by yijiewu on 2/12/17.
 */

public class SendData extends AsyncTask<Object, Object, Void> {
    @Override
    protected Void doInBackground(Object... params) {

        int status=MyApplication.getVariable();
        Log.d("send data", "IN THE STORE TO BACKEND LOOP");

        OutputStreamWriter writer = null;
        BufferedReader reader;

       // String id = Settings.Secure.getString(context.getContentResolver(),
       //         Settings.Secure.ANDROID_ID);



        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        String currentDateTimeString=sdf.format(new Date());


        //String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        //Log.d("time format",currentDateTimeString);


        try {
            // Create data variable for sent values to server
            String query_string = null;

            query_string = URLEncoder.encode("device", "UTF-8")
                    + "=" + URLEncoder.encode("Kitchen Estimote 6", "UTF-8");

            if(status==0) {
                //I am just going to use 1 represent it's in beacon1's range
                query_string += "&" + URLEncoder.encode("action", "UTF-8") + "="
                        + URLEncoder.encode(String.valueOf(6), "UTF-8");
            }else{
                query_string += "&" + URLEncoder.encode("action", "UTF-8") + "="
                        + URLEncoder.encode(String.valueOf(-6), "UTF-8");
            }

            query_string += "&" + URLEncoder.encode("timestamp", "UTF-8") + "='"
                    + URLEncoder.encode(currentDateTimeString, "UTF-8") + "'";

            String text = "";

            // Defined URL  where to send data
            URL url = null;
            url = new URL("http://murphy.wot.eecs.northwestern.edu/~sme0261/SQLGateway.py");

            // Send POST data request
            URLConnection conn = null;
            conn = (URLConnection) url.openConnection();
            conn.setDoOutput(true);
            writer = new OutputStreamWriter(conn.getOutputStream());
            writer.write(query_string);
            writer.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null) {
                // Append server response in string
                sb.append(line + "\n");
            }
            text = sb.toString();
            Log.d("sendData", text);
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
