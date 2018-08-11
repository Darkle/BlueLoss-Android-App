
Don't use the Android Studio (IntelliJ) emulator as that makes my system hang and crash and makes it so that I have to reboot.

Remember to first enable USB debugging on the phone you are testing on: https://www.kingoapp.com/root-tutorials/how-to-enable-usb-debugging-mode-on-android.htm

We are targeting Android 5.0 (Lollipop): https://developer.android.com/about/versions/android-5.0#BluetoothBroadcasting

For setting up IntelliJ, you may need to set the Gradle settings like the "Gradle JVM" set it to Use JAVA_HOME (https://stackoverflow.com/a/36539783/2785644) and also to "Use local gradle distribution" 

Then go to Settings->Appearance & Behaviour->System Settings->Android SDK and check if anything needs to be installed or removed there.
- also if you are running on a PC that supports Intel Virtualization Technology, you should also make sure to install the Intel x86 Emulator Accelerator in the SDK Tools tab there as that will make the Emulator run faster. 

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

If intellij is really slow, you may want to try some of these: https://stackoverflow.com/questions/30817871/android-studio-is-slow-how-to-speed-up/32366177

Note: when you're testing the app, it seems to stay broadcasting even when you close the app, so you may need to disable then re-enable blueloss everytime you reload the app on the phone in dev.

To view logcat via the terminal, you can run `adb shell`, then type in `logcat` and press enter and that will show you the full log.

To uninstall your apk via the terminal, run `adb uninstall your.package.name`

To install your apk via the terminal, tun `adb install /path/to/apk-debug.apk`

We use the broadcast receiver for detecting network changes in Android Marshmellow (6) and below. We use Job Schedulers to check for network changes in Android Nougat(7) and above.

The built in android emulator in IntelliJ seems to crash my system, so try using Androidx86 with virtualbox to test on Oreo. 
-- to get the virtualbox emulator to show up as a device when you click run in IntelliJ, first on the commandline, type `adb connect 192.168.1.5:5555` and change the IP to whatever the ip is of the virtualbox vm.