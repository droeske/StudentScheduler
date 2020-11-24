package com.example.studentscheduler;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;
import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * End Receiver (Used for course end channel notifications)
 *
 * StudentScheduler: Designed at API level 26 using Oreo OS
 * @author David Roeske
 */

public class MyEndReceiver extends BroadcastReceiver {

    static int notificationID;
    String channel_id1="3";

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"Notification",Toast.LENGTH_LONG).show();

        createNotificationChannel(context,channel_id1);

        Notification n1= new NotificationCompat.Builder(context, channel_id1)
                .setSmallIcon(R.drawable.calendar)
                .setContentText("Course ends today")
                .setContentTitle("Course End").build();

        NotificationManager notificationManager=(NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID++,n1);
    }
    private void createNotificationChannel(Context context, String CHANNEL_ID) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Course end";
            String description = "end";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}