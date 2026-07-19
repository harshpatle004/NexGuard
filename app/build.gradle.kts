plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.nexguard"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.example.nexguard"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }


    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // ----------------------------
    // Core Android
    // ----------------------------
    implementation("androidx.core:core-ktx:1.17.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.9.2")
    implementation("androidx.activity:activity-compose:1.11.0")

    // ----------------------------
    // Compose
    // ----------------------------
    implementation(platform("androidx.compose:compose-bom:2025.06.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2025.06.00"))

    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // ----------------------------
    // Navigation
    // ----------------------------
    implementation("androidx.navigation:navigation-compose:2.9.3")

    // ----------------------------
    // Lifecycle
    // ----------------------------
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.9.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.2")

    // ----------------------------
    // Coroutines
    // ----------------------------
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")

    // ----------------------------
    // Firebase
    // ----------------------------
    implementation(platform("com.google.firebase:firebase-bom:34.2.0"))

    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-messaging")

    // ----------------------------
    // Google Sign-In
    // ----------------------------
    implementation("androidx.credentials:credentials:1.5.0")
    implementation("androidx.credentials:credentials-play-services-auth:1.5.0")
    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.1")

    // ----------------------------
    // CameraX
    // ----------------------------
    implementation("androidx.camera:camera-core:1.5.0")
    implementation("androidx.camera:camera-camera2:1.5.0")
    implementation("androidx.camera:camera-lifecycle:1.5.0")
    implementation("androidx.camera:camera-view:1.5.0")
    implementation("androidx.camera:camera-video:1.5.0")

    // ----------------------------
    // WebRTC
    // ----------------------------
    implementation("io.github.webrtc-sdk:android:125.6422.07")

    // ----------------------------
    // Socket.IO
    // ----------------------------
    implementation("io.socket:socket.io-client:2.1.2") {
        exclude(group = "org.json", module = "json")
    }

    // ----------------------------
    // Networking
    // ----------------------------
    implementation("com.squareup.okhttp3:okhttp:5.1.0")
    implementation("com.google.code.gson:gson:2.13.1")

    // ----------------------------
    // Image Loading
    // ----------------------------
    implementation("io.coil-kt:coil-compose:2.7.0")

    // ----------------------------
    // Logging
    // ----------------------------
    implementation("com.jakewharton.timber:timber:5.0.1")

    // ----------------------------
    // Splash Screen
    // ----------------------------
    implementation("androidx.core:core-splashscreen:1.0.1")

    // ----------------------------
    // DataStore
    // ----------------------------
    implementation("androidx.datastore:datastore-preferences:1.1.7")

    // ----------------------------
    // WorkManager
    // ----------------------------
    implementation("androidx.work:work-runtime-ktx:2.10.3")

    // ----------------------------
    // Permissions
    // ----------------------------
    implementation("com.google.accompanist:accompanist-permissions:0.37.3")

    // ----------------------------
    // Testing
    // ----------------------------
    testImplementation("junit:junit:4.13.2")

    androidTestImplementation("androidx.test.ext:junit:1.3.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.7.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    implementation("io.github.webrtc-sdk:android:125.6422.07")

    implementation("io.socket:socket.io-client:2.1.2") {
        exclude(group = "org.json", module = "json")
    }
}