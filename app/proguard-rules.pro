#-------------------------------------------------
# JetPack Navigation
# This fixes:
# Caused by: androidx.fragment.app.Fragment$InstantiationException: Unable to instantiate fragment androidx.navigation.fragment.NavHostFragment: make sure class name exists
# Caused by: androidx.fragment.app.FragmentActivity$HostCallbacks: Unable to instantiate fragment com.google.android.gms.maps.SupportMapFragment: make sure class name exists
#-------------------------------------------------
-keepnames class androidx.navigation.fragment.NavHostFragment

# Kotlin serialization looks up the generated serializer classes through a function on companion
# objects. The companions are looked up reflectively so we need to explicitly keep these functions.
-keepclasseswithmembers class **.*$Companion {
    kotlinx.serialization.KSerializer serializer(...);
}
# If a companion has the serializer function, keep the companion field on the original type so that
# the reflective lookup succeeds.
-if class **.*$Companion {
  kotlinx.serialization.KSerializer serializer(...);
}
-keepclassmembers class <1>.<2> {
  <1>.<2>$Companion Companion;
}

-keepclassmembers class * implements android.os.Parcelable { *; }
-keepnames class * extends android.os.Parcelable
-keepnames class * extends java.io.Serializable

# Room
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-dontwarn androidx.room.paging.**

# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.* { *; }
-keepattributes Signature
-keepattributes Exceptions

# Fox Tracking
-keep class com.appsflyer.** { *; }

-keep class com.adjust.sdk.** { *; }

-keep class com.google.android.gms.common.ConnectionResult {
    int SUCCESS;
}

-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient {
    com.google.android.gms.ads.identifier.AdvertisingIdClient$Info getAdvertisingIdInfo(android.content.Context);
}

-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient$Info {
    java.lang.String getId();
    boolean isLimitAdTrackingEnabled();
}
-keep public class com.android.installreferrer.** { *; }

# Keep domain model
-keep class fxc.dev.core.domain.model.** { *; }
