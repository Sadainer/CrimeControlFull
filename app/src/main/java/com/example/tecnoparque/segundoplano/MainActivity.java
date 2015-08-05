package com.example.tecnoparque.segundoplano;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import microsoft.aspnet.signalr.client.Action;
import microsoft.aspnet.signalr.client.ConnectionState;
import microsoft.aspnet.signalr.client.ErrorCallback;
import microsoft.aspnet.signalr.client.LogLevel;
import microsoft.aspnet.signalr.client.Logger;
import microsoft.aspnet.signalr.client.MessageReceivedHandler;
import microsoft.aspnet.signalr.client.Platform;
import microsoft.aspnet.signalr.client.StateChangedCallback;
import microsoft.aspnet.signalr.client.http.android.AndroidPlatformComponent;
import microsoft.aspnet.signalr.client.hubs.HubConnection;
import microsoft.aspnet.signalr.client.hubs.HubProxy;


public class MainActivity extends Activity {


    private static Context context;
    HubProxy proxy;
   /* private static String  serverUrl;
    private static String userId;
    private HubProxy eventHub;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText etPost = (EditText) findViewById(R.id.TxtTexto);
        Button sendButton = (Button) findViewById(R.id.butEnviar);

        startConnection();
        sendButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                RegistroDTO Usu = new RegistroDTO();
                Usu.setUsuarioCedula("1065582510");
                Usu.setDireccion("Calle central");
                Usu.setLocal("Mi futuro");
                Usu.setIDRed("Comercio");

                proxy.invoke("send", "Console", new Gson().toJson(Usu).toString());
                        /*.done(new Action<Void>() {
                   @Override
                    public void run(Void obj) throws Exception {
                        System.out.println("SENT!");
                    }
                })*/

            }
        });


        /*IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        BroadcastReceiver mReceiver = new MyReceiverPantalla();
        registerReceiver(mReceiver, filter);*/
    }

    public void startConnection() {

        Platform.loadPlatformComponent(new AndroidPlatformComponent());
        String host = "http://190.109.185.138:8002/";
        final HubConnection connection = new HubConnection(host);
        proxy = connection.createHubProxy("ChatHub");

        // subscribe to received - equal to `connection.received(function (data)` from javascript
        connection.received(new MessageReceivedHandler() {

            @Override
            public void onMessageReceived(JsonElement json) {
                System.out.println("RAW received message: " + json.toString());

                // ADD HANDLING OF RECEIVED IN HERE

                Intent intent =
                        new Intent(MainActivity.this, ActivityAlarma.class);

                Bundle b = new Bundle();
                b.putString("Usuario", json.toString());
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        // equal to `connection.disconnected(function ()` from javascript
        connection.closed(new Runnable() {

            @Override
            public void run() {

            }
        });

        // equal to `connection.stateChanged(function (change)`
        connection.stateChanged(new StateChangedCallback() {

            @Override
            public void stateChanged(ConnectionState oldState, ConnectionState newState) {
                // ADD CODE TO HANDLE STATE CHANGES
            }
        });

        // start the connection
        connection.start()
                .done(new Action<Void>() {

                    @Override
                    public void run(Void obj) throws Exception {
                        proxy.invoke("registerConId", "Android_S4");
                        System.out.println("Connected");

                    }
                });
    }


    private void ConectarSignalR(){
        // Create a new console logger
        Logger logger = new Logger() {

            @Override
            public void log(String message, LogLevel level) {
                System.out.println(message);
            }
        };

        // Connect to the server
        HubConnection conn = new HubConnection("http://190.109.185.138:8002/", "", true, logger);

        // Create the hub proxy
        proxy = conn.createHubProxy("ChatHub");

        proxy.subscribe(new Object() {
            @SuppressWarnings("unused")
            public void messageReceived(String message) {}
        });


        // Subscribe to the error event
        conn.error(new ErrorCallback() {

            @Override
            public void onError(Throwable error) {
                error.printStackTrace();
            }
        });

        // Subscribe to the connected event
        conn.connected(new Runnable() {

            @Override
            public void run() {
                System.out.println("CONNECTED");
            }
        });

        // Subscribe to the closed event
        conn.closed(new Runnable() {

            @Override
            public void run() {
                System.out.println("DISCONNECTED");
            }
        });


        // Start the connection
        conn.start()
                .done(new Action<Void>() {

                    @Override
                    public void run(Void obj) throws Exception {
                        System.out.println("Done Connecting!");
                    }
                });

        // Subscribe to the received event
        conn.received(new MessageReceivedHandler() {

            @Override
            public void onMessageReceived(JsonElement json) {
                System.out.println("RAW received message: " + json.toString());
                /*MensajeDTO myObject = new Gson().fromJson(json, MensajeDTO.class);
                RegistroDTO Usuario = myObject.getA().get(0);
                System.out.println("Objeto mensaje" + new Gson().toJson(Usuario));*/
                Intent intent =
                        new Intent(MainActivity.this, ActivityAlarma.class);

              /*  Bundle b = new Bundle();
                b.putString("Usuario", new Gson().toJson(Usuario));
                intent.putExtras(b);*/
                startActivity(intent);
            }
        });

    }



    public static void vibrate(){
        Vibrator v = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
