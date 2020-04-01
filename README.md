# Nicedevice

The app it's to be used to help the development / Quality Team to start a new ODM project(Tablet - Smartphone).
Config device to start Google Tests. Features help to check all the features are correct.

## Compatibility 

Android 9

To guarantee system permissions was necessary add device administrator:

### Set:

__$ adb shell dpm set-device-owner com.studio.nicedevice/.DeviceAdminReceiver__

### Remove:

__$ adb shell dpm remove-active-admin com.studio.nicedevice/.DeviceAdminReceiver__

## Features

### Properties

Show all the main properties from the Device

### Google Config

Enable Development Mode
Enable ADB mode
Enable stay awake
Enable Wifi P&D
Set Time settings
Set Display Settings 

### Collect Logs

Start Logs 
Stop Logs
Save logs

## Goals

- [x] Properties
- [x] Google Config
- [ ] Easily mode collect
- [ ] Call Drops check



## Resources

### Add DevicePolicyManager

[Google API] https://developer.android.com/work/dpc/dedicated-devices

[Google API] https://developer.android.com/reference/android/app/admin/DevicePolicyManager

[Policy Manager] https://technostacks.com/blog/android-kiosk-mode/

[Policy Manager] https://snow.dog/blog/kiosk-mode-android

[Policy Manager] https://codelabs.developers.google.com/codelabs/cosu/index.html#2


## Contact

Diego Fraga

Email: diego.rfraga@gmail.com


