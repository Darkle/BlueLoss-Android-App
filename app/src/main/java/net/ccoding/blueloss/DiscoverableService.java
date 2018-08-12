package net.ccoding.blueloss;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class DiscoverableService extends Service {
  private boolean discoverableServiceRunning = false;
  private NotificationManager mNotificationManager;
  // notificationIdCode can't be 0
  private static int notificationIdCode = new Random().nextInt(Integer.MAX_VALUE - 1) + 1;

  @Override
  public void onCreate() {
    mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    if(Utils.isOreoOrAbove()) {
      MyLogger.d("Doing Oreo notification");
      startForeground(notificationIdCode, BlueLossServiceNotification.showServiceNotification(mNotificationManager, this));
    }
  }

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }
  @Override
  public void onDestroy() {
    // Cancel the persistent notification.
    mNotificationManager.cancel(notificationIdCode);
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    MyLogger.d("onStartCommand called");
    MyLogger.d("networkStatusServiceRunning : " + discoverableServiceRunning);

    if(!discoverableServiceRunning){
      discoverableServiceRunning = true;

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
    return Service.START_NOT_STICKY;
  }
}

