package net.ccoding.blueloss;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import com.bugsnag.android.Bugsnag;


public class MainActivity extends AppCompatActivity {
  private BlueLossSettings blueLossSettings;
  private Networks networks;
  private Discoverable discoverable;
  private NetworkInformation networkInfo;
  private static final int exitDelay = (Toast.LENGTH_LONG + 2) * 1000;
  private static final int permissionRequestCode = 1;
  private static View mainActivityView;
  public Intent discoverableService;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Bugsnag.init(this);

    mainActivityView = findViewById(android.R.id.content);
    blueLossSettings = new BlueLossSettings(this);
    networkInfo = new NetworkInformation(this);
    networks = new Networks(this, networkInfo);
    discoverable = new Discoverable(blueLossSettings, networks);
    discoverableService = new Intent(this, DiscoverableService.class);

    if(!blueLossSettings.isBugsnagLoggingEnabled()){
      Bugsnag.disableExceptionHandler();
    }

    if(!permissionsEnabled(MainActivity.this)){
      promptForPermissions(MainActivity.this);
    }

    setUpCompoundButtonListeners();

    discoverable.toggleDiscoverable();

    if(Utils.isOreoOrAbove()){
      startForegroundService(discoverableService);
    }
    else{
      startService(discoverableService);
    }

  }

  @Override
  public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    if(requestCode != permissionRequestCode){
      return;
    }
    if(grantResults.length < 1 || grantResults[0] != PackageManager.PERMISSION_GRANTED){
      Utils.showSnackBar(mainActivityView, "This permission is needed. Exiting BlueLoss.");
      // Close the app if we aren't allowed permission.
      Utils.setTimeout(new Runnable() {
        public void run() {
          stopService(discoverableService);
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
        permissionRequestCode
    );
  }

  public static boolean permissionsEnabled(MainActivity activity){
    return ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
        == PackageManager.PERMISSION_GRANTED;
  }

  private void setUpCompoundButtonListeners(){

    Switch enableDisableSwitch = findViewById(R.id.enableDisableSwitch);
    enableDisableSwitch.setChecked(blueLossSettings.isBlueLossEnabled());

    enableDisableSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        blueLossSettings.setBlueLossEnabled(isChecked);
        /*
          Inside of toggleDiscoverable() we return if BlueLoss is disabled. We do this because otherwise
          we could interfere with users when they are using the Android BlueTooth settings page (as the device
          needs to be discoverable when that setting page is open) - because of that, we need to specifically
          call setUnDiscoverable() here, otherwise it won't get called.
        */
        if(!isChecked){
          discoverable.setUnDiscoverable();
        }
        else{
          discoverable.toggleDiscoverable();
        }
      }
    });

    CheckBox forceDiscoverableCheckBox = findViewById(R.id.forceDiscoverableCheckBox);
    forceDiscoverableCheckBox.setChecked(blueLossSettings.forceDiscoverableIsEnabled());

    forceDiscoverableCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        blueLossSettings.setForceDiscoverable(isChecked);
        discoverable.toggleDiscoverable();
      }
    });

    CheckBox bugsnagLoggingCheckBox = findViewById(R.id.bugsnagLoggingCheckBox);
    bugsnagLoggingCheckBox.setChecked(blueLossSettings.isBugsnagLoggingEnabled());

    bugsnagLoggingCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        blueLossSettings.setBugsnagLoggingEnabled(isChecked);
        if(isChecked){
          Bugsnag.enableExceptionHandler();
        }
        else{
          Bugsnag.disableExceptionHandler();
        }
      }
    });


  }
}
