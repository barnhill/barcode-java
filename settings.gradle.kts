@file:Suppress("UnstableApiUsage")

import java.io.IOException
import java.net.Socket


pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

plugins {
    id("com.gradle.develocity") version "4.0.2"
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

develocity {
    buildScan {
        termsOfUseUrl = "https://gradle.com/terms-of-service"
        termsOfUseAgree = "yes"
    }
}

val remoteCacheUrl: String? by extra
val cacheUrl: String? = if (System.getenv("REMOTE_CACHE_URL") == null) remoteCacheUrl else System.getenv("REMOTE_CACHE_URL")

if (cacheUrl != null) {
    buildCache {
        local {
            isEnabled = true
        }
        remote<HttpBuildCache> {
            url = uri(cacheUrl)
            isEnabled = isOnline()
            isPush = true
            isAllowUntrustedServer = true
            isAllowInsecureProtocol = false
            if (isEnabled) {
                println("Using remote build cache: $cacheUrl")
            } else {
                println("Not using remote build cache!")
            }

            val remoteCacheUser: String? by extra
            val remoteCachePass: String? by extra
            credentials {
                username = if (System.getenv("REMOTE_CACHE_USER") == null) remoteCacheUser as String else System.getenv("REMOTE_CACHE_USER")
                password = if (System.getenv("REMOTE_CACHE_PASS") == null) remoteCachePass as String else System.getenv("REMOTE_CACHE_PASS")
            }
        }
    }
} else {
    println("Not using remote build cache!")
}

private fun isOnline(): Boolean {
    try {
        // Attempt to create a socket connection to a well-known server (e.g., Cloudflare DNS)
        // This is a common way to check for basic internet connectivity.
        // Adjust the host and port as needed for your network environment.
        Socket("1.1.1.1", 53).close()
        println("Internet connection test passed")
        return true
    } catch (_: IOException) {
        println("Internet connection test failed, offline mode")
        return false
    }
}

rootProject.name = "barcode"
