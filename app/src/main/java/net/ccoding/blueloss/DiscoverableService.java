package net.ccoding.blueloss;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class DiscoverableService extends Service {
  private boolean discoverableServiceRunning = false;
  private NotificationManager mNM;
  // notificationIdCode can't be 0
  private static int notificationIdCode = new Random().nextInt(Integer.MAX_VALUE) + 1;
  // Unique Identification Number for the Notification.
  // We use it on Notification start, and to cancel it.
//  private static int uniqueCode = 55599843;
//  private static String notificationChannelId = "net.ccoding.blueloss:servicenotifcationchannelid";
//  private static String notificationChannelName = "net.ccoding.blueloss:servicenotifcationchannel";

  @Override
  public void onCreate() {
    mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    if(Utils.isOreoOrAbove()) {
      startForeground(notificationIdCode, BlueLossServiceNotification.showServiceNotification(mNM, this));
    }
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//      NotificationChannel infoChannel = new NotificationChannel(
//          notificationChannelId,
//          notificationChannelName,
//          NotificationManager.IMPORTANCE_DEFAULT
//      );
//      infoChannel.setDescription("BlueLoss notification channel");
//      infoChannel.enableLights(false);
//      infoChannel.enableVibration(false);
//      mNM.createNotificationChannel(infoChannel);
//
//      Notification notification = new Notification
//          .Builder(this, "blueloss-service-notification")
//          .setSmallIcon(R.drawable.ic_stat_notificationicon_blueloss)  // the status icon
//          .setTicker("Ticker Text")  // the status text
//          .setWhen(System.currentTimeMillis())  // the time stamp
//          .setChannelId(notificationChannelId)
////        .setContentTitle(getText(R.string.local_service_label))  // the label of the entry
//          .setContentText("Content Text")  // the contents of the entry
//          .build();
//      startForeground(NOTIFICATION_ID, notification);
//    }
  }

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }
  @Override
  public void onDestroy() {
    // Cancel the persistent notification.
    mNM.cancel(notificationIdCode);
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    MyLogger.d("onStartCommand called");
    MyLogger.d("networkStatusServiceRunning : " + discoverableServiceRunning);
    MyLogger.d(this);

    if(!discoverableServiceRunning){
      discoverableServiceRunning = true;

//      showNotification();

      MyLogger.d("After if(!networkCheckServiceAlreadyRunning){ check");

      final BlueLossSettings blueLossSettings = new BlueLossSettings(this);
      final NetworkInformation networkInfo = new NetworkInformation(this);
      final Networks networks = new Networks(this, networkInfo);
      final Discoverable discoverable = new Discoverable(blueLossSettings, networks);

      new Timer().scheduleAtFixedRate(
        new TimerTask() {
          @Override
          public void run() {
            MyLogger.d("After TimerTask timeout");
            discoverable.toggleDiscoverable();
          }
        },
        5000,
        5000
      );
      return Service.START_STICKY;
    }
    stopSelf();
    return Service.START_NOT_STICKY;
  }

//  private void showNotification() {
//    if(Utils.isOreoOrAbove()){
//
//      NotificationChannel infoChannel = new NotificationChannel(
//          notificationChannelId,
//          notificationChannelName,
//          NotificationManager.IMPORTANCE_DEFAULT
//      );
//      infoChannel.setDescription("BlueLoss notification channel");
//      infoChannel.enableLights(false);
//      infoChannel.enableVibration(false);
//      mNM.createNotificationChannel(infoChannel);
//
//      Notification notification = new Notification
//          .Builder(this, "blueloss-service-notification")
//          .setSmallIcon(R.drawable.ic_stat_notificationicon_blueloss)  // the status icon
//          .setTicker("Ticker Text")  // the status text
//          .setWhen(System.currentTimeMillis())  // the time stamp
//          .setChannelId(notificationChannelId)
////        .setContentTitle(getText(R.string.local_service_label))  // the label of the entry
//          .setContentText("Content Text")  // the contents of the entry
//          .build();
//
//
//      mNM.notify(uniqueCode, notification);
//    }
//  }
}

