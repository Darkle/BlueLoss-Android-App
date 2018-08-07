package net.ccoding.blueloss;


import android.widget.Toast;

import java.util.AbstractMap;
import java.util.Map;
import java.util.NoSuchElementException;

final class Utils {
  private static final String logTag = Utils.class.getSimpleName();

  public static Map.Entry<String,String> getStringMapFirstEntry(Map<String,String> map){
    Map.Entry<String,String> entry = new AbstractMap.SimpleEntry<String, String>(null, null);
    try{
      entry = map.entrySet().iterator().next();
    }
    catch (NoSuchElementException e){
      e.printStackTrace();
    }
    return entry;
  }

  public static void showToast(String toastMessage){
    Toast toast = Toast.makeText(MainActivity.appContext, toastMessage, Toast.LENGTH_SHORT);
    toast.show();
  }
}