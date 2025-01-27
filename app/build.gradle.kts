plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.devtools.ksp")
    id("kotlin-kapt")
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.bottomnavigation"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.bottomnavigation"
        minSdk = 28
        targetSdk = 35
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
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures{
        viewBinding = true
    }
}

dependencies {
    implementation(libs.firebase.messaging)
    val room_version = "2.6.1"
    val nav_version = "2.8.4"
    val lottieVersion = "3.4.0"
    //lottie
    implementation ("com.airbnb.android:lottie:$lottieVersion")
    //
    implementation ("com.google.android.material:material:1.9.0")
    //room
    implementation("androidx.room:room-ktx:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    //ksp("androidx.room:room-compiler:$room_version")
    //firestore
    implementation("com.google.firebase:firebase-firestore")
    //firebase
    implementation(libs.firebase.auth)
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    implementation("com.google.android.gms:play-services-auth:21.3.0")
    //navigation
    implementation ("androidx.navigation:navigation-fragment:$nav_version")
    implementation ("androidx.navigation:navigation-ui:$nav_version")
    //other
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}