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

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // We set up stuff here so that we can access them in non-activity classes.
    appPrefs = getSharedPreferences( "blueloss_settings", MODE_PRIVATE);
    prefsNetworks = getSharedPreferences( "networks", MODE_PRIVATE);
    wifiManager = (WifiManager)this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);


//    Log.d("DAWG", BlueLossSettings.isBlueLossEnabled() + "");
//    BlueLossSettings.setBlueLossEnabled(false);
//    Log.d("DAWG", BlueLossSettings.isBlueLossEnabled() + "");
//    BlueLossSettings.setBlueLossEnabled(true);

    if(Utils.shouldSetToDiscoverable()){
      Discoverable.setDiscoverable();
    }

    setUpCompoundButtonListeners();
  }

  private void setUpCompoundButtonListeners(){
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
