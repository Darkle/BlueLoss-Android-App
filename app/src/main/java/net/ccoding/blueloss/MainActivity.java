package net.ccoding.blueloss;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
  private BlueLossSettings blueLossSettings;
  private Networks networks;
  private Discoverable discoverable;
  private NetworkInformation networkInfo;
  private static final int exitDelay = (Toast.LENGTH_LONG + 2) * 1000;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
//    Logger.addLogAdapter(new AndroidLogAdapter());
    blueLossSettings = new BlueLossSettings(this);
    networkInfo = new NetworkInformation(this);
    networks = new Networks(this, networkInfo);
    discoverable = new Discoverable(blueLossSettings, networks);

    if(!permissionsEnabled(MainActivity.this)){
      promptForPermissions(MainActivity.this);
    }
    if(discoverable.shouldSetToDiscoverable()){
      discoverable.setDiscoverable();
    }
    setUpCompoundButtonListeners();

    Intent intent = new Intent(this, DiscoverableService.class);
    if(Utils.isOreoOrAbove()){
      startForegroundService(intent);
    }
    else{
      MyLogger.d("Else 1");
      startService(intent);
    }
    Intent intent2 = new Intent(this, DiscoverableService.class);
    if(Utils.isOreoOrAbove()){
      startForegroundService(intent2);
    }
    else{
      MyLogger.d("Else 2");
      startService(intent2);
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    if(requestCode != 1){
      return;
    }
    if(grantResults.length < 1 || grantResults[0] != PackageManager.PERMISSION_GRANTED){
      Toast toast = Toast.makeText(getApplicationContext(), "This permission is needed. Exiting BlueLoss.", Toast.LENGTH_LONG);
      toast.show();
      // Close the app if we aren't allowed permission.
      Utils.setTimeout(new Runnable() {
        public void run() {
          Utils.forceAppExit();
        }
      }, exitDelay);
    }
  }

  public static void promptForPermissions(MainActivity activity){
    // ACCESS_COARSE_LOCATION is considered a dangerous permission, so we need to ask for it:
    // https://developer.android.com/guide/topics/permissions/overview#permission-groups
    ActivityCompat.requestPermissions(
        activity,
        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
        1
    );
  }

  public static boolean permissionsEnabled(MainActivity activity){
    return ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
        == PackageManager.PERMISSION_GRANTED;
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
        MyLogger.d(networkInfo.getNetworkInfo());

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
