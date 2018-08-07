package net.ccoding.blueloss;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
  public static SharedPreferences appPrefs;
  public static SharedPreferences prefsNetworks;
  public static WifiManager wifiManager;
  public static Context appContext;

  private static final String logTag = MainActivity.class.getSimpleName();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // We set up stuff here so that we can access them in non-activity classes.
    appContext = this.getApplicationContext();
    appPrefs = getSharedPreferences( "blueloss_settings", MODE_PRIVATE);
    prefsNetworks = getSharedPreferences( "networks", MODE_PRIVATE);
    wifiManager = (WifiManager)this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

    if(!Permissions.permissionsEnabled(MainActivity.this)){
      Permissions.promptForPermissions(MainActivity.this);
    }

    if(Utils.shouldSetToDiscoverable()){
      Discoverable.setDiscoverable();
    }

    setUpCompoundButtonListeners();
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    Permissions.handleRequestResult(requestCode, permissions, grantResults);
  }

  private void setUpCompoundButtonListeners(){

//    Button saveNetwork = findViewById(R.id.saveButton);
//    saveNetwork.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        Networks.saveCurrentNetwork();
//        Log.d(logTag, NetworkInfo.getNetworkInfo() + "");
//
//      }
//    });
//
//    Button removeNetwork = findViewById(R.id.removeButton);
//    removeNetwork.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        Networks.removeNetwork("f8:1a:67:42:f3:e8");
//      }
//    });


    CheckBox discoverableWhenNotConnectedToNetworkCheckBox = findViewById(R.id.discoverableWhenNotConnectedToNetworkCheckBox);
    discoverableWhenNotConnectedToNetworkCheckBox.setChecked(BlueLossSettings.isDiscoverableWhenNotConnectedToNetwork());

    discoverableWhenNotConnectedToNetworkCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        BlueLossSettings.setDiscoverableWhenNotConnectedToNetwork(isChecked);
      }
    });

    CheckBox rollbarLoggingCheckBox = findViewById(R.id.rollbarLoggingCheckBox);
    rollbarLoggingCheckBox.setChecked(BlueLossSettings.isRollbarLoggingEnabled());

    rollbarLoggingCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        BlueLossSettings.setRollbarLoggingEnabled(isChecked);
      }
    });


    Switch enableDisableSwitch = findViewById(R.id.enableDisableSwitch);
    enableDisableSwitch.setChecked(BlueLossSettings.isBlueLossEnabled());

    enableDisableSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        BlueLossSettings.setBlueLossEnabled(isChecked);
      }
    });
  }
}
