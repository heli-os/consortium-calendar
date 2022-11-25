apply(from = "../../docker-build.gradle")

dependencies {
    implementation(project(":commons:common-util"))
    implementation(project(":commons:health-check"))
    implementation(project(":commons:logback-appender"))
    implementation(project(":consortium-calendar-assets:consortium-calendar-assets-domain"))

}
