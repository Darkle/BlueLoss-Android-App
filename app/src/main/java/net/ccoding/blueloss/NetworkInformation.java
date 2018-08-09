package net.ccoding.blueloss;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.util.HashMap;
import java.util.Map;

public class NetworkInformation {
  private WifiManager wifiManager;

  public NetworkInformation(Context context) {
    this.wifiManager = (WifiManager)context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
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
