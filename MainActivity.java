package com.example.chris.netcontrol;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

public class MainActivity extends ActionBarActivity {

    protected EditText username;
    private ProgressDialog pDialog;
    private EditText password;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private String enteredUsername, enteredPassword,funcion="1";
    //private final String serverUrl = "http://netcontrol.zapto.org/index.php";
    private final String serverUrl = "http://192.168.88.252/index.php";
    JSONParser jsonParser = new JSONParser();

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        username = (EditText)findViewById(R.id.textUsuario);
        password = (EditText)findViewById(R.id.textContrasena);
        Button loginButton = (Button)findViewById(R.id.buttonLogin);

        if (loginButton != null) {
            loginButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    enteredUsername = username.getText().toString();
                    enteredPassword = password.getText().toString();

                    if(enteredUsername.equals("") && enteredPassword.equals(""))
                    {
                        Toast.makeText(MainActivity.this, "Alerta: Campos vacíos!!", Toast.LENGTH_LONG).show();
                        return;

                    }
                    else if(enteredUsername.equals(""))
                    {
                        Toast.makeText(MainActivity.this, "Alerta: Campo USUARIO vacío!!", Toast.LENGTH_LONG).show();
                        return;
                    }
                    else if(enteredPassword.equals(""))
                    {
                        Toast.makeText(MainActivity.this, "Alerta: Campos CONTRASEÑA vacío!!", Toast.LENGTH_LONG).show();
                        return;
                    }
                    new AttemptLogin().execute();

                }

            });
        }

    }


    private class AttemptLogin extends AsyncTask<String, String, String> {

        @Override

        protected String doInBackground(String... args)
        {
            int success;
            try {
                // Building Parameters
                List parametros = new ArrayList();
                parametros.add(new BasicNameValuePair("funcion",funcion));
                parametros.add(new BasicNameValuePair("username", enteredUsername));
                parametros.add(new BasicNameValuePair("password", enteredPassword));

                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(serverUrl, "POST", parametros);

                // check your log for json response
                Log.d("Loging in...", json.toString());

                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1)
                {
                    Log.d("Login Successful!", json.toString());
                    // save user data
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(MainActivity.this);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("username", enteredUsername);
                    edit.commit();

                    Intent i = new Intent(MainActivity.this,MenuActivity.class);
                    finish();
                    startActivity(i);

                    return json.getString(TAG_MESSAGE);

                }
                else
                {
                    Log.d("Login Failure!", json.getString(TAG_MESSAGE));
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
                Toast.makeText(MainActivity.this, file_url, Toast.LENGTH_LONG).show();
            }
        }
    }
}