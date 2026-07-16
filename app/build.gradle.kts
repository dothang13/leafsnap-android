@file:Suppress("UnstableApiUsage")

import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import java.io.FileInputStream
import java.util.Locale
import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("fxc.android.application")
    id("fxc.android.hilt")
    id("fxc.android.application.firebase")
    id(
        libs.plugins.kotlin.parcelize
            .get()
            .pluginId
    )
    id(
        libs.plugins.kotlin.serialization
            .get()
            .pluginId
    )
}

val keystorePropertiesFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

android {
    namespace = "fxc.dev.app"

    defaultConfig {
        applicationId = "com.led.keyboard.neon.classic"
        versionCode = 1
        versionName = "1.0"

        multiDexEnabled = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        getByName("debug") {
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            storeFile = file(keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["storePassword"] as String
        }

        create("release") {
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            storeFile = file(keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["storePassword"] as String
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isDebuggable = false
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            isMinifyEnabled = false
            isDebuggable = true
            signingConfig = signingConfigs.getByName("debug")
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro"
            )
        }

        applicationVariants.all {
            val outputFileName = rootProject.name.replace(" ", "-") +
                "-${name.uppercase(Locale.getDefault())}" +
                ".apk"
            outputs.all {
                val output = this as? BaseVariantOutputImpl
                output?.outputFileName = outputFileName
            }
        }
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":common"))
    implementation(project(":data"))
    implementation(project(":fox_ads"))
    implementation(project(":fox_billing"))
    implementation(project(":fox_tracking"))

    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.work.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.constraint)
    implementation(libs.androidx.annotation)

    // Lifecycle
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.extensions)
    implementation(libs.androidx.lifecycle.process)

    // Play service
    implementation(libs.play.review)
    implementation(libs.play.app.update)
    implementation(libs.gms.ads)

    // Billing
    implementation(libs.billing.ktx)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)

    // Kotpref
    implementation(libs.kotpref.core)
    implementation(libs.kotpref.initializer)
    implementation(libs.kotpref.enumSupport)

    // Remote Konfig
    implementation(libs.remote.konfig)

    // Chucker
    debugImplementation(libs.chucker)
    releaseImplementation(libs.chucker.no.op)

    // fox
    implementation(libs.fox.common)

    // Others
    implementation(libs.timber)
    implementation(libs.material)
    implementation(libs.glide)
}
