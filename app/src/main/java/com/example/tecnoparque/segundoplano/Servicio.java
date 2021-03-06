package com.example.tecnoparque.segundoplano;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class Servicio extends Service {

int conteo=0;
private Timer crono= new Timer();
private static final long intervalo=5000;
int tiempo=0,cambio=0,toque=0;
boolean para=true;
    @Override
    public void onCreate() {
        super.onCreate();
        // REGISTER RECEIVER THAT HANDLES SCREEN ON AND SCREEN OFF LOGIC
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        BroadcastReceiver mReceiver = new MyReceiverPantalla();
        registerReceiver(mReceiver, filter);

        crono.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if(conteo==0){
                    tiempo=0;
                }else{
                    tiempo = tiempo + 1;
                    if(tiempo-cambio>=2){
                        conteo=0;
                    }
                }

                Log.e("Sadainer", "Tiempo " + tiempo);
            }
        }, 0, 1000);

    }

    @Override
    public void onStart(Intent intent, int startId) {
        conteo+=1;
        boolean screenOn = intent.getBooleanExtra("screen_state", false);
        Context context=this;
        toque=conteo;
        cambio=tiempo;
        if ((conteo>=3)&&(tiempo<=2)) {
            Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            v.vibrate(100);
            Log.e("Sada", "Alerta activada " + conteo + " Toques en "+ tiempo + " segundos");
            conteo=0;
            tiempo=0;
        }
        if((tiempo>2)&&(conteo>2)){
            Log.e("Sada", "Alerta NO activada " + conteo + " Toques en "+ tiempo + " segundos");
            conteo=0;
            tiempo=0;
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }



}
