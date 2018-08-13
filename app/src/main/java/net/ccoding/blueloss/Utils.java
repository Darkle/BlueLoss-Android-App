package net.ccoding.blueloss;

import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.view.View;

import java.util.AbstractMap;
import java.util.Map;
import java.util.NoSuchElementException;

final class Utils {

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

  public interface TaskHandle {
    void invalidate();
  }

  // via http://peatiscoding.me/geek-stuff/quick-note/javascript-settimeout-android-java/
  public static TaskHandle setTimeout(final Runnable r, long delay) {
    final Handler h = new Handler();
    h.postDelayed(r, delay);
    return new TaskHandle() {
      @Override
      public void invalidate() {
        h.removeCallbacks(r);
      }
    };
  }

  public static void showSnackBar(View view, String message){
    Snackbar snackbar = Snackbar.make(
        view,
        message,
        Snackbar.LENGTH_LONG
    );
    snackbar.show();
  }

//  public static boolean isNougatOrAbove(){
//    return android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N;
//  }

  public static boolean isOreoOrAbove(){
    return android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O;
  }

  public static void forceAppExit(){
    android.os.Process.killProcess(android.os.Process.myPid());
  }

  public static String removeDoubleQuotes(String ssid){
    if(ssid != null && ssid.length() > 2){
      if(ssid.startsWith("\"")){
        ssid = ssid.substring(1);
      }
      if(ssid.endsWith("\"")){
        ssid = ssid.substring(0, ssid.length() - 1);
      }
    }
    return ssid;
  }
}