package net.ccoding.blueloss;

import android.bluetooth.BluetoothAdapter;

final public class Bluetooth {
  private static BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
  public static boolean isEnabled(){
    return mBluetoothAdapter.isEnabled();
  }
}
