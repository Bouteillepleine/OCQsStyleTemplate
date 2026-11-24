plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.ocqsstyletemplate"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.example.ocqsstyletemplate"
        minSdk = 33
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            isShrinkResources = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    lint {
        disable += "UnusedResources"
    }
}
