pluginManagement {
    val kotlinVersion: String by settings
    val springBootVersion: String by settings
    val springDependencyManagementVersion: String by settings
    val palantirDockerVersion: String by settings

    plugins {
        id("org.springframework.boot") version springBootVersion
        id("io.spring.dependency-management") version springDependencyManagementVersion
        id("com.palantir.docker") version palantirDockerVersion

        kotlin("jvm") version kotlinVersion
        kotlin("plugin.spring") version kotlinVersion
        kotlin("plugin.jpa") version kotlinVersion
    }
}

rootProject.name = "consortium-calendar"
include("commons")
include("commons:health-check")
include("commons:test-fixtures")
include("commons:logback-appender")
include("commons:common-util")
include("consortium-calendar-assets")
include("consortium-calendar-assets:consortium-calendar-assets-interfaces")
