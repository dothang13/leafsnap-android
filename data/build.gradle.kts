@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("fxc.android.library")
    id("fxc.android.hilt")
    id("fxc.android.room")
    id(libs.plugins.kotlin.parcelize.get().pluginId)
    id(libs.plugins.kotlin.serialization.get().pluginId)
}

android {
    namespace = "fxc.dev.data"
}

dependencies {
    implementation(project(":common"))
    implementation(project(":secrets"))

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.okhttp.logging)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.timber)
}