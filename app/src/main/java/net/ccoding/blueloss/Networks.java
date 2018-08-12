package net.ccoding.blueloss;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
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
  private String bssidNullSnackBarMessage = "The current network returned null for it's BSSID, so we are not saving it.";
  private String networAlreadySavedSnackBarMessage = "The current network has already been saved.";

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
//      Utils.showSnackBar(, bssidNullSnackBarMessage);
      return;
    }
    if(networkFoundInSavedNetworks(bssid)){
//      Utils.showSnackBar(, networAlreadySavedSnackBarMessage);
      return;
    }
    saveNetwork(bssid, ssid);
  }

  public void removeNetwork(String bssid, String ssid){
    if(!networkFoundInSavedNetworks(bssid)){
      return;
    }
    Map<String,String> savedNetworks = getSavedNetworks();
    savedNetworks.remove(bssid);

    prefsNetworks.edit().putString(
        "networks",
        convertNetworksHashMapToJsonString(savedNetworks)
    ).apply();

//    Utils.showSnackBar(, ssid + "removed");
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
//    Utils.showSnackBar(, ssid + "saved");
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