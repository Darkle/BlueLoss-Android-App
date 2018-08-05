package net.ccoding.blueloss;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {
  private Method setScanMode;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    if (mBluetoothAdapter == null) {
      // Device doesn't support Bluetooth
      // show a toast
      return;
    }

    try {
      setScanMode = BluetoothAdapter.class.getMethod("setScanMode", int.class,int.class);
      setScanMode.setAccessible(true);
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    }

    setDiscoverable(mBluetoothAdapter, setScanMode);
//    Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
//    discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);
//    startActivity(discoverableIntent);

//    try {
//      Thread.sleep(360000);
//      Log.d("DAWG", "Sleep finished");
//      setUnDiscoverable(mBluetoothAdapter, setScanMode);
//      Log.d("DAWG", "closeDiscoverableTimeout done");
//      Thread.sleep(120000);
//      setDiscoverable(mBluetoothAdapter, setScanMode);
//      Log.d("DAWG", "called setDiscoverable 2nd time");
////      Intent discoverableIntent2 = new
////          Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
////      discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 1);
////      startActivity(discoverableIntent);
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }
  }
  /*
  * https://stackoverflow.com/a/47452626/2785644.
  * https://github.com/yinhaide/HDBluetooth/blob/master/hdbluetooth/src/main/java/com/yhd/hdbluetooth/BluetoothHelper.java#L285
  * https://stackoverflow.com/questions/37628/what-is-reflection-and-why-is-it-useful
  * https://developer.android.com/reference/android/bluetooth/le/ScanSettings.Builder.html#setScanMode(int)
  * */
  private void setUnDiscoverable(BluetoothAdapter mBluetoothAdapter, Method setScanMode) {
    try {
//      Method setDiscoverableTimeout = BluetoothAdapter.class.getMethod("setDiscoverableTimeout", int.class);
//      setDiscoverableTimeout.setAccessible(true);


//      setDiscoverableTimeout.invoke(mBluetoothAdapter, 1);
      setScanMode.invoke(mBluetoothAdapter, BluetoothAdapter.SCAN_MODE_CONNECTABLE);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void setDiscoverable(BluetoothAdapter mBluetoothAdapter, Method setScanMode){
    try {
//      Method setDiscoverableTimeout = BluetoothAdapter.class.getMethod("setDiscoverableTimeout", int.class);
//      setDiscoverableTimeout.setAccessible(true);
//      Method setScanMode =BluetoothAdapter.class.getMethod("setScanMode", int.class,int.class);
//      setScanMode.setAccessible(true);

//      setDiscoverableTimeout.invoke(mBluetoothAdapter, 1);
      setScanMode.invoke(mBluetoothAdapter, BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
