plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.jetbrains.kotlin.plugin.serialization)
    alias(libs.plugins.google.dagger.hilt.android) apply false
    alias(libs.plugins.jetbrains.kotlin.plugin.compose) apply false
    alias(libs.plugins.android.library) apply false
}
allprojects {
    repositories {
        maven { url = uri("https://jitpack.io") }
    }
}