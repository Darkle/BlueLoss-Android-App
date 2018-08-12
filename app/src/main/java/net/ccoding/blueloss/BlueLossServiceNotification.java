package net.ccoding.blueloss;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

final public class BlueLossServiceNotification {
  private static String notificationChannelId = "net.ccoding.blueloss:servicenotifcationchannelid";
  private static String notificationChannelName = "net.ccoding.blueloss:servicenotifcationchannel";

  @TargetApi(Build.VERSION_CODES.O)
  public static Notification showServiceNotification(NotificationManager mNotificationManager, Context context){

    NotificationChannel infoChannel = new NotificationChannel(
        notificationChannelId,
        notificationChannelName,
        NotificationManager.IMPORTANCE_DEFAULT
    );
    infoChannel.setDescription("BlueLoss notification channel");
    infoChannel.enableLights(false);
    infoChannel.enableVibration(false);
    mNotificationManager.createNotificationChannel(infoChannel);

    return new Notification
        .Builder(context, "blueloss-service-notification")
        .setSmallIcon(R.drawable.ic_stat_notificationicon_blueloss)  // the status icon
        .setTicker("BlueLoss service is running")  // the status text
        .setWhen(System.currentTimeMillis())  // the time stamp
        .setChannelId(notificationChannelId)
        .setContentText("BlueLoss service is running")  // the contents of the entry
        .build();
  }
}
