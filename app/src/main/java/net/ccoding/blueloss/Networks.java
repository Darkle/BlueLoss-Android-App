package net.ccoding.blueloss;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class Networks {
  private static final Type typeOfHashMap = new TypeToken<Map<String, String>>() { }.getType();  // https://stackoverflow.com/a/12117517/2785644
  private SharedPreferences prefsNetworks;
  private NetworkInformation networkInfo;
  private static int modePrivate = 0;

  public Networks(Context context, NetworkInformation networkInfo) {
    this.prefsNetworks = context.getSharedPreferences( "networks", modePrivate);
    this.networkInfo = networkInfo;
  }

  public boolean isConnectedToASavedNetwork() {
    Map.Entry<String,String> networkEntry = Utils.getStringMapFirstEntry(networkInfo.getNetworkInfo());
    String bssid = networkEntry.getKey();

    if(bssid == null){
      return false;
    }
    return networkFoundInSavedNetworks(bssid);
  }

  public void saveCurrentNetwork(){
    Map.Entry<String,String> networkEntry = Utils.getStringMapFirstEntry(networkInfo.getNetworkInfo());
    String bssid = networkEntry.getKey();
    String ssid = networkEntry.getValue();
    if(bssid == null){
      return;
    }
    saveNetwork(bssid, ssid);
  }

  public void removeNetwork(String bssid){
    if(!networkFoundInSavedNetworks(bssid)){
      return;
    }
    Map<String,String> savedNetworks = getSavedNetworks();
    savedNetworks.remove(bssid);

    prefsNetworks.edit().putString(
        "networks",
        convertNetworksHashMapToJsonString(savedNetworks)
    ).apply();
  }

  private Map<String,String> getSavedNetworks(){
    String serializedNetworks = prefsNetworks.getString("networks","");
    Map<String,String> networks = convertJsonStringToNetworksHashMap(serializedNetworks);

    if(networks == null){
      networks = new HashMap<String,String>();
    }
    return networks;
  }

  private void saveNetwork(String bssid, String ssid){
    if(networkFoundInSavedNetworks(bssid)){
      return;
    }

    Map<String,String> savedNetworks = getSavedNetworks();
    savedNetworks.put(bssid, ssid);

    prefsNetworks.edit().putString("networks", convertNetworksHashMapToJsonString(savedNetworks)).apply();
  }

  private boolean networkFoundInSavedNetworks(String bssid){
    Map<String,String> savedNetworks = getSavedNetworks();
    return savedNetworks.containsKey(bssid);
  }

  private Map<String,String> convertJsonStringToNetworksHashMap(String json){
    Gson gson = new Gson();
    return gson.fromJson(json, typeOfHashMap);
  }

  private String convertNetworksHashMapToJsonString(Map<String,String> networks){
    Gson gson = new Gson();
    return gson.toJson(networks, typeOfHashMap);
  }
}