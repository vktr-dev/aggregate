buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    id("com.android.application") version ("8.1.0") apply (false)
    id("com.android.library") version ("8.1.0") apply (false)
    id("com.google.dagger.hilt.android") version("2.47") apply(false)
    id("com.google.devtools.ksp") version("1.9.0-1.0.12") apply(false)
    kotlin("android") version("1.9.0") apply(false)
    kotlin("plugin.serialization") version("1.9.0") apply(false)
}