plugins {
    id("fxc.android.library")
    id(
        libs.plugins.kotlin.serialization
            .get()
            .pluginId
    )
}

android {
    namespace = "fxc.dev.fox_tracking"
}

dependencies {
    implementation(project(":common"))

    // Common
    implementation(libs.androidx.startup)
    implementation(libs.timber)
    implementation(libs.androidx.lifecycle.process)

    // Firebase analytics tracking
    implementation(platform(libs.firebase.bom)) // version 32.5.0
    implementation(libs.firebase.analytics)

    // In-House tracking
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.okhttp.logging)

    // AppsFlyer tracking
    implementation(libs.appsFlyer) // version 6.9.2

    // Adjust tracking
    implementation(libs.adjust) // version 4.38.5
    implementation(libs.installReferrer) // version 2.2
    implementation(libs.gms.adsIdentifier) // version 18.0.1
    implementation(libs.gms.appset) // version 16.0.2
}
