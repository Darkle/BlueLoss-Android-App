package net.ccoding.blueloss;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.util.HashMap;
import java.util.Map;

public class NetworkInfo {
  private WifiManager wifiManager;

//  public NetworkInfo(WifiManager wifiManager) {
//    this.wifiManager = wifiManager;
//  }
  public NetworkInfo(Context context, String wifiService) {
    this.wifiManager = (WifiManager)context.getApplicationContext().getSystemService(wifiService);
  }

  public Map<String,String> getNetworkInfo(){
    Map<String,String> networkInfo = new HashMap<>();
    networkInfo.put(getNetworkBSSID(), getNetworkSSID());
    return networkInfo;
  }

  private WifiInfo getConnectionInfo(){
    return wifiManager.getConnectionInfo();
  }

  private String getNetworkBSSID(){
    return getConnectionInfo().getBSSID();
  }

  private String getNetworkSSID(){
    return getConnectionInfo().getSSID();
  }
}
