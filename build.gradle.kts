// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories{
        google()
        mavenCentral()

    }
    dependencies{
        classpath("androidx.navigation.safeargs:androidx.navigation.safeargs.gradle.plugin:2.7.7")
    }
}
plugins {
    id("com.android.application") version "8.2.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
}