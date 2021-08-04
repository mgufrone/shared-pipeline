package com.mgufron
import com.mgufron.ReporterSuite
import groovy.util.XmlSlurper

class ReporterProcessor {
  final List<String> reportPath
  private List<ReporterSuite> testCollections = []
  ReporterProcessor(List<String> reportPath) {
    this.reportPath = reportPath
    process()
  }
  void process() {
    reportPath.each { String path ->
      testCollections.push(new ReporterSuite(path))
    }
  }
  int getTotalTests() {
    return sum { ReporterSuite it ->
      return it.totalTests
    }
  }
  int getFailures() {
    return sum { ReporterSuite it ->
      return it.failures
    }
  }
  int getSkipped() {
    return sum { ReporterSuite suite ->
      return suite.skipped
    }
  }
  int getSuccessful() {
    return sum { ReporterSuite suite ->
      return suite.success
    }
  }
  private int sum(Closure closure) {
    return testCollections.inject(0) { int total, ReporterSuite proc ->
      return total + closure(proc)
    }
  }
}
