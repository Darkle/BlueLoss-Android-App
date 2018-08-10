package net.ccoding.blueloss;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class OnBootReceiver extends BroadcastReceiver {
  @Override
  public void onReceive(Context context, Intent intent) {
    if(Utils.isOreoOrAbove()){
      context.startForegroundService(new Intent(context, NetworkCheckService.class));
    }
    else{
      context.startService(new Intent(context, NetworkCheckService.class));
    }
  }
}