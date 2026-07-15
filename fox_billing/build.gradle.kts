plugins {
    id("fxc.android.library")
}

android {
    namespace = "fxc.dev.fox_billing"
}

dependencies {
    implementation(project(":common"))
    implementation(project(":fox_tracking"))

    // Common
    implementation(libs.androidx.startup)
    implementation(libs.androidx.lifecycle.process)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.timber)

    // Billing (using KTX for Kotlin)
    implementation(libs.billing.ktx)
}
