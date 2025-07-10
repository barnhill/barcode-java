buildscript {
    extra["gradle"] = "8.14.3"
    extra["javaVersion"] = JavaVersion.VERSION_11
}

val gradle: String by extra
val javaVersion: JavaVersion by extra

plugins {
    id("java")
    id("signing")

    alias(libs.plugins.dokka)
    alias(libs.plugins.maven.publish)
    alias(libs.plugins.gradle.cachefix).apply(false)
}

group = "com.pnuema.java"
version = project.properties["VERSION_NAME"].toString()

dependencies {
    implementation(libs.dokka.gradle)
    testImplementation(libs.junit)
}

val dokkaOutputDir = layout.buildDirectory.asFile.get().resolve("docs")
dokka {
    moduleName.set(project.properties["POM_NAME"].toString())
    dokkaSourceSets.javaMain {
        sourceLink {
            localDirectory.set(file("src/main/kotlin"))
            remoteUrl(project.properties["POM_URL"].toString())
        }
        perPackageOption {
            matchingRegex.set(".*utils.*") // will match all utils packages and sub-packages and skip Javadoc for them
            suppress.set(true)
        }
    }
    pluginsConfiguration.html {
        footerMessage.set("(c) " + project.properties["POM_DEVELOPER_NAME"].toString())
    }
    dokkaPublications.html {
        outputDirectory.set(dokkaOutputDir)
    }
}

tasks {
    wrapper {
        gradleVersion = gradle
        distributionType = Wrapper.DistributionType.BIN
    }
    jar {
        manifest {
            attributes["Implementation-Title"] = "Barcode for Java"
            attributes["Implementation-Version"] = archiveVersion
            attributes["Implementation-Vendor"] = project.properties["POM_DEVELOPER_NAME"].toString()
        }
    }

    val sourcesJar by registering(Jar::class, fun Jar.() {
        archiveClassifier.set("sources")
        from(sourceSets["main"].allSource)
    })

    val javadocJar by registering(Jar::class, fun Jar.() {
        dependsOn.add(javadoc)
        dependsOn.add(dokkaGenerate)
        archiveClassifier.set("javadoc")
        from(dokkaOutputDir)
    })

    test {
        useJUnitPlatform()
    }

    java {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    artifacts {
        archives(jar)
        archives(sourcesJar)
        archives(javadocJar)
    }

    afterEvaluate {
        tasks.named("generateMetadataFileForMavenPublication") {
            dependsOn.add(tasks.named("dokkaGenerate"))
            dependsOn.add(tasks.named("sourcesJar"))
        }
    }
}