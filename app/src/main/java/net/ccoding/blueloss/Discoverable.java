package net.ccoding.blueloss;

import android.bluetooth.BluetoothAdapter;

import java.lang.reflect.Method;

/*
* https://stackoverflow.com/a/47452626/2785644.
* https://stackoverflow.com/a/30269261/2785644
* https://github.com/yinhaide/HDBluetooth/blob/master/hdbluetooth/src/main/java/com/yhd/hdbluetooth/BluetoothHelper.java#L285
* https://github.com/angcyo/DuDuHome_Home/blob/master/app/src/main/java/com/dudu/android/launcher/utils/BtPhoneUtils.java#L810
* https://stackoverflow.com/questions/37628/what-is-reflection-and-why-is-it-useful
*/
final class Discoverable {
  private static Method setScanMode;
  private static final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
  private static final int scanTimeInSeconds = 1;
  private static final String logTag = Discoverable.class.getSimpleName();

  static {
    try {
      setScanMode = BluetoothAdapter.class.getMethod("setScanMode", int.class, int.class);
      setScanMode.setAccessible(true);
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    }
  }

  public static void setUnDiscoverable() {
    try {
      setScanMode.invoke(mBluetoothAdapter, BluetoothAdapter.SCAN_MODE_CONNECTABLE, scanTimeInSeconds);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void setDiscoverable(){
    if(!BlueLossSettings.isBlueLossEnabled()){
      return;
    }
    try {
      setScanMode.invoke(mBluetoothAdapter, BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE, scanTimeInSeconds);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
