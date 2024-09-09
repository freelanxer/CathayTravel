// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    // Hilt
    alias(libs.plugins.hiltAndroid) apply false
    // Kapt
    alias(libs.plugins.kotlin.kapt) apply false
}