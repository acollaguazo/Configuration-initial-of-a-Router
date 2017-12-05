package com.example.chris.netcontrol;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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
public class Loopback extends ActionBarActivity {

    private String ip="127.0.0.1",loop="7",mascara="255.255.255.0", funcion="2";
    private String router;
    private EditText ipT,mascaraT,loopT;
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    //private final String serverUrl = "http://netcontrol.zapto.org/index.php";
    private final String serverUrl = "http://192.168.88.252/index.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.loopback);

        Button crearB = (Button)findViewById(R.id.buttonCrear);
        ipT = (EditText) findViewById(R.id.editTextIP);
        loopT = (EditText) findViewById(R.id.editTextNumero);
        mascaraT = (EditText) findViewById(R.id.editTextMascara);
        router = getIntent().getExtras().getString("router");

        if (crearB != null) {
            crearB.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    ip = ipT.getText().toString();
                    mascara= mascaraT.getText().toString();
                    loop = loopT.getText().toString();

                    if (ip.length()<7 || mascara.length()<7 || loop.length()<1 )
                    {
                        Toast.makeText(Loopback.this,"Parámetros incompletos!",Toast.LENGTH_LONG).show();
                        return;
                    }

                    new CreateLoopback().execute();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        Intent i= new Intent(Loopback.this,MenuActivity.class);
        finish();
        startActivity(i);

        // Otherwise defer to system default behavior.
        super.onBackPressed();
    }

    private class CreateLoopback extends AsyncTask<String, String, String> {

        @Override

        protected String doInBackground(String... args)
        {
            int success;
            try {
                // Building Parameters
                List parametros = new ArrayList();
                parametros.add(new BasicNameValuePair("funcion",funcion));
                parametros.add(new BasicNameValuePair("router",router));
                parametros.add(new BasicNameValuePair("numeroLoopback",loop ));
                parametros.add(new BasicNameValuePair("ip", ip));
                parametros.add(new BasicNameValuePair("mascara", mascara));

                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(serverUrl, "POST", parametros);


                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1)
                {
                    return json.getString(TAG_MESSAGE);
                }
                else
                {
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
                Toast.makeText(Loopback.this, file_url, Toast.LENGTH_LONG).show();
            }
        }
    }
}
