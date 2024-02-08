import org.jetbrains.compose.ExperimentalComposeLibrary

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {

        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.ktor.client.okhttp)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)

        }

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.animation)
            implementation(compose.material)
            implementation(compose.ui)
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.negotiation)
            implementation(libs.ktor.client.json)
            implementation(libs.ktor.client.logging)

            implementation(libs.kmpalette.core)

            implementation(libs.kmpalette.extensions.network)

            implementation(libs.kamel)
            api("io.insert-koin:koin-core:3.5.0")
            api("io.insert-koin:koin-compose:1.1.0")
//            //Voyagar
//            // Navigator
//            implementation(libs.voyagarNavigator)
//            // Screen Model
//            implementation(libs.voyagarScreenModel)
//            // BottomSheetNavigator
//            implementation(libs.voyagarBottomSheetNavigator)
//            // TabNavigator
//            implementation(libs.voyagarTabNavigator)
//            // Transitions
//            implementation(libs.voyagarTabTransitions)

            //PreCompose
            api("moe.tlaster:precompose:1.5.10")
            //api("moe.tlaster:precompose-molecule:1.5.10")
            api("moe.tlaster:precompose-viewmodel:1.5.10")
            api("moe.tlaster:precompose-koin:1.5.10")

        }
    }
}

android {
    namespace = "org.example.project"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "org.example.project"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }
}

