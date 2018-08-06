package net.ccoding.blueloss;


final class Utils {

  public static boolean shouldSetToDiscoverable() {
    return BlueLossSettings.isDiscoverableWhenNotConnectedToNetwork() || Networks.isConnectedToASavedNetwork();
  }


}