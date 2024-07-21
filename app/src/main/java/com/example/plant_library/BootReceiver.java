package com.example.plant_library;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Log.d("BootReceiver", "Boot completed received");
            Intent serviceIntent = new Intent(context, WateringReminderService.class);
            serviceIntent.setAction("SET_REMINDERS");
            context.startService(serviceIntent);
        }
    }
}