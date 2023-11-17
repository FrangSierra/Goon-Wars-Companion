// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        mavenCentral()
        google()
    }

    val kotlin_version = Constants.kotlinVersion
    val ksp_version = "${kotlin_version}-1.0.9"

    extra.apply {
        set("kotlin_version", kotlin_version)
        set("detekt_version", "1.22.0")
        set("aboutlibraries_version", "10.6.1")
        set("ksp_version", ksp_version)
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.2.2") {
            because(
                "If we update to 7.4.2 the launcher icon generation and easylauncher plugin breaks. " +
                    "Take a look to this issue to see if it persists: https://github.com/usefulness/easylauncher-gradle-plugin/issues/408 " +
                    "Also upgrade the desugar lib to 2.0.2 when we finally upgrade the plug-in version."
            )
        }
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        //classpath("com.google.gms:google-services:4.3.15")
        classpath("de.mannodermaus.gradle.plugins:android-junit5:1.8.2.1")

        classpath("org.jetbrains.kotlin:kotlin-allopen:$kotlin_version")
        classpath("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:$ksp_version")
    }
}