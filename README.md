# Flickr-Recents [![Build status](https://build.appcenter.ms/v0.1/apps/b82df14e-7992-433c-9cbd-1b3d1c6e45ea/branches/master/badge)](https://appcenter.ms)

This is a simple app that presents the user with a scrollable stream of images from [Flickr Recent photos API](https://www.flickr.com/services/api/flickr.photos.getRecent.htm). The app doesn't require any [dangerous permissions](https://developer.android.com/training/permissions/requesting) or to logging in.

The code has been kept modular, extensible, and testable by using interfaces for all third-party libraries and design patterns including IOC/dependency injection where ever needed. This extensibility and modularity greatly help in swapping various concrete implementations and eases app maintenance. More on good android design principles [here](https://medium.com/elevate-by-lateral-view/design-principles-on-android-703c9387d6d7).

I have marked some issues I faced while writing unit test cases to investigate/study/resolve for myself as TODO. The unit tests written so far can be found under ~app/src/test.

I look forward to your feedback and comments on the code, architecture improvements and/or any general suggestions.

#### Getting started

#### 1. Open the project in `Android Studio`

Select `Open Existing Project` from the options. To install `Android Studio` please follow the instructions [here](https://developer.android.com/sdk/installing/studio.html)

#### 2. Running project on a device

After the project has been successfully imported it will build itself automatically.
Connect the mobile device and press the `Run 'app'` button from `Run menu`.
Please also note the `min supported API level is 23 (Android 6.0, Jelly Bean)`.

More instructions [here](https://developer.android.com/training/basics/firstapp/running-app.html)

### Formatting rules followed:
* Column width 100
* Field instance members are prefixed with 'm' e.g. mTextView
* Static members are prefixed with 's'
* Constants are private (unless needed elsewhere), static and all in uppercase, e.g. FADE_ANIMATION_OUT_MILLIS

### Naming conventions

String ids:

`<activity/fragment/view/global>_<activity_name>__<string name>` e.g. activity_home_welcome_message

If a string is used in multiple places, use the 'global' prefix rather than the screen name.

Layout ids:

`<activity/fragment/view>_<activity_name>_<type>_<description>` e.g. activity_home_textview_welcome_message

### Third-party Libraries used
* `Retrofit` - https://github.com/square/retrofit
* `Butterknife` - https://github.com/JakeWharton/butterknife
* `EventBus` - https://github.com/greenrobot/EventBus
* `Picasso` - https://github.com/square/picasso
* `gson` - https://github.com/google/gson
* `JUnit` - https://github.com/junit-team/junit4
* `Mockito` - https://github.com//mockito/mockito
* `Hamcrest` - https://github.com/hamcrest/JavaHamcrest
* `Robolectric` - https://github.com/robolectric/robolectric
