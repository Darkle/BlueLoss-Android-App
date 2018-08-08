
Remember to first enable USB debugging on the phone you are testing on: https://www.kingoapp.com/root-tutorials/how-to-enable-usb-debugging-mode-on-android.htm

We are targeting Android 5.0 (Lollipop): https://developer.android.com/about/versions/android-5.0#BluetoothBroadcasting

For setting up IntelliJ, you may need to set the Gradle settings like the "Gradle JVM" set it to Use JAVA_HOME (https://stackoverflow.com/a/36539783/2785644) and also to "Use local gradle distribution" 

Then go to Settings->Appearance & Behaviour->System Settings->Android SDK and install the Lollipop 5.0 SDK (API Level 21) if its not installed & also SDK level 27 for compat stuff
- also remove any other api versions that are there as they will cause minor issues
- also if you are running on a PC that supports Intel Virtualization Technology, you should also make sure to install the Intel x86 Emulator Accelerator in the SDK Tools tab there as that will make the Emulator run faster. You can use this tool to check if you support it: https://downloadcenter.intel.com/download/7838/Intel-Processor-Identification-Utility-Windows-Version - you need YES for the two Virtualization stuff - e.g. https://imgur.com/a/8FHVyUd

Then set up the SDK:
 - If the yellow bar up top is showing, click on the Setup SDK in the yellow bar top right, then if Android API 21 Platform isnt there, you will need to add it manually, click on the plus icon and select Android SDK and add C:\Users\Coop\AppData\Local\Android\Sdk to the folder selector window path and then click ok and then select version 21.
- If not, go to Build menu, then Edit Libraries & Dependancies and go to app and click the Properties tab and set Compile SDK version to 21.
 
If you get an issue with building with gradle that the sdk version is wrong, you may need to add something like:
```
configurations.all {
    resolutionStrategy {
        force 'com.android.support:support-annotations:26.1.0'
    }
}
```
to the build.gradle file

Note: when you're testing the app, it seems to stay broadcasting even when you close the app, so you may need to disable then re-enable blueloss everytime you reload the app on the phone in dev.

To view logcat via the terminal, you can run `adb shell`, then type in `logcat` and press enter and that will show you the full log.

To uninstall your apk via the terminal, run `adb uninstall your.package.name`

To install your apk via the terminal, tun `adb install /path/to/apk-debug.apk`