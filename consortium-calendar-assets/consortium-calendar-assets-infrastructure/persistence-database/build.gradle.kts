dependencies {
    implementation(project(":commons:common-model"))
    implementation(project(":commons:common-util"))

    val kotlinJdslVersion: String by project
    implementation("com.linecorp.kotlin-jdsl:spring-data-kotlin-jdsl-starter:$kotlinJdslVersion")
    runtimeOnly("mysql:mysql-connector-java")
}
