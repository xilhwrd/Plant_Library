package com.example.plant_library;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;

public class WateringReminderReceiver extends BroadcastReceiver{
    private static final String CHANNEL_ID = "WateringReminderChannel";

    @Override
    public void onReceive(Context context, Intent intent) {

        String plantName = intent.getStringExtra("plant_name");
        int notificationId = intent.getIntExtra("notification_id", 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.img_logo) // Thay đổi thành biểu tượng của bạn
                .setContentTitle("Nhắc nhở tưới nước")
                .setContentText("Đã đến lúc tưới nước cho cây " + plantName + " của bạn.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, builder.build());
    }


}
