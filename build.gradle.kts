import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    extra["gradle"] = "8.2.1"
    extra["javaVersion"] = JavaVersion.VERSION_11
}

val gradle: String by extra
val javaVersion: JavaVersion by extra

plugins {
    id("java")
    id("signing")

    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.dokka)
    alias(libs.plugins.maven.publish)
    alias(libs.plugins.gradle.cachefix).apply(false)
}

group = "com.pnuema.java"
version = project.properties["VERSION_NAME"].toString()

dependencies {
    implementation(libs.dokka.gradle)
    implementation(libs.kotlin.gradle)
    testImplementation(libs.junit)
}

val dokkaOutputDir = buildDir.resolve("docs")
tasks {
    wrapper {
        gradleVersion = gradle
        distributionType = Wrapper.DistributionType.BIN
    }
    jar {
        manifest {
            attributes["Implementation-Title"] = "Barcode for Java"
            attributes["Implementation-Version"] = archiveVersion
            attributes["Implementation-Vendor"] = "Brad Barnhill"
        }
    }

    val sourcesJar by creating(Jar::class) {
        archiveClassifier.set("sources")
        from(sourceSets["main"].allSource)
    }

    val javadocJar by creating(Jar::class) {
        dependsOn.add(dokkaHtml)
        archiveClassifier.set("javadoc")
        from(dokkaOutputDir)
    }

    test {
        useJUnitPlatform()
    }

    java {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
        withSourcesJar()
        withJavadocJar()
    }

    dokkaHtml {
        outputDirectory.set(file(dokkaOutputDir))
        dokkaSourceSets {
            named("main")
        }
    }

    compileKotlin {
        kotlinOptions {
            jvmTarget = javaVersion.toString()
        }
    }

    compileTestKotlin {
        kotlinOptions {
            jvmTarget = javaVersion.toString()
        }
    }

    artifacts {
        archives(jar)
        archives(sourcesJar)
        archives(javadocJar)
    }

    afterEvaluate {
        tasks.named("generateMetadataFileForMavenPublication") {
            dependsOn.add(tasks.named("dokkaJavadocJar"))
            dependsOn.add(tasks.named("kotlinSourcesJar"))
        }
    }
}