@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

plugins {
    id("com.gradle.develocity") version "3.17.5"
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
            removeUnusedEntriesAfterDays = 30
        }

        remote<HttpBuildCache> {
            url = uri(cacheUrl)
            isEnabled = true
            isPush = true
            isAllowUntrustedServer = true
            isAllowInsecureProtocol = false
            if (isEnabled) {
                println("Using remote build cache: $cacheUrl")
            }

            val remoteCacheUser: String? by extra
            val remoteCachePass: String? by extra
            credentials {
                username = if (System.getenv("REMOTE_CACHE_USER") == null) remoteCacheUser else System.getenv("REMOTE_CACHE_USER")
                password = if (System.getenv("REMOTE_CACHE_PASS") == null) remoteCachePass else System.getenv("REMOTE_CACHE_PASS")
            }
        }
    }
} else {
    println("Not using remote build cache!")
}

rootProject.name = "barcode"
