@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.twofasAndroidLibrary)
    alias(libs.plugins.twofasCompose)
}

android {
    namespace = "com.twofasapp.developer"
}

dependencies {
    implementation(project(":base"))
    implementation(project(":di"))
    implementation(project(":resources"))
    implementation(project(":extensions"))
    implementation(project(":design"))
    implementation(project(":environment"))
    implementation(project(":prefs"))
    implementation(project(":featuretoggle"))
    implementation(project(":push"))

    implementation(libs.bundles.appCompat)
    implementation(libs.bundles.compose)
    implementation(libs.kotlinCoroutines)
    implementation(libs.bundles.viewModel)
}