package net.ccoding.blueloss;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

final class Networks {
  private static final String toastMessageForNullBSSID = "The current network returned null for the bssid, so we're not saving it. Sorry :-(";
  private static final Type typeOfHashMap = new TypeToken<Map<String, String>>() { }.getType();  // https://stackoverflow.com/a/12117517/2785644
  private static final String logTag = Networks.class.getSimpleName();

  public static boolean isConnectedToASavedNetwork() {
    Map.Entry<String,String> networkEntry = Utils.getStringMapFirstEntry(NetworkInfo.getNetworkInfo());
    String bssid = networkEntry.getKey();

    if(bssid == null){
      return false;
    }
    return networkFoundInSavedNetworks(bssid);
  }

  public static void saveCurrentNetwork(){
    Map.Entry<String,String> networkEntry = Utils.getStringMapFirstEntry(NetworkInfo.getNetworkInfo());
    String bssid = networkEntry.getKey();
    String ssid = networkEntry.getValue();
    if(bssid == null){
      Utils.showToast(toastMessageForNullBSSID);
      return;
    }
    saveNetwork(bssid, ssid);
  }

  public static void removeNetwork(String bssid){
    if(!networkFoundInSavedNetworks(bssid)){
      return;
    }
    Map<String,String> savedNetworks = getSavedNetworks();
    savedNetworks.remove(bssid);

    MainActivity.prefsNetworks.edit().putString(
        "networks",
        convertNetworksHashMapToJsonString(savedNetworks)
    ).apply();
  }

  private static Map<String,String> getSavedNetworks(){
    String serializedNetworks = MainActivity.prefsNetworks.getString("networks","");
    Map<String,String> networks = convertJsonStringToNetworksHashMap(serializedNetworks);

    if(networks == null){
      networks = new HashMap<String,String>();
    }
    return networks;
  }

  private static void saveNetwork(String bssid, String ssid){
    if(networkFoundInSavedNetworks(bssid)){
      return;
    }

    Map<String,String> savedNetworks = getSavedNetworks();
    savedNetworks.put(bssid, ssid);

    MainActivity.prefsNetworks.edit().putString(
        "networks",
        convertNetworksHashMapToJsonString(savedNetworks)
    ).apply();
  }

  private static boolean networkFoundInSavedNetworks(String bssid){
    Map<String,String> savedNetworks = getSavedNetworks();
    return savedNetworks.containsKey(bssid);
  }

  private static Map<String,String> convertJsonStringToNetworksHashMap(String json){
    Gson gson = new Gson();
    return gson.fromJson(json, typeOfHashMap);
  }

  private static String convertNetworksHashMapToJsonString(Map<String,String> networks){
    Gson gson = new Gson();
    return gson.toJson(networks, typeOfHashMap);
  }
}