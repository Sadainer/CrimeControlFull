package com.example.tecnoparque.segundoplano;

import android.content.Context;

import com.google.gson.JsonElement;

import microsoft.aspnet.signalr.client.Action;
import microsoft.aspnet.signalr.client.ErrorCallback;
import microsoft.aspnet.signalr.client.LogLevel;
import microsoft.aspnet.signalr.client.Logger;
import microsoft.aspnet.signalr.client.MessageReceivedHandler;
import microsoft.aspnet.signalr.client.Platform;
import microsoft.aspnet.signalr.client.http.android.AndroidPlatformComponent;
import microsoft.aspnet.signalr.client.hubs.HubConnection;
import microsoft.aspnet.signalr.client.hubs.HubProxy;

public class program {

    private Context cnt = null;
    private String mData = null;

    public program (String data) {
        mData = data;
    }

    public void execute() {
        Platform.loadPlatformComponent(new AndroidPlatformComponent());

        // Connect to the server
        HubConnection conn = new HubConnection("http://190.109.185.138:8002/", "", true, logger);

        // Create the hub proxy
        HubProxy proxy = conn.createHubProxy("ChatHub");

        proxy.subscribe(new Object() {
            @SuppressWarnings("unused")
            public void messageReceived(String name, String message) {
                System.out.println(name + ": " + message);
            }
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
            }
        });

        conn.stop();
    }
        

        // Create a new console logger
        Logger logger = new Logger() {

            @Override
            public void log(String message, LogLevel level) {
                System.out.println(message);
            }
        };


    }

