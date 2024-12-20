plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.dacs3_uongngocthanh_22iteb071"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.dacs3_uongngocthanh_22iteb071"
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        viewBinding = true
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Use Firebase BOM to manage Firebase dependencies
    implementation(platform("com.google.firebase:firebase-bom:32.0.0"))

    // Add the Firebase dependencies
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-database-ktx")
    implementation ("com.google.firebase:firebase-storage:20.0.1")

//    implementation("com.google.firebase:firebase-database:21.0.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("com.github.bumptech.glide:glide:4.15.1") // Phiên bản mới nhất
    annotationProcessor("com.github.bumptech.glide:compiler:4.15.1") // Thêm dòng này



    implementation ("com.tbuonomo:dotsindicator:5.0")

    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.1")


}
