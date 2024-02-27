plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    // id("com.google.gms.google-services")
    id("com.google.devtools.ksp")
    id("de.mannodermaus.android-junit5")
    idea
}

repositories {
    google()
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
    maven {
        name = "GitHubPackages"
        url = uri("https://maven.pkg.github.com/minikorp/duo")
    }
}

android {
    compileSdk = Constants.compileSdkVersion
    buildToolsVersion = "33.0.0"

    namespace = "com.durdinstudios.goonwarscollector"

    defaultConfig {
        minSdk = Constants.minSdkVersion
        targetSdk = Constants.targetSdkVersion
        versionCode = 2
        versionName = "0.0.2"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Constants.composeCompilerVersion
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
        // Flag to enable support for the new language APIs
        isCoreLibraryDesugaringEnabled = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf(
            "-opt-in=androidx.compose.ui.ExperimentalComposeUiApi",
            "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
            "-opt-in=androidx.compose.material.ExperimentalMaterialApi",
            "-opt-in=com.google.accompanist.pager.ExperimentalPagerApi",
            "-opt-in=com.google.accompanist.permissions.ExperimentalPermissionsApi",
            "-opt-in=androidx.compose.ui.text.ExperimentalTextApi",
            "-Xjvm-default=all"
        )
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {
    /** Desugaring lib to ensure compatibility in older Android devices */
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5") {
        because("We need to upgrade the Android Gradle plug-in to 7.4+ to update this lib. Check this when updating the plug-in.")
    }

    //Mini
    val duo_version = "1.0.2"
    implementation("com.minikorp.duo:duo:$duo_version")
    ksp("com.minikorp.duo:ksp:$duo_version")

    implementation("com.prof18.rssparser:rssparser:6.0.0")

    /** Kotlin and coroutines */
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Constants.kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${Constants.kotlinVersion}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Constants.kotlinxCoroutinesVersion}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Constants.kotlinxCoroutinesVersion}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${Constants.kotlinxCoroutinesVersion}")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${Constants.kotlinxCoroutinesVersion}")

    /**
     * Dependency injection: Kodein
     * TODO: 22/9/2021 In the future, these dependencies will be included in Mini
     */
    implementation("org.kodein.di:kodein-di-jvm:${Constants.kodeinVersion}")
    implementation("org.kodein.di:kodein-di-framework-android-x:${Constants.kodeinVersion}")
    implementation("org.kodein.di:kodein-di-conf-jvm:${Constants.kodeinVersion}")
    implementation("org.kodein.di:kodein-di-framework-compose:${Constants.kodeinVersion}")

    /** Android support core and architecture libs */
    implementation("androidx.appcompat:appcompat:${Constants.appcompatVersion}")
    implementation("androidx.appcompat:appcompat-resources:${Constants.appcompatVersion}")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.annotation:annotation:1.6.0")
    implementation("androidx.core:core-splashscreen:1.0.0")

    /** Compose */
    implementation("androidx.compose.ui:ui:${Constants.composeUiVersion}")
    implementation("androidx.compose.runtime:runtime:${Constants.composeUiVersion}")
    implementation("androidx.compose.runtime:runtime-livedata:${Constants.composeUiVersion}")
    //Tooling support (Previews, etc)
    implementation("androidx.compose.ui:ui-tooling:${Constants.composeUiVersion}")
    //Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc)
    implementation("androidx.compose.foundation:foundation:${Constants.composeFoundationVersion}")
    //Material Design
    implementation("androidx.compose.material:material:${Constants.composeMaterialVersion}")
    //Material design icon
    implementation("androidx.compose.material:material-icons-core:${Constants.composeMaterialVersion}")

    //Accompanist
    implementation("com.google.accompanist:accompanist-swiperefresh:${Constants.accompanistVersion}")
    implementation("com.google.accompanist:accompanist-insets:${Constants.accompanistVersion}")
    implementation("com.google.accompanist:accompanist-systemuicontroller:${Constants.accompanistVersion}")
    implementation("com.google.accompanist:accompanist-placeholder-material:${Constants.accompanistVersion}")
    implementation("com.google.accompanist:accompanist-permissions:${Constants.accompanistVersion}")
    implementation("com.google.accompanist:accompanist-navigation-animation:${Constants.accompanistVersion}")

    implementation("com.github.skydoves:landscapist-glide:2.1.9") {
        because("Landscapist 2.1.10+ requires compiling against Java 17. Review when we update Java versions")
    }

    /** Android UI libs */
    implementation("com.google.android.material:material:1.8.0")

    /** Network utils */
    implementation("com.squareup.retrofit2:converter-moshi:${Constants.retrofitVersion}")
    implementation("com.squareup.retrofit2:adapter-rxjava2:${Constants.retrofitVersion}")
    implementation("com.squareup.retrofit2:retrofit:${Constants.retrofitVersion}")
    implementation("com.squareup.retrofit2:retrofit-mock:${Constants.retrofitVersion}")
    implementation("com.squareup.okhttp3:logging-interceptor:${Constants.okhttpVersion}")
    implementation("com.squareup.okhttp3:okhttp:${Constants.okhttpVersion}")

    /** Moshi */
    implementation("com.squareup.moshi:moshi:${Constants.moshiVersion}")
    implementation("com.squareup.moshi:moshi-kotlin:${Constants.moshiVersion}")
    implementation("com.squareup.moshi:moshi-adapters:${Constants.moshiVersion}")
    implementation("dev.zacsweers.moshix:moshi-sealed-runtime:${Constants.moshiSealedVersion}")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:${Constants.moshiVersion}")
    ksp("dev.zacsweers.moshix:moshi-sealed-codegen:${Constants.moshiSealedVersion}")

    // WalletConnect
    //  implementation(platform("v:1.7.0"))
    //  implementation("com.walletconnect:android-core")
    //  implementation("com.walletconnect:auth")
    //  implementation("com.walletconnect:web3wallet")

    implementation("com.github.TrustWallet:wallet-connect-kotlin:1.5.6")

    /** Chucker */
    debugImplementation("com.github.ChuckerTeam.Chucker:library:${Constants.chuckerVersion}")
    releaseImplementation("com.github.ChuckerTeam.Chucker:library-no-op:${Constants.chuckerVersion}")

    /** Log */
    implementation("com.github.minikorp:grove:1.0.3")

    /**
     * Testing
     */
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    testImplementation("junit:junit:4.13.2")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.9.2")

    testImplementation("androidx.arch.core:core-testing:2.2.0")

    // Test rules and transitive dependencies:
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${Constants.composeUiVersion}")
    // Needed for createComposeRule, but not createAndroidComposeRule:
    debugImplementation("androidx.compose.ui:ui-test-manifest:${Constants.composeUiVersion}")

    testImplementation("org.mockito:mockito-core:${Constants.mockitoVersion}")
    androidTestImplementation("org.mockito:mockito-android:${Constants.mockitoVersion}")
    androidTestImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
    testImplementation("org.mockito:mockito-inline:${Constants.mockitoVersion}")

    androidTestImplementation("androidx.test:runner:1.5.2")
    // Kakao Compose version
    androidTestImplementation("io.github.kakaocup:compose:0.2.2")

    androidTestImplementation("androidx.test.espresso:espresso-core:${Constants.espressoVersion}")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:${Constants.espressoVersion}")
    androidTestImplementation("androidx.test.espresso:espresso-intents:${Constants.espressoVersion}")
    androidTestImplementation("androidx.test.espresso:espresso-web:${Constants.espressoVersion}")

    androidTestImplementation("androidx.test.uiautomator:uiautomator:2.2.0")

    implementation("androidx.core:core-splashscreen:1.0.0")

    implementation("androidx.appcompat:appcompat-resources:${Constants.appcompatVersion}")
}

afterEvaluate {
    kotlin.sourceSets.forEach { sourceSet ->
        sourceSet.kotlin.srcDir("build/generated/ksp/${sourceSet.name}/kotlin")
    }
}
