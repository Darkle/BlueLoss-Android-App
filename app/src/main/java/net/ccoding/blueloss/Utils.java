package net.ccoding.blueloss;


import android.content.Context;
import android.widget.Toast;

import java.util.Map;

final class Utils {

  public static boolean shouldSetToDiscoverable() {
    return BlueLossSettings.isDiscoverableWhenNotConnectedToNetwork() || Networks.isConnectedToASavedNetwork();
  }

  public static Map.Entry<String,String> getStringMapFirstEntry(Map<String,String> map){
    return map.entrySet().iterator().next();
  }

  public static void showToast(String toastMessage){
    Toast toast = Toast.makeText(MainActivity.appContext, toastMessage, Toast.LENGTH_SHORT);
    toast.show();
  }
}