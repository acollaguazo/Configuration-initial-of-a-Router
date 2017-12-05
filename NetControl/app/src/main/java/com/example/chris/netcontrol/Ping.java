package com.example.chris.netcontrol;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chris on 30/7/2017.
 */
public class Ping extends ActionBarActivity {

    private String ipDes="127.0.0.1",ipOri="7", funcion="3",router;
    private EditText ipDesT,ipOriT;
    private TextView resultadoT;
    private ProgressDialog pDialog;
    public static String resultado="";
    JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_RESULTADO = "text";
    //private final String serverUrl = "http://netcontrol.zapto.org/index.php";
    private final String serverUrl = "http://192.168.88.252/index.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ping);

        router = getIntent().getExtras().getString("router");
        Button pingB = (Button)findViewById(R.id.buttonPing);
        ipDesT = (EditText) findViewById(R.id.editTextDestino);
        ipOriT = (EditText) findViewById(R.id.editTextOrigen);
        resultadoT = (TextView) findViewById(R.id.editTextRespuesta);

        if (pingB != null) {
            pingB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ipDes = ipDesT.getText().toString();
                    ipOri= ipOriT.getText().toString();
                    if (ipDes.length()<7 || ipOri.length()<7)
                    {
                        Toast.makeText(Ping.this,"Faltan Datos!",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        new CreatePing().execute();
                    }
                }
            });
        }
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
                parametros.add(new BasicNameValuePair("ipOrigen", ipOri));

                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(serverUrl, "POST", parametros);

                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1)
                {
                    return json.getString(TAG_RESULTADO);
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
                resultado=file_url;
                resultadoT.setText(resultado, TextView.BufferType.NORMAL);
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent i= new Intent(Ping.this,MenuActivity.class);
        finish();
        startActivity(i);

        // Otherwise defer to system default behavior.
        super.onBackPressed();
    }
}
