plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.lab4_ex1"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.lab4_ex1"
        minSdk = 29
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation("androidx.compose.material:material-icons-extended:1.7.5")
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.play.services.maps)
    implementation(libs.androidx.benchmark.traceprocessor.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
   // implementation(libs.maps.compose)
   // implementation(libs.maps.compose)
    // compose maps library
    implementation("com.google.maps.android:maps-compose:6.2.1")
    // implementation (com.google.maps.android:maps-compose:6.2.1)

    // Optionally, you can include the Compose utils library for Clustering,
    // Street View metadata checks, etc.
    implementation ("com.google.maps.android:maps-compose-utils:6.2.1")

    // Optionally, you can include the widgets library for ScaleBar, etc.
    implementation ("com.google.maps.android:maps-compose-widgets:6.2.1")

    // Add Play Services Location so LocationServices is available
    implementation("com.google.android.gms:play-services-location:21.0.1")

}