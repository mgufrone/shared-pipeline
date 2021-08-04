package com.mgufron

class ReporterSuite {
  final String reportPath
  int totalTests = 0
  int failures = 0
  int success = 0
  int skipped = 0
  ReporterSuite(String reportPath) {
    this.reportPath = reportPath
    process()
  }
  private void process() {
    def slurper = new XmlSlurper().parse(reportPath)
    def suites = slurper.testsuite
    if (suites instanceof Iterable) {
      suites.each { suite ->
        processSuite(suite)
      }
      return
    }
    processSuite(suites)
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