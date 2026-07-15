
import com.fxc.compositebuild.get
import com.fxc.compositebuild.implementation
import com.fxc.compositebuild.kapt
import com.fxc.compositebuild.libs
import com.fxc.compositebuild.plugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidHiltConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = target.run {
        pluginManager.run {
            apply(libs.plugin("kotlin.kapt").pluginId)
            apply(libs.plugin("hilt").pluginId)
        }

        dependencies {
            implementation(libs["hilt.android"])
            kapt(libs["hilt.compiler"])
        }
    }
}
