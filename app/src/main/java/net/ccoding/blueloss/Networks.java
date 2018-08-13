package net.ccoding.blueloss;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Networks {
  private static final Type typeOfHashMap = new TypeToken<LinkedHashMap<String, String>>() { }.getType();  // https://stackoverflow.com/a/12117517/2785644
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
    LinkedHashMap.Entry<String,String> networkEntry = Utils.getStringMapFirstEntry(networkInfo.getNetworkInfo());
    String bssid = networkEntry.getKey();

    if(bssid == null){
      return false;
    }
    return networkFoundInSavedNetworks(bssid);
  }

  public void saveCurrentNetwork(){
    LinkedHashMap.Entry<String,String> networkEntry = Utils.getStringMapFirstEntry(networkInfo.getNetworkInfo());
    String bssid = networkEntry.getKey();
    String ssid = networkEntry.getValue();
    if(bssid == null){
//      Utils.showSnackBar(, bssidNullSnackBarMessage);  remember cant pass in view when constructing Networks as the boot recieved has no view i think
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
    LinkedHashMap<String,String> savedNetworks = getSavedNetworks();
    savedNetworks.remove(bssid);

    prefsNetworks.edit().putString(
        "networks",
        convertNetworksHashMapToJsonString(savedNetworks)
    ).apply();

//    Utils.showSnackBar(, ssid + "removed");
  }

  public LinkedHashMap<String,String> getSavedNetworks(){
    String serializedNetworks = prefsNetworks.getString("networks","");
    LinkedHashMap<String,String> networks = convertJsonStringToNetworksHashMap(serializedNetworks);

    if(networks == null){
      networks = new LinkedHashMap<String,String>();
    }
    return networks;
  }

  private void saveNetwork(String bssid, String ssid){
    if(networkFoundInSavedNetworks(bssid)){
      return;
    }

    LinkedHashMap<String,String> savedNetworks = getSavedNetworks();
    savedNetworks.put(bssid, ssid);

    prefsNetworks.edit().putString("networks", convertNetworksHashMapToJsonString(savedNetworks)).apply();
//    Utils.showSnackBar(, ssid + "saved");
  }

  private boolean networkFoundInSavedNetworks(String bssid){
    LinkedHashMap<String,String> savedNetworks = getSavedNetworks();
    return savedNetworks.containsKey(bssid);
  }

  private LinkedHashMap<String,String> convertJsonStringToNetworksHashMap(String json){
    Gson gson = new Gson();
    return gson.fromJson(json, typeOfHashMap);
  }

  private String convertNetworksHashMapToJsonString(LinkedHashMap<String,String> networks){
    Gson gson = new Gson();
    return gson.toJson(networks, typeOfHashMap);
  }

  public void _____debug____AddRandomNetworks(){
    saveNetwork("eb-a6-57-e5-9d-2e", "Network 1");
    saveNetwork("e3-b7-56-33-41-86", "Network 2");
    saveCurrentNetwork();
    saveNetwork("87-2a-fc-2c-51-eb", "Network asdfsdafk kjasdh kasjdh kasjdh kasjdh kasjd dh");
    saveNetwork("30-65-0d-40-2e-b2", "Network 3");
    saveNetwork("7e-24-16-1e-89-f6", "Network 4");
    saveNetwork("78-c4-b9-df-69-48", "Network 5");
    saveNetwork("02-ec-22-bb-a0-df", "Network 6");
    saveNetwork("5f-03-b6-06-c8-76", "Network 7");
    saveNetwork("e2-e2-99-3b-45-cb", "Network 8");
    saveNetwork("27-f3-bd-25-6a-40", "Network 9");
    saveNetwork("22-8f-40-f5-54-5c", "Network 10");
    saveNetwork("44-53-87-64-80-1b", "Network 11");
  }
}