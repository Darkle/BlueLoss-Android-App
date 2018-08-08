package net.ccoding.blueloss;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

final class Permissions {
  private static final String logTag = Permissions.class.getSimpleName();
  private static final int exitDelay = (Toast.LENGTH_LONG + 2) * 1000;

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
  public static void handleRequestResult(int requestCode, String permissions[], int[] grantResults){
   if(requestCode != 1){
      return;
    }
    if(grantResults.length < 1 || grantResults[0] != PackageManager.PERMISSION_GRANTED){
      Utils.showToast("This permission is needed. Exiting BlueLoss.");
      // Close the app if we aren't allowed permission.
      Utils.setTimeout(new Runnable() {
        public void run() {
          Utils.forceAppExit();
        }
      }, exitDelay);
    }
  }

}
