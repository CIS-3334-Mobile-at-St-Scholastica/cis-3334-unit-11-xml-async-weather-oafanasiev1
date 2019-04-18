package edu.css.unit11_weather_async;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    EditText etLoc;
    EditText etTemp;
    EditText etWind;
    EditText etVis;

    AsyncDownloadXML AsyncWeatherDownloader = new AsyncDownloadXML();

    /**
     * This method is triggered when activity is started. Assigns all the variables
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set up our edit text variables
        etLoc =  (EditText) findViewById(R.id.textLoc);
        etTemp =  (EditText) findViewById(R.id.textTemp);
        etWind =  (EditText) findViewById(R.id.textWind);
        etVis =  (EditText) findViewById(R.id.textVis);
    }

    /**
     * This method is triggered when the user clicks on the button to get information. It starts the AsyncWeatherDownloader
     * @param v
     * @throws XmlPullParserException
     * @throws URISyntaxException
     * @throws IOException
     */
    public void btnClick (View v) throws XmlPullParserException, URISyntaxException, IOException {
        // Download the weather asynchronously
        AsyncWeatherDownloader.execute(this);
    }

    /**
     * Sets the temp that is passed to the etWind to the main screen.
     * @param newTemp
     */
    public void setTemp(String newTemp) {

        etTemp.setText(newTemp);
    }

    /**
     * Sets the wind and passes it to the UI.
     * @param newWind
     */
    public void setWind(String newWind)
    {
        etWind.setText(newWind);
    }

    /**
     * Sets visibility that is passed to the UI
     * @param newVis
     */
    public void setVis(String newVis){
        etVis.setText(newVis);
    }

    /**
     * Returns the location that the user has entered
     * @return
     */
    public String getLocation() {

        return etLoc.getText().toString();
    }


    /**
     * Sets the status to the toast in the UI
     * @param newStatus
     */
    public void setStatus(String newStatus) {
        Toast toast=Toast.makeText(getApplicationContext(), newStatus,Toast.LENGTH_LONG );
        toast.show();
    }

}
