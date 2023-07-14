buildscript {
    extra["gradleVersion"] = "8.2.1"
}

val gradleVersion: String by extra

plugins {
    id("java")
    id("signing")
    id("maven-publish")
    id("com.vanniktech.maven.publish") version "0.25.3"
    id("org.jetbrains.dokka") version "1.8.20"
}

group = "com.pnuema.java"
version = project.properties["VERSION_NAME"].toString()

/*dependencies {
    testImplementation group: "junit", name: "junit", version: "4.13.2"
}*/

val dokkaOutputDir = buildDir.resolve("docs")
tasks {
    wrapper {
        gradleVersion = gradleVersion
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
        dependsOn.add(javadoc)
        dependsOn.add(dokkaJavadoc)
        archiveClassifier.set("javadoc")
        from(dokkaOutputDir)
    }

    test {
        useJUnitPlatform()
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        withSourcesJar()
        withJavadocJar()
    }

    artifacts {
        archives(jar)
        archives(sourcesJar)
        archives(javadocJar)
    }

    build {
        dependsOn(javadocJar)
    }
}