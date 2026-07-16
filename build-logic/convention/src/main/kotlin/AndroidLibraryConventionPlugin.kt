import com.android.build.gradle.LibraryExtension
import com.fxc.compositebuild.configureKotlinAndroid
import com.fxc.compositebuild.libs
import com.fxc.compositebuild.plugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = target.run {
        pluginManager.run {
            apply(libs.plugin("kotlin.android").pluginId)
            apply(libs.plugin("android.library").pluginId)
        }

        extensions.configure<LibraryExtension> {
            configureKotlinAndroid(this)
            defaultConfig.targetSdk = ProjectConfigure.targetSdk
        }
    }
}
