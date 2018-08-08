package net.ccoding.blueloss;

import android.os.Handler;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.Map;
import java.util.NoSuchElementException;

final class Utils {
  private static final String logTag = Utils.class.getSimpleName();

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

  public static void showToast(String toastMessage){
    Toast toast = Toast.makeText(MainActivity.appContext, toastMessage, Toast.LENGTH_LONG);
    toast.show();
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

  public static void forceAppExit(){
    android.os.Process.killProcess(android.os.Process.myPid());
  }
}