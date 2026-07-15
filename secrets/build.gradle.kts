@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("fxc.android.library")
    id(libs.plugins.hiddensecrets.get().pluginId)
}

android {
    namespace = "f.x.c.secrets"

    // Enable NDK build
    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
        }
    }
}