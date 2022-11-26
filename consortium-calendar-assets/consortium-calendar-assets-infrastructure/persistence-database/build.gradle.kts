dependencies {
    val kotlinJdslVersion: String by project
    implementation("com.linecorp.kotlin-jdsl:spring-data-kotlin-jdsl-starter:$kotlinJdslVersion")

    runtimeOnly("mysql:mysql-connector-java")
}
