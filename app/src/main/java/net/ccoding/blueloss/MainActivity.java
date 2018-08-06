package net.ccoding.blueloss;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
  public static SharedPreferences appPrefs;
  public static SharedPreferences networks;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // This is so that we can access the prefs in a non activity class.
    appPrefs = getSharedPreferences( "blueloss_settings", MODE_PRIVATE);
    networks = getSharedPreferences( "networks", MODE_PRIVATE);

    setUpCompoundButtonListeners();

//    Log.d("DAWG", BlueLossSettings.isBlueLossEnabled() + "");
//    BlueLossSettings.setBlueLossEnabled(false);
//    Log.d("DAWG", BlueLossSettings.isBlueLossEnabled() + "");
//    BlueLossSettings.setBlueLossEnabled(true);

    Discoverable.setDiscoverable();

  }

  private void setUpCompoundButtonListeners(){
    CheckBox discoverableWhenNotConnectedToNetworkCheckBox = findViewById(R.id.discoverableWhenNotConnectedToNetworkCheckBox);
    discoverableWhenNotConnectedToNetworkCheckBox.setChecked(BlueLossSettings.isDiscoverableWhenNotConnectedToNetwork());

    discoverableWhenNotConnectedToNetworkCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        BlueLossSettings.setDiscoverableWhenNotConnectedToNetwork(isChecked);
        Log.d("DAWG", "isDiscoverableWhenNotConnectedToNetwork is enabled: " + BlueLossSettings.isDiscoverableWhenNotConnectedToNetwork());
      }
    });

    CheckBox rollbarLoggingCheckBox = findViewById(R.id.rollbarLoggingCheckBox);
    rollbarLoggingCheckBox.setChecked(BlueLossSettings.isRollbarLoggingEnabled());

    rollbarLoggingCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        BlueLossSettings.setRollbarLoggingEnabled(isChecked);
        Log.d("DAWG", "isRollbarLoggingEnabled is enabled: " + BlueLossSettings.isRollbarLoggingEnabled());
      }
    });


    Switch enableDisableSwitch = findViewById(R.id.enableDisableSwitch);
    enableDisableSwitch.setChecked(BlueLossSettings.isBlueLossEnabled());

    enableDisableSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        BlueLossSettings.setBlueLossEnabled(isChecked);
        Log.d("DAWG", "BlueLoss is enabled: " + BlueLossSettings.isBlueLossEnabled());
      }
    });
  }
}
