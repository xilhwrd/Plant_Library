package com.example.plant_library;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class WateringReminderService extends IntentService {
    private static final String PREFS_NAME = "WateringReminderPrefs";
    private static final String KEY_LAST_NOTIFICATION_DAY = "last_notification_day";
    public WateringReminderService() {
        super("WateringReminderService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null && "SET_REMINDERS".equals(intent.getAction())) {
            Log.d("WateringReminderService", "Service started");
            setWateringReminders();
        }
    }

    private void setWateringReminders() {
        List<CalendarDay> wateringDays = getWateringDaysFromDatabase();
        String plantName = getPlantNameFromDatabase();

        Calendar today = Calendar.getInstance();
        CalendarDay todayDay = CalendarDay.from(today);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String lastNotificationDay = prefs.getString(KEY_LAST_NOTIFICATION_DAY, "");

        // Chỉ lập lịch thông báo nếu ngày hiện tại chưa được thông báo
        if (!todayDay.toString().equals(lastNotificationDay)) {
            for (CalendarDay day : wateringDays) {
                if (day.equals(todayDay)) {
                    scheduleWateringReminder(this, day, plantName, todayDay.hashCode());

                    // Lưu ngày đã thông báo
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString(KEY_LAST_NOTIFICATION_DAY, todayDay.toString());
                    editor.apply();
                    break; // Chỉ cần thông báo một lần
                }
            }
        }

//        int notificationId = 0;
//        for (CalendarDay day : wateringDays) {
//            scheduleWateringReminder(this, day, plantName, notificationId++);
//        }
    }

    private void scheduleWateringReminder(Context context, CalendarDay wateringDay, String plantName, int notificationId) {
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        String channelId = "WateringReminderChannel";
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel(channelId, "Watering Reminder", NotificationManager.IMPORTANCE_HIGH);
//            notificationManager.createNotificationChannel(channel);
//        }
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
//                .setSmallIcon(R.drawable.img_logo) // Thay đổi thành biểu tượng của bạn
//                .setContentTitle("Nhắc nhở tưới nước")
//                .setContentText("Đã đến lúc tưới nước cho cây " + plantName + " của bạn.")
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setAutoCancel(false);
//
//        notificationManager.notify(0, builder.build());

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, wateringDay.getYear());
        calendar.set(Calendar.MONTH, wateringDay.getMonth() - 1);
        calendar.set(Calendar.DAY_OF_MONTH, wateringDay.getDay());
        calendar.set(Calendar.HOUR_OF_DAY, 20);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_MONTH, 1); // Đặt cho ngày hôm sau nếu ngày đã qua
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "WateringReminderChannel";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Watering Reminder", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.img_logo) // Thay đổi thành biểu tượng của bạn
                .setContentTitle("Nhắc nhở tưới nước")
                .setContentText("Đã đến lúc tưới nước cho cây " + plantName + " của bạn.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true); // Thay đổi để thông báo tự động hủy

        notificationManager.notify(notificationId, builder.build());
    }
    public static void startService(Context context, List<CalendarDay> wateringDays, String plantName) {
        Intent intent = new Intent(context, WateringReminderService.class);
        intent.setAction("SET_REMINDERS");
        context.startService(intent);
    }
    private List<CalendarDay> getWateringDaysFromDatabase() {
        // Lấy dữ liệu từ cơ sở dữ liệu của bạn
        return new ArrayList<>();
    }

    private String getPlantNameFromDatabase() {
        // Lấy dữ liệu từ cơ sở dữ liệu của bạn
        return "";
    }
}