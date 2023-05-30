import com.google.protobuf.gradle.*

plugins {
    id("com.google.protobuf") version "0.8.13"
}
apply(plugin = "kotlin-jpa")

val grpcVersion = "3.19.4"
val grpcKotlinVersion = "1.2.1"
val grpcProtoVersion = "1.44.1"

dependencies {
    implementation("io.grpc:grpc-stub:1.46.0")
    implementation("io.grpc:grpc-kotlin-stub:$grpcKotlinVersion")
    implementation("io.grpc:grpc-protobuf:$grpcProtoVersion")
    implementation("com.google.protobuf:protobuf-kotlin:$grpcVersion")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.google.api.grpc:proto-google-common-protos:2.19.1")

}
sourceSets {
    getByName("main") {
        java {
            srcDirs(
                "build/generated/source/proto/main/java",
                "build/generated/source/proto/main/kotlin"
            )
        }
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:$grpcVersion"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:$grpcProtoVersion"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:$grpcKotlinVersion:jdk7@jar"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("grpc")
                id("grpckt")
            }
            it.builtins {
                id("kotlin")
            }
        }
    }
}