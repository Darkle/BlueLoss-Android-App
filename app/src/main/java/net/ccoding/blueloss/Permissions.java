package net.ccoding.blueloss;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

final class Permissions {
  private static final String logTag = Permissions.class.getSimpleName();

  public static void promptForPermissions(MainActivity activity){
    // ACCESS_COARSE_LOCATION is a dangerous permissions, so we need to ask for it:
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
      // Close the app if we aren't allowed permission.
      android.os.Process.killProcess(android.os.Process.myPid());
    }
  }

}
