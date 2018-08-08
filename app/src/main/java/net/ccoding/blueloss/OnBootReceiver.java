package net.ccoding.blueloss;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class OnBootReceiver extends BroadcastReceiver {

  private static final String logTag = OnBootReceiver.class.getSimpleName();

  @Override
  public void onReceive(Context context, Intent intent) {
    BlueLossSettings blueLossSettings = new BlueLossSettings(context);
    NetworkInfo networkInfo = new NetworkInfo(context.getApplicationContext(), Context.WIFI_SERVICE);
    Networks networks = new Networks(context, networkInfo);
    Discoverable discoverable = new Discoverable(blueLossSettings, networks);

    if(discoverable.shouldSetToDiscoverable()){
      discoverable.setDiscoverable();
    }
  }
}