package net.ccoding.blueloss;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

public class DiscoverableService extends Service {
  private boolean discoverableServiceRunning = false;

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    MyLogger.d("onStartCommand called");
    MyLogger.d("networkStatusServiceRunning : " + discoverableServiceRunning);

    if(!discoverableServiceRunning){
      discoverableServiceRunning = true;
//      if(Utils.isOreoOrAbove()){
//        // do the notification stuff!!!!!!!!!!!! for later androids
//      }

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
            if(discoverable.shouldSetToDiscoverable()){
              discoverable.setDiscoverable();
            }
            else {
              discoverable.setUnDiscoverable();
            }
          }
        },
        5000,
        5000
      );
    }

    return Service.START_STICKY;
  }

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

}

