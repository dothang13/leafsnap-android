import com.android.build.api.dsl.ApplicationExtension
import com.fxc.compositebuild.configureKotlinAndroid
import com.fxc.compositebuild.libs
import com.fxc.compositebuild.plugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = target.run {
        pluginManager.run {
            apply(libs.plugin("kotlin.android").pluginId)
            apply(libs.plugin("android.application").pluginId)
        }

        extensions.configure<ApplicationExtension> {
            configureKotlinAndroid(this)
            defaultConfig.targetSdk = ProjectConfigure.targetSdk
        }
    }
}
