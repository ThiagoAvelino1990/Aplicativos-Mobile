// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.6.1")
        classpath("io.realm:realm-gradle-plugin:10.15.1")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}
