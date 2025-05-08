/*
 * Copyright 2023 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("plugin.compose")
    id("com.vanniktech.maven.publish")
}

android {
    namespace = "dev.bartuzen.compose.preference"
    buildToolsVersion = "35.0.0"
    compileSdk = 35
    defaultConfig {
        minSdk = 21
        consumerProguardFiles("proguard-rules.pro")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions { jvmTarget = JavaVersion.VERSION_1_8.toString() }
    buildFeatures { compose = true }
    packaging { resources { excludes += "/META-INF/{AL2.0,LGPL2.1}" } }
    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation(platform("androidx.compose:compose-bom:2024.11.00"))
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.7")
}

publishing {
    repositories {
        maven {
            name = "GitLab"
            url = uri ("https://gitlab.com/api/v4/projects/69663547/packages/maven")
            credentials(HttpHeaderCredentials::class) {
                name = "Private-Token"
                value = findProperty("gitlabToken") as String? ?: System.getenv("GITLAB_TOKEN")
            }
            authentication {
                create<HttpHeaderAuthentication>("header")
            }
        }
    }
}

mavenPublishing {
    signAllPublications()
    coordinates("dev.bartuzen", "composepreference", "1.1.1")
    pom {
        name.set("ComposePreference")
        description.set("Compose Preference library")
        inceptionYear.set("2023")
        url.set("https://github.com/Bartuzen/ComposePreference")
        licenses {
            license {
                name.set("The Apache Software License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        developers {
            developer {
                id.set("Bartuzen")
                name.set("Bartu Özen")
                url.set("https://github.com/Bartuzen")
            }
        }
    }
}