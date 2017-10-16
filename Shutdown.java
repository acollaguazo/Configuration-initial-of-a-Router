package com.example.chris.netcontrol;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chris on 30/7/2017.
 */
public class Shutdown extends ActionBarActivity {

    private String inter, funcion="5",router;
    private EditText interT;
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    //private final String serverUrl = "http://netcontrol.zapto.org/index.php";
    private final String serverUrl = "http://192.168.88.252/index";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.shutdown);

        router = getIntent().getExtras().getString("router");
        interT = (EditText)findViewById(R.id.editTextInterface);
        Button shutB = (Button)findViewById(R.id.buttonShutdown);

        if (shutB != null) {
            shutB.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    inter = interT.getText().toString();
                    if (inter.length()<7)
                    {
                        Toast.makeText(Shutdown.this,"Parámetros Errados!",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        new AttempShutdown().execute();
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        Intent i= new Intent(Shutdown.this,MenuActivity.class);
        finish();
        startActivity(i);

        // Otherwise defer to system default behavior.
        super.onBackPressed();
    }

    private class AttempShutdown extends AsyncTask<String, String, String> {

        @Override

        protected String doInBackground(String... args)
        {
            int success;
            try {
                // Building Parameters
                List parametros = new ArrayList();
                parametros.add(new BasicNameValuePair("funcion",funcion));
                parametros.add(new BasicNameValuePair("router",router));
                parametros.add(new BasicNameValuePair("interface",inter ));

                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(serverUrl, "POST", parametros);

                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1)
                {
                    Log.d("Message:","Exito!");
                    return json.getString(TAG_MESSAGE);

                }
                else
                {
                    Log.d("Message:",json.getString(TAG_MESSAGE));
                    return json.getString(TAG_MESSAGE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override

        protected void onPostExecute(String file_url)
        {
            // dismiss the dialog once product deleted
            if (file_url != null) {
                Toast.makeText(Shutdown.this, file_url, Toast.LENGTH_LONG).show();
            }
        }
    }
}
