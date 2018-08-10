package net.ccoding.blueloss;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

final public class MyLogger {
  static
  {
    Logger.addLogAdapter(new AndroidLogAdapter());
  }

  public static void d(Object itemToLog){
    Logger.d(itemToLog);
  }
}
