package net.ccoding.blueloss;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.lang.reflect.Method;

public class OnBootReceiver extends BroadcastReceiver {

  private static final String logTag = OnBootReceiver.class.getSimpleName();

  @Override
  public void onReceive(Context context, Intent intent) {
//    if(Utils.shouldSetToDiscoverable()){
//      Discoverable.setDiscoverable();
//    }
    Method setScanMode = null;
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    try {
      setScanMode = BluetoothAdapter.class.getMethod("setScanMode", int.class, int.class);
      setScanMode.setAccessible(true);
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    }
    try {
      setScanMode.invoke(mBluetoothAdapter, BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE, 1);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}