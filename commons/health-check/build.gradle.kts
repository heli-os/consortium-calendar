val testFixtures = project(":commons:test-fixtures").sourceSets["test"].output

dependencies {
    testImplementation(testFixtures)
}
