package net.ccoding.blueloss;

import android.net.wifi.WifiInfo;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

final class Networks {
  public static boolean isConnectedToASavedNetwork() {
    Map<String,String> networkInfo = getNetworkInfo();
    String bssid = networkInfo.get("BSSID");
//    if(bssid == null){
//      return false;
//    }
    return networkFoundInSavedNetworks(bssid);
  }

  private static Map<String,String> getNetworkInfo(){
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

  private static boolean networkFoundInSavedNetworks(String bssid){
    Map<String,String> savedNetworks = getSavedNetworks();

    boolean networkFound = false;

    for (Object value : savedNetworks.values()) {
      if(value == bssid){
        networkFound = true;
      }
    }

    return networkFound;
  }

  private static Map<String,String> getSavedNetworks(){
    String serializedNetworks = MainActivity.prefsNetworks.getString("networks","");
    // https://stackoverflow.com/a/12117517/2785644
    Type typeOfHashMap = new TypeToken<Map<String, String>>() { }.getType();
    Gson gson = new Gson();

    return gson.fromJson(serializedNetworks, typeOfHashMap);
  }

  private static void saveCurrentNetwork(){
    Map<String,String> networkInfo = getNetworkInfo();
    saveNetwork(networkInfo.get("BSSID"), networkInfo.get("SSID"));
  }

  private static void saveNetwork(String bssid, String ssid){
    if(networkFoundInSavedNetworks(bssid)){
      return;
    }

    Map<String,String> savedNetworks = getSavedNetworks();
    savedNetworks.put("BSSID", bssid);
    savedNetworks.put("SSID", ssid);

    Gson gson = new Gson();
    String networksAsJsonString = gson.toJson(savedNetworks);

    Log.d("DAWG", networksAsJsonString);

//    MainActivity.prefsNetworks.edit().putString("networks", networksAsJsonString).apply();
  }

  private static void removeNetwork(String bssid){
    if(!networkFoundInSavedNetworks(bssid)){
      return;
    }
    Map<String,String> savedNetworks = getSavedNetworks();
    savedNetworks.remove(bssid);

    MainActivity.prefsNetworks.edit().putString("networks", blueLossEnabled).apply();
  }
}