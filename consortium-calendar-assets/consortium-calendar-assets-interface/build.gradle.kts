apply(from = "../../docker-build.gradle")

dependencies {
    implementation(project(":commons:health-check"))
    implementation(project(":commons:logback-appender"))
    implementation(project(":commons:common-model"))
    implementation(project(":commons:common-util"))
    implementation(project(":consortium-calendar-assets:consortium-calendar-assets-domain"))
    implementation("org.springframework.boot:spring-boot-starter-validation")
}
