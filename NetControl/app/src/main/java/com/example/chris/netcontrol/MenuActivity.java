package com.example.chris.netcontrol;

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
 * Created by chris on 25/7/2017.
 */
public class MenuActivity extends ActionBarActivity{

    public String router,funcion="6";
    JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_RESULTADO = "text";
    //private final String serverUrl = "http://netcontrol.zapto.org/index.php";
    private final String serverUrl = "http://192.168.88.252/index.php";
    public static EditText routerT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.menu);

        Button plantillaB = (Button)findViewById(R.id.buttonPlantilla);
        Button showRunB = (Button)findViewById(R.id.buttonRespaldo);
        Button rutaB = (Button)findViewById(R.id.buttonRuta);
        Button lookbackB = (Button)findViewById(R.id.buttonLoopback);
        Button pingB = (Button)findViewById(R.id.buttonPing);
        Button shutdownB = (Button)findViewById(R.id.buttonShut);
        Button noShutdownB = (Button)findViewById(R.id.buttonNoShut);
        Button processB = (Button)findViewById(R.id.buttonProcess);
        Button showInterfacesB = (Button)findViewById(R.id.buttonInterfaces);
        Button routeTableB = (Button)findViewById(R.id.buttonRoute);
        routerT = (EditText)findViewById(R.id.editTextRouter);


        //Se accede a la actividad de PING
        if (lookbackB != null) {
            lookbackB.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {

                    router = routerT.getText().toString();
                    if (router.length()<7) {
                        Toast.makeText(MenuActivity.this,"Faltan Datos!",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Intent i =  new Intent(MenuActivity.this,Loopback.class);
                        i.putExtra("router",router);
                        finish();
                        startActivity(i);
                    }

                }
            });
        }

        //Se envia la plantilla inicial al router indicado
        if (plantillaB != null) {
            plantillaB.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    router = routerT.getText().toString();
                    if (router.length()<7) {
                        Toast.makeText(MenuActivity.this,"Faltan Datos!",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        new Plantilla().execute();
                    }

                }
            });
        }

        if (noShutdownB != null) {
            noShutdownB.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    router = routerT.getText().toString();
                    if (router.length()<7) {
                        Toast.makeText(MenuActivity.this,"Faltan Datos!",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Intent i =  new Intent(MenuActivity.this,NoShutdown.class);
                        i.putExtra("router",router);
                        finish();
                        startActivity(i);
                    }

                }
            });
        }

        if (shutdownB != null) {
            shutdownB.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    router = routerT.getText().toString();
                    if (router.length()<7) {
                        Toast.makeText(MenuActivity.this,"Faltan Datos!",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Intent i =  new Intent(MenuActivity.this,Shutdown.class);
                        i.putExtra("router",router);
                        finish();
                        startActivity(i);
                    }

                }
            });
        }

        if (pingB != null) {
            pingB.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    router = routerT.getText().toString();
                    if (router.length()<7) {
                        Toast.makeText(MenuActivity.this,"Faltan Datos!",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Intent i =  new Intent(MenuActivity.this,Ping.class);
                        i.putExtra("router",router);
                        finish();
                        startActivity(i);
                    }

                }
            });
        }

        if (rutaB != null) {
            rutaB.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    router = routerT.getText().toString();
                    if (router.length()<7) {
                        Toast.makeText(MenuActivity.this,"Faltan Datos!",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Intent i =  new Intent(MenuActivity.this,RutaEstatica.class);
                        i.putExtra("router",router);
                        finish();
                        startActivity(i);
                    }

                }
            });
        }

        if (showRunB != null) {
            showRunB.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    router = routerT.getText().toString();
                    if (router.length()<7) {
                        Toast.makeText(MenuActivity.this,"Faltan Datos!",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        funcion="9";
                        new AttempShow().execute();
                    }

                }
            });
        }

        if (showInterfacesB != null) {
            showInterfacesB.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    router = routerT.getText().toString();
                    if (router.length()<7) {
                        Toast.makeText(MenuActivity.this,"Faltan Datos!",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        funcion="10";
                        new AttempShow().execute();
                    }

                }
            });
        }

        if (routeTableB != null) {
            routeTableB.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    router = routerT.getText().toString();
                    if (router.length()<7) {
                        Toast.makeText(MenuActivity.this,"Faltan Datos!",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        funcion="12";
                        new AttempShow().execute();
                    }

                }
            });
        }

        if (processB != null) {
            processB.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    router = routerT.getText().toString();
                    if (router.length()<7) {
                        Toast.makeText(MenuActivity.this,"Faltan Datos!",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        funcion="11";
                        new AttempShow().execute();
                    }

                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        Intent i= new Intent(MenuActivity.this,MainActivity.class);
        finish();
        startActivity(i);

        // Otherwise defer to system default behavior.
        super.onBackPressed();
    }

    private class AttempShow extends AsyncTask<String, String, String> {

        @Override

        protected String doInBackground(String... args) {
            int success;
            try {
                // Building Parameters
                List parametros = new ArrayList();
                parametros.add(new BasicNameValuePair("funcion", funcion));
                parametros.add(new BasicNameValuePair("router", router));

                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(serverUrl, "POST", parametros);

                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    return json.getString(TAG_RESULTADO);
                } else {
                    return json.getString(TAG_MESSAGE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override

        protected void onPostExecute(String file_url)
        {
            // dismiss the dialog once product deleted
            if (file_url != null) {
                Intent i = new Intent(MenuActivity.this,ShowActivity.class);
                i.putExtra("resul",file_url);
                finish();
                startActivity(i);
            }
        }
    }

    private class Plantilla extends AsyncTask<String, String, String> {

        @Override

        protected String doInBackground(String... args) {
            funcion = "6";
            int success;

            try {
                // Building Parameters
                List parametros = new ArrayList();
                parametros.add(new BasicNameValuePair("funcion", funcion));
                parametros.add(new BasicNameValuePair("router", router));

                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(serverUrl, "POST", parametros);

                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("Message:",json.getString(TAG_MESSAGE));
                    return json.getString(TAG_MESSAGE);

                } else {
                    Log.d("Message:",json.getString(TAG_MESSAGE));
                    return json.getString(TAG_MESSAGE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }
    }
}
