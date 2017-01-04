# todo-app

A simple mobile app to show all the cool new things I'm learning with ClojureScript and ReactNative.

## Usage

I used [re-natal](https://github.com/drapanjanas/re-natal) which is a command line utility that makes setting up a React
Native app using Clojurescript super easy. You should be able to run this app following the re-natal documentation. I'm
testing the app on a device (the emulator was way too slow for me). There are
instructions on the re-natal README for how to set that up.

While setting up re-natal, do take note of the [dependencies](https://github.com/drapanjanas/re-natal#dependencies).
The [React Native documentation](https://facebook.github.io/react-native/docs/running-on-device.html) is pretty good
too and you should follow that if you intend to test on an Android device like I did.

Once you have React Native, Android Studio and re-natal set up, you should be able to do the following once you have a device connected
via USB to your local environment.


`$ adb reverse tcp:8081 tcp:8081`

`$ adb reverse tcp:3449 tcp:3449`

If you want to use the stock Android emulator (AVD) replace "real" with "avd"

`$ re-natal use-android-device real`

`$ re-natal use-figwheel`

`$ lein figwheel android`

That should start [Figwheel](https://github.com/bhauman/lein-figwheel) (another amazing library). You then have to run
the following command to get React Packager to start and build the code (not sure
if I'm using the correct technical terminology here).

`$ react-native run-android`

This should launch the app on your device in a few seconds.

## License

Copyright © 2017

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
