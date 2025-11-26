// Top-level build file where you can add configuration options common to all sub-projects/modules.
import org.gradle.api.tasks.Delete

plugins {
    // No root-level plugins required for this project; module plugins live in app/build.gradle.kts
}

// Register a clean task
tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
