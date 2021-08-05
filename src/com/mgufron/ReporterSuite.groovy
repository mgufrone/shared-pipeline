package com.mgufron
import groovy.util.XmlSlurper
class ReporterSuite {
  final String reportPath
  int totalTests = 0
  int failures = 0
  int success = 0
  int skipped = 0
  ReporterSuite(String reportPath) {
    this.reportPath = reportPath
  }
  void withFile(File file) {
    reportSuite(file)
  }
  ReporterSuite withText(String text) {
    return createReport(text)
  }
  void generateReportSuite() {
    File file = new File(reportPath)
    reportSuite(file)
  }
  void reportSuite(File file) {
    createReport(file.getText())
  }
  ReporterSuite createReport(String text) {
    def slurper = new XmlSlurper().parseText(text)
    def suites = slurper.testsuite
    if (suites instanceof Iterable || suites instanceof List) {
      suites.each { suite ->
        processSuite(suite)
      }
      return this
    }
    processSuite(suites)
    return this
  }
  private void processSuite(testsuite) {
    def totalTests = testsuite.@tests.toInteger()
    def failures = testsuite.@failures.toInteger()
    def success = totalTests - failures
    def skipped = testsuite.@skipped.toInteger()
    if (skipped > 0) {
      this.skipped += skipped
    }
    this.totalTests += totalTests
    this.success += success
    this.failures += failures
  }
}
