
pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
        maven {
            url = uri("https://artifact.bytedance.com/repository/pangle/")
        }
        maven {
            url = uri("https://dl-maven-android.mintegral.com/repository/mbridge_android_sdk_oversea")
        }
    }
}

rootProject.name = "LeafSnap" // Đặt tên cho project theo mã dự án + tên app

include(":app")
include(":common")
include(":data")
include(":fox_ads")
include(":fox_billing")
include(":fox_tracking")
include(":secrets")
