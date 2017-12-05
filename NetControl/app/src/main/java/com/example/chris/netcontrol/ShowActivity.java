package com.example.chris.netcontrol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

/**
 * Created by chris on 30/7/2017.
 */
public class ShowActivity extends ActionBarActivity {

    private String router;
    private TextView resultadoT;
    public static String resultado="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.showrun);
        resultadoT = (TextView) findViewById(R.id.editTextRespShows);

        resultado = getIntent().getExtras().getString("resul");

        resultadoT.setText(resultado, TextView.BufferType.NORMAL);
    }

    @Override
    public void onBackPressed() {
        Intent i= new Intent(ShowActivity.this,MenuActivity.class);
        finish();
        startActivity(i);

        // Otherwise defer to system default behavior.
        super.onBackPressed();
    }
}
