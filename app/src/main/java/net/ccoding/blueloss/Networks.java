package net.ccoding.blueloss;

import android.net.wifi.WifiInfo;

import java.util.HashMap;
import java.util.Map;

final class Networks {
  public static boolean isConnectedToASavedNetwork() {
    Map networkInfo = getNetworkInfo();
    String bssid = networkInfo.get("BSSID");
    if(bssid == null){
      return false;
    }
    return networkFoundInSavedNetworks();
  }

  private static networkFoundInSavedNetworks(){

  }

  private static Map getNetworkInfo(){
    Map<String,String> networkInfo = new HashMap<>();
    networkInfo.put("BSSID", getNetworkBSSID());
    networkInfo.put("SSID", getNetworkSSID());
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