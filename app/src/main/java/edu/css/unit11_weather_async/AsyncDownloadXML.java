package edu.css.unit11_weather_async;

import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class AsyncDownloadXML extends AsyncTask<MainActivity, String, String> {
    MainActivity actWeather;


    //Implementation of AsyncTask used to download XML feed
// This method is run in a separate thread.  Do not do any UI stuff here.
// Calls onPostExecute when done and passes it the return value or String
    @Override
    protected String doInBackground(MainActivity... new_actWeather) {
        try {
            Log.v("Gibbons","AsyncDownloadXML doInBackground");

            // Save a pointer to the main Weather Activity which is passed in as a parameter
            actWeather = new_actWeather[0];

            // create the XML Pull Parser
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            URL weatherURL =  new URL("http://w1.weather.gov/xml/current_obs/KDLH.xml");
            String  weatherStrURL =  "http://w1.weather.gov/xml/current_obs/KDLH.xml";

            InputStream stream = weatherURL.openStream();
            xpp.setInput(stream, null);
            int eventType = xpp.getEventType();

            String tempStr = "Updaing...";			// Temperature Update String
            String windStr = "Updaing...";			// Wind Update String
            publishProgress(tempStr,windStr);

            while (eventType != XmlPullParser.END_DOCUMENT) {

                // look for a start tag
                if(eventType == XmlPullParser.START_TAG) {
                    Log.v("Gibbons","Start tag found");
                    // get the tag name and process it
                    String tag = xpp.getName();
                    if (tag.equals("wind_mph")){
                        eventType = xpp.next(); 		// go to next element which should be the text
                        windStr = xpp.getText();
                        Log.v("Gibbons","Wind MPH" +windStr);
                        publishProgress(tempStr,windStr);	// Update the display
                    }
                    if (tag.equals("temperature_string")){
                        eventType = xpp.next(); 		// go to next element which should be the text
                        tempStr = xpp.getText();
                        Log.v("Gibbons","Temp" + tempStr);
                        publishProgress(tempStr,windStr);	// Update the display
                    }

                }
                eventType = xpp.next();
            }
            return "Successfully updated weather";

        } catch (IOException e) {
            Log.v("Gibbons","AsyncDownloadXML doInBackground IOException");
            Log.v("Gibbons",e.getMessage());
            return(e.getMessage());
        } catch (XmlPullParserException e) {
            Log.v("Gibbons","AsyncDownloadXML doInBackground XmlPullParserException");
            Log.v("Gibbons",e.getMessage());
            return(e.getMessage());
        }  catch (Exception e) {
            Log.v("Gibbons","AsyncDownloadXML doInBackground Exception");
            Log.v("Gibbons",e.getMessage());
            return(e.getMessage());
        }

    }

    @Override
    protected void onProgressUpdate(String... update) {
        Log.v("Gibbons","in onProgressUpdate");
        actWeather.setTemp(update[0]);
        actWeather.setWind(update[1]);

    }

    @Override
    protected void onPostExecute(String result) {
        Log.v("Gibbons", "in onPostExecute");
        actWeather.setStatus(result);

    }

    }


