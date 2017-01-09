package com.bukalapak.test.chessapp.util;

import android.app.ActivityManager;
import android.content.Context;

/**
 * Created by arief.radityo@gmail.com on 12/03/2016 in ChessApp.
 *
 * Utility class
 */
public class Util {

    /**
     * Normalize the character format to value from 0-7
     * @param row
     * @return normalized row index
     */
    public static int normalizeRow(char row){
        return 7 - (Integer.valueOf(String.valueOf(row))-1);
    }

    /**
     * Normalize the character format to value from 0-7
     * @param col
     * @return normalized column index
     */
    public static int normalizeCol(char col){
        return col - 'a';
    }

    /**
     * Check whether socket service is already running or not
     * @param context
     * @return
     */
    public static boolean isSocketServiceRunning(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (("com.bukalapak.test.chessapp.service.SocketService_").equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
