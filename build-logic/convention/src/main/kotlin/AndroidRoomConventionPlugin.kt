import com.fxc.compositebuild.get
import com.fxc.compositebuild.implementation
import com.fxc.compositebuild.ksp
import com.fxc.compositebuild.libs
import com.fxc.compositebuild.plugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidRoomConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = target.run {
        pluginManager.apply(libs.plugin("ksp").pluginId)

        dependencies {
            implementation(libs["room.runtime"])
            implementation(libs["room.ktx"])
            ksp(libs["room.compiler"])
        }
    }
}
