@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.twofasAndroidLibrary)
    alias(libs.plugins.twofasCompose)
}

android {
    namespace = "com.twofasapp.security"
}

dependencies {
    implementation(project(":base"))
    implementation(project(":di"))
    implementation(project(":resources"))
    implementation(project(":extensions"))
    implementation(project(":design"))
    implementation(project(":prefs"))
    implementation(project(":navigation"))
    implementation(project(":featuretoggle"))
    implementation(project(":security:domain"))
    implementation(project(":time:domain"))

    implementation(libs.bundles.appCompat)
    implementation(libs.bundles.compose)
    implementation(libs.biometric)
}