package net.ccoding.blueloss;

import android.net.wifi.WifiInfo;

import java.util.HashMap;
import java.util.Map;

final class NetworkInfo {
  public static Map<String,String> getNetworkInfo(){
    Map<String,String> networkInfo = new HashMap<>();
    networkInfo.put(getNetworkBSSID(), getNetworkSSID());
    return networkInfo;
  }

  private static WifiInfo getConnectionInfo(){
    return MainActivity.wifiManager.getConnectionInfo();
  }

  private static String getNetworkBSSID(){
    return getConnectionInfo().getBSSID();
  }

  private static String getNetworkSSID(){
    return getConnectionInfo().getSSID();
  }
}
