package net.ccoding.blueloss;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NetworkConnectionReceiver extends BroadcastReceiver {
  private static final String logTag = NetworkConnectionReceiver.class.getSimpleName();

  @Override
  public void onReceive(Context context, Intent intent) {
    Log.d(logTag, "NetworkConnectionReceiver onReceive called");

    BlueLossSettings blueLossSettings = new BlueLossSettings(context);
    NetworkInformation networkInfo = new NetworkInformation(context.getApplicationContext());
    Networks networks = new Networks(context, networkInfo);
    Discoverable discoverable = new Discoverable(blueLossSettings, networks);
    /*
      If wifi is turned off, when we call shouldSetToDiscoverable it calls
      networks.isConnectedToASavedNetwork, which will return false as it gets a null BSSID, which
      means we will fall through here to the else clause which is what we want. We want to set the
      device to be undiscoverable when the wifi is off.
     */

    if(discoverable.shouldSetToDiscoverable()){
      discoverable.setDiscoverable();
    }
    else {
      discoverable.setUnDiscoverable();
    }
  }
}
