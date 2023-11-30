import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
//    `java-library`
	kotlin("jvm") version "1.7.0"
	kotlin("plugin.serialization") version "1.7.0"
}

group = "com.kousenit"
version = "1.0"

val scriptname: String by project  // read value from gradle.properties

repositories {
	mavenCentral()
}

dependencies {
	implementation(kotlin("reflect"))
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")

	implementation("org.apache.commons:commons-math3:3.6.1")
	implementation("com.google.code.gson:gson:2.9.0")
	implementation("commons-validator:commons-validator:1.7")

	implementation("io.ktor:ktor-server-core:2.3.6")
	implementation("io.ktor:ktor-server-netty:2.3.6")

	testImplementation("org.hamcrest:hamcrest:2.2")
	testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
	testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.1")
	testImplementation(kotlin("test-junit5"))

	implementation(kotlin("script-runtime"))
	implementation(kotlin("stdlib-jdk8"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
}

java {
	sourceCompatibility = JavaVersion.VERSION_11
	targetCompatibility = JavaVersion.VERSION_11
}

tasks.named<Test>("test") {
	useJUnitPlatform {
		maxParallelForks = Runtime.getRuntime().availableProcessors() / 2 + 1
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		jvmTarget = "11"
		freeCompilerArgs = listOf("-Xjsr305=strict")
		suppressWarnings = true
	}
}
