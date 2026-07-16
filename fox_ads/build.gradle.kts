plugins {
    id("fxc.android.library")
}

android {
    namespace = "fxc.dev.fox_ads"

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":common"))
    implementation(project(":fox_tracking"))

    // Common
    implementation(libs.androidx.constraint)
    implementation(libs.androidx.startup)
    implementation(libs.material)
    implementation(libs.timber)

    // Lifecycle
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.process)

    // Admob
    implementation(libs.gms.ads)
    implementation(libs.gms.adsIdentifier)
    implementation(libs.ump)
    implementation(libs.facebook.mediation)
    implementation(libs.pangle)
    implementation(libs.mintegral)
    implementation(libs.applovin)
    implementation(libs.facebook.mediation)
    implementation(libs.skeletonlayout)
}
