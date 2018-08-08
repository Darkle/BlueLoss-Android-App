package net.ccoding.blueloss;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
  public static Context appContext;
  private BlueLossSettings blueLossSettings;
  private Networks networks;
  private Discoverable discoverable;
  private NetworkInformation networkInfo;

  private static final String logTag = MainActivity.class.getSimpleName();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    appContext = getApplicationContext();
    blueLossSettings = new BlueLossSettings(this);
    networkInfo = new NetworkInformation(this);
    networks = new Networks(this, networkInfo);
    discoverable = new Discoverable(blueLossSettings, networks);

    Log.d(logTag, networkInfo.getNetworkInfo() +"");
    if(!Permissions.permissionsEnabled(MainActivity.this)){
      Permissions.promptForPermissions(MainActivity.this);
    }
    if(discoverable.shouldSetToDiscoverable()){
      discoverable.setDiscoverable();
    }
    setUpCompoundButtonListeners();
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    Permissions.handleRequestResult(requestCode, permissions, grantResults);
  }

  private void setUpCompoundButtonListeners(){

    Button saveNetwork = findViewById(R.id.saveButton);
    saveNetwork.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        networks.saveCurrentNetwork();
        if(discoverable.shouldSetToDiscoverable()){
          discoverable.setDiscoverable();
        }
        Log.d(logTag, networkInfo.getNetworkInfo() + "");

      }
    });

    Button removeNetwork = findViewById(R.id.removeButton);
    removeNetwork.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        networks.removeNetwork("f8:1a:67:42:f3:e8");
      }
    });


    CheckBox discoverableWhenNotConnectedToNetworkCheckBox = findViewById(R.id.discoverableWhenNotConnectedToNetworkCheckBox);
    discoverableWhenNotConnectedToNetworkCheckBox.setChecked(blueLossSettings.isDiscoverableWhenNotConnectedToNetwork());

    discoverableWhenNotConnectedToNetworkCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        blueLossSettings.setDiscoverableWhenNotConnectedToNetwork(isChecked);
      }
    });

    CheckBox rollbarLoggingCheckBox = findViewById(R.id.rollbarLoggingCheckBox);
    rollbarLoggingCheckBox.setChecked(blueLossSettings.isRollbarLoggingEnabled());

    rollbarLoggingCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        blueLossSettings.setRollbarLoggingEnabled(isChecked);
      }
    });


    Switch enableDisableSwitch = findViewById(R.id.enableDisableSwitch);
    enableDisableSwitch.setChecked(blueLossSettings.isBlueLossEnabled());

    enableDisableSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        blueLossSettings.setBlueLossEnabled(isChecked);
      }
    });
  }
}
