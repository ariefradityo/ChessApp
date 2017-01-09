package com.bukalapak.test.chessapp.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.androidannotations.annotations.EIntentService;
import org.androidannotations.annotations.ServiceAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by arief.radityo@gmail.com on 12/03/2016 in ChessApp.
 */

@EIntentService
public class SocketService extends IntentService {

    private static final String TAG = SocketService.class.getSimpleName();

    private static final String HOST_NAME = "xinuc.org";
    private static final String HOST_NAME_ALT = "http://mobile.suitmedia.com/bl/chess.php";
    private static final int PORT = 7387;


    public static final String ACTION_SUCCESS = "action_success";
    public static final String ACTION_ERROR = "action_error";
    public static final String MESSAGE_KEY = "message";

    public SocketService() {
        super(SocketService.class.getSimpleName());
    }

    @ServiceAction
    protected void persistentConnection(){
        Log.d(TAG, "Service started");
        try {
            Socket socket = new Socket(HOST_NAME, PORT);
            socket.setKeepAlive(true);
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String message;
                while (true){
                    message = in.readLine();
                    if(message != null){
                        Log.d(TAG, "Message received");
                        Intent messageIntent = new Intent();
                        messageIntent.setAction(ACTION_SUCCESS);
                        messageIntent.putExtra(MESSAGE_KEY, message);
                        LocalBroadcastManager.getInstance(this).sendBroadcast(messageIntent);
                    }
                }
            }
            catch (SocketException e){
                e.printStackTrace();
                sendErrorIntent("Koneksi ke server terputus");
            }
            finally {
                socket.close();
                Log.e(TAG, "Socket closed");
            }

        }
        catch (UnknownHostException e){
            e.printStackTrace();
            sendErrorIntent("Koneksi ke server bermasalah");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    private void sendErrorIntent(String message){
        Intent errorIntent = new Intent();
        errorIntent.setAction(ACTION_ERROR);
        errorIntent.putExtra(MESSAGE_KEY, message);
        LocalBroadcastManager.getInstance(this).sendBroadcast(errorIntent);
    }
}
