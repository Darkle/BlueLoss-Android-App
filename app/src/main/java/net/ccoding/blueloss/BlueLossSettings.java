package net.ccoding.blueloss;


import android.content.Context;
import android.content.SharedPreferences;

public class BlueLossSettings {
  private SharedPreferences appPrefs;
  private static int modePrivate = 0;
  private static final String logTag = BlueLossSettings.class.getSimpleName();

  public BlueLossSettings(Context context) {
    this.appPrefs = context.getSharedPreferences( "blueloss_settings", modePrivate);
  }

  public boolean isBlueLossEnabled() {
    return appPrefs.getBoolean("bluelossEnabled",true);
  }

  public void setBlueLossEnabled(boolean blueLossEnabled) {
    appPrefs.edit().putBoolean("bluelossEnabled", blueLossEnabled).apply();
  }

  public boolean isDiscoverableWhenNotConnectedToNetwork() {
    return appPrefs.getBoolean("discoverableWhenNotConnectedToNetwork",false);
  }

  public void setDiscoverableWhenNotConnectedToNetwork(boolean discoverableWhenNotConnectedToNetwork) {
    appPrefs.edit().putBoolean(
        "discoverableWhenNotConnectedToNetwork",
        discoverableWhenNotConnectedToNetwork
    ).apply();
  }

  public boolean isRollbarLoggingEnabled() {
    return appPrefs.getBoolean("rollbarLoggingEnabled",true);
  }

  public void setRollbarLoggingEnabled(boolean rollbarLoggingEnabled) {
    appPrefs.edit().putBoolean("rollbarLoggingEnabled", rollbarLoggingEnabled).apply();
  }
}
