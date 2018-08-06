package net.ccoding.blueloss;


final class BlueLossSettings {
  public static boolean isBlueLossEnabled() {
    return MainActivity.appPrefs.getBoolean("bluelossEnabled",true);
  }

  public static void setBlueLossEnabled(boolean blueLossEnabled) {
    MainActivity.appPrefs.edit().putBoolean("bluelossEnabled", blueLossEnabled).apply();
  }

  public static boolean isDiscoverableWhenNotConnectedToNetwork() {
    return MainActivity.appPrefs.getBoolean("discoverableWhenNotConnectedToNetwork",false);
  }

  public static void setDiscoverableWhenNotConnectedToNetwork(boolean discoverableWhenNotConnectedToNetwork) {
    MainActivity.appPrefs.edit().putBoolean(
        "discoverableWhenNotConnectedToNetwork",
        discoverableWhenNotConnectedToNetwork
    ).apply();
  }

  public static boolean isRollbarLoggingEnabled() {
    return MainActivity.appPrefs.getBoolean("rollbarLoggingEnabled",true);
  }

  public static void setRollbarLoggingEnabled(boolean rollbarLoggingEnabled) {
    MainActivity.appPrefs.edit().putBoolean("rollbarLoggingEnabled", rollbarLoggingEnabled).apply();
  }
}
