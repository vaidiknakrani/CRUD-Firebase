plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.crud_firebase"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.crud_firebase"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    // UI
    implementation("androidx.appcompat:appcompat:1.7.0")          // latest stable close to yours [web:13]
    implementation("com.google.android.material:material:1.12.0") // stable instead of alpha [web:13]
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")

    // Firebase BOM (platform, not a normal dependency)
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    implementation("com.google.firebase:firebase-firestore")

    // Jetpack DataStore (needed by Firebase heartbeat / Firestore)
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    // Test
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}