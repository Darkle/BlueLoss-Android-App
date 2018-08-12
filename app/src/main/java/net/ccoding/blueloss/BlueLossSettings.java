package net.ccoding.blueloss;

import android.content.Context;
import android.content.SharedPreferences;

public class BlueLossSettings {
  private SharedPreferences appPrefs;
  private static int modePrivate = 0;

  public BlueLossSettings(Context context) {
    this.appPrefs = context.getSharedPreferences( "blueloss_settings", modePrivate);
  }

  public boolean isBlueLossEnabled() {
    return appPrefs.getBoolean("bluelossEnabled",true);
  }

  public void setBlueLossEnabled(boolean blueLossEnabled) {
    appPrefs.edit().putBoolean("bluelossEnabled", blueLossEnabled).apply();
  }

  public boolean forceDiscoverableIsEnabled() {
    return appPrefs.getBoolean("forceDiscoverable",false);
  }

  public void setForceDiscoverable(boolean forceDiscoverable) {
    appPrefs.edit().putBoolean("forceDiscoverable", forceDiscoverable).apply();
  }

  public boolean isBugsnagLoggingEnabled() {
    return appPrefs.getBoolean("bugsnagLoggingEnabled",true);
  }

  public void setBugsnagLoggingEnabled(boolean bugsnagLoggingEnabled) {
    appPrefs.edit().putBoolean("bugsnagLoggingEnabled", bugsnagLoggingEnabled).apply();
  }
}
