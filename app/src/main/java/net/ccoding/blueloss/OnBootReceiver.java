package net.ccoding.blueloss;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class OnBootReceiver extends BroadcastReceiver {
  @Override
  public void onReceive(Context context, Intent intent) {
    BlueLossSettings blueLossSettings = new BlueLossSettings(context);
    NetworkInformation networkInfo = new NetworkInformation(context.getApplicationContext());
    Networks networks = new Networks(context, networkInfo);
    Discoverable discoverable = new Discoverable(blueLossSettings, networks);

    if(discoverable.shouldSetToDiscoverable()){
      discoverable.setDiscoverable();
    }
    // We only need to use Job Schedulers in a service for Nougat and above.
    // For devices using Android versions below Nougat we are using a broadcast receiver.
//    if(Utils.isNougatOrAbove()) {
//      NetworkCheckService networkCheckService = new NetworkCheckService();
//      networkCheckService.enqueueWork(context, new Intent());
//    }
  }
}