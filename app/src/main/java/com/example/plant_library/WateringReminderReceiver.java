package com.example.plant_library;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class WateringReminderReceiver extends BroadcastReceiver{
    private static final String CHANNEL_ID = "WateringReminderChannel";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("WateringReminderReceiver", "Received broadcast");
        String plantName = intent.getStringExtra("plant_name");
        int notificationId = intent.getIntExtra("notification_id", 0);


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Watering Reminder", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.img_logo) // Thay đổi thành biểu tượng của bạn
                .setContentTitle("Nhắc nhở tưới nước")
                .setContentText("Đã đến lúc tưới nước cho cây " + plantName + " của bạn.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        notificationManager.notify(notificationId, builder.build());
    }


}
