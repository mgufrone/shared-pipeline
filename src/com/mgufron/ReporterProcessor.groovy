package com.mgufron
import com.mgufron.ReporterSuite
import groovy.util.XmlSlurper

class ReporterProcessor {
  final List<String> reportPath
  private List<ReporterSuite> testCollections = []
  ReporterProcessor(List<String> reportPath) {
    this.reportPath = reportPath
  }
  void process() {
    reportPath.each { String path ->
      testCollections.push(new ReporterSuite(path))
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
