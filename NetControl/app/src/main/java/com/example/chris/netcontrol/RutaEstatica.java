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
public class RutaEstatica extends ActionBarActivity {

    private String ipDes,nextHop, mascara, funcion="8",router;
    private EditText ipDesT,mascaraT,nextHopT;
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    //private final String serverUrl = "http://netcontrol.zapto.org/index.php";
    private final String serverUrl = "http://192.168.88.252/index.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.rutaestatica);

        router = getIntent().getExtras().getString("router");
        Button crearB = (Button)findViewById(R.id.buttonCrear);
        ipDesT = (EditText) findViewById(R.id.editTextDestino);
        mascaraT = (EditText) findViewById(R.id.editTextMascara);
        nextHopT = (EditText) findViewById(R.id.editTextNextHop);

        if (crearB != null) {
            crearB.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    ipDes = ipDesT.getText().toString();
                    mascara= mascaraT.getText().toString();
                    nextHop= nextHopT.getText().toString();
                    if (ipDes.length()<7 || mascara.length()<7 || nextHop.length()<7)
                    {
                        Toast.makeText(RutaEstatica.this,"Parámetros incompletos!",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        new CreatePing().execute();
                    }

                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        Intent i= new Intent(RutaEstatica.this,MenuActivity.class);
        finish();
        startActivity(i);

        // Otherwise defer to system default behavior.
        super.onBackPressed();
    }

    private class CreatePing extends AsyncTask<String, String, String> {

        @Override

        protected String doInBackground(String... args)
        {
            int success;
            try {
                // Building Parameters
                List parametros = new ArrayList();
                parametros.add(new BasicNameValuePair("funcion",funcion));
                parametros.add(new BasicNameValuePair("router",router));
                parametros.add(new BasicNameValuePair("ipDestino",ipDes ));
                parametros.add(new BasicNameValuePair("mascara", mascara));
                parametros.add(new BasicNameValuePair("nextHop", nextHop));

                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(serverUrl, "POST", parametros);

                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1)
                {
                    Log.d("Message:",json.getString(TAG_MESSAGE));
                    return json.getString(TAG_MESSAGE);

                }
                else
                {
                    Log.d("Message:","Error:"+json.getString(TAG_MESSAGE));
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
                Toast.makeText(RutaEstatica.this, file_url, Toast.LENGTH_LONG).show();
            }
        }
    }
}
