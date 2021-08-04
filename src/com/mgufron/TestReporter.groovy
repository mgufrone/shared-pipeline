package com.mgufron
import groovy.util.XmlSlurper

class TestProcessor {
  final String reportPath
  int totalTests = 0
  int failures = 0
  int success = 0
  int skipped = 0
  TestProcessor(String reportPath) {
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

class TestReporter {
  final List<String> reportPath
  private List<TestProcessor> testCollections = []
  TestReporter(List<String> reportPath) {
    this.reportPath = reportPath
    process()
  }
  private void process() {
    reportPath.each { String path ->
      testCollections.push(new TestProcessor(path))
    }
  }
  int getTotalTests() {
    return sum { TestProcessor it ->
      return it.totalTests
    }
  }
  int getFailures() {
    return sum { TestProcessor it ->
      return it.failures
    }
  }
  int getSkipped() {
    return sum { TestProcessor suite ->
      return suite.skipped
    }
  }
  int getSuccessful() {
    return sum { TestProcessor suite ->
      return suite.success
    }
  }
  private int sum(Closure closure) {
    return testCollections.inject(0) { int total, TestProcessor proc ->
      return total + closure(proc)
    }
  }
}
