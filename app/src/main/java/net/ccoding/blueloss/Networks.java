package net.ccoding.blueloss;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

final class Networks {
  private static String toastMessageForNullBSSID = "The current network returned null for the bssid, so we're not saving it :-(";

  public static boolean isConnectedToASavedNetwork() {
    Map.Entry<String,String> networkInfo = Utils.getStringMapFirstEntry(NetworkInfo.getNetworkInfo());
    String bssid = networkInfo.getKey();

    if(bssid == null){
      return false;
    }
    return networkFoundInSavedNetworks(bssid);
  }

  private static boolean networkFoundInSavedNetworks(String bssid){
    Map<String,String> savedNetworks = getSavedNetworks();
    return savedNetworks.containsKey(bssid);
  }

  private static Map<String,String> getSavedNetworks(){
    String serializedNetworks = MainActivity.prefsNetworks.getString("networks","");
    // https://stackoverflow.com/a/12117517/2785644
    Type typeOfHashMap = new TypeToken<Map<String, String>>() { }.getType();
    Gson gson = new Gson();
    Map<String,String> networks = gson.fromJson(serializedNetworks, typeOfHashMap);

    if(networks == null){
      networks = new HashMap<>();
    }
    return networks;
  }

  public static void saveCurrentNetwork(){
    Map.Entry<String,String> networkInfo = Utils.getStringMapFirstEntry(NetworkInfo.getNetworkInfo());
    String bssid = networkInfo.getKey();
    String ssid = networkInfo.getValue();
    if(bssid == null){
      Utils.showToast(toastMessageForNullBSSID);
      return;
    }
    saveNetwork(bssid, ssid);
  }

  private static void saveNetwork(String bssid, String ssid){
    if(networkFoundInSavedNetworks(bssid)){
      return;
    }

    Map<String,String> savedNetworks = getSavedNetworks();
    savedNetworks.put(bssid, ssid);

    MainActivity.prefsNetworks.edit().putString(
        "networks",
        convertNetworksMapToJsonString(savedNetworks)
    ).apply();
  }

  public static void removeNetwork(String bssid){
    if(!networkFoundInSavedNetworks(bssid)){
      return;
    }
    Map<String,String> savedNetworks = getSavedNetworks();
    savedNetworks.remove(bssid);

    MainActivity.prefsNetworks.edit().putString(
        "networks",
        convertNetworksMapToJsonString(savedNetworks)
    ).apply();
  }

  private static String convertNetworksMapToJsonString(Map<String,String> networks){
    Gson gson = new Gson();
    return gson.toJson(networks);
  }
}