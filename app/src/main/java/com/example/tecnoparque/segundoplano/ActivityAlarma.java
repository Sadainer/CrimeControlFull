package com.example.tecnoparque.segundoplano;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;


public class ActivityAlarma extends ActionBarActivity {

    MediaPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        setContentView(R.layout.activity_activity_alarma);

        final TextView Usu= (TextView) findViewById(R.id.textView);
        String jsonMyObject = new String();
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            jsonMyObject = bundle.getString("Usuario");
        }
        RegistroDTO myObject = new Gson().fromJson(jsonMyObject, RegistroDTO.class);
        Usu.setText(myObject.getUsuarioCedula() + " " + myObject.getDireccion() + " " + myObject.getLocal() + " " + myObject.getIDRed());
        //Construimos el mensaje a mostrar
        player = MediaPlayer.create(ActivityAlarma.this,R.raw.alerta);
        player.start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_alarma, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
