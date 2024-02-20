
plugins {
    `android-library`
    `kotlin-android`
}

apply<MainGradlePlugin>()

android {
    namespace = "com.igordudka.data"
}

dependencies {

    retrofit()
    room()
    hilt()
}