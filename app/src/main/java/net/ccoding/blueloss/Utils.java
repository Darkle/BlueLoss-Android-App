package net.ccoding.blueloss;

import android.os.Handler;

import java.util.AbstractMap;
import java.util.Map;
import java.util.NoSuchElementException;

final class Utils {

  public interface TaskHandle {
    void invalidate();
  }

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

//  public static boolean isNougatOrAbove(){
//    return android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N;
//  }

  public static boolean isOreoOrAbove(){
    return android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O;
  }

  public static void forceAppExit(){
    android.os.Process.killProcess(android.os.Process.myPid());
  }
}