# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in <android-sdk>/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# https://github.com/square/retrofit/issues/1584
-keepnames class rx.Single

# TODO change to match your package model
# Keep non static or private fields of models so Gson can find their names
-keepclassmembers class ua.anironglass.boilerplate.data.model.** {
    !static !private <fields>;
}
# TODO change to match your Retrofit services (only if using inner models withing the service)
# Some models used by gson are inner classes inside the retrofit service
-keepclassmembers class ua.anironglass.boilerplate.data.remote.ApiServicee$** {
    !static !private <fields>;
}