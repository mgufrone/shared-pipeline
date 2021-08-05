package com.mgufron
import com.mgufron.ReporterSuite
import groovy.util.XmlSlurper

class ReporterProcessor {
  final List<String> reportPath
  private List<ReporterSuite> testCollections = []
  ReporterProcessor(List<String> reportPath) {
    this.reportPath = reportPath
  }
  void withTexts(List<String> files) {
    testCollections = files.inject([]) { arr, it ->
      arr.push(new ReporterSuite().withText(it))
      return arr
    }
  }
  void generateReportSuite() {
    reportPath.each { String path ->
      def rp = new ReporterSuite(path)
      rp.generateReportSuite()
      testCollections.push(rp)
    }
  }
  int getTotalTests() {
    return sum { ReporterSuite it ->
      it.totalTests
    }
  }
  int getFailures() {
    return sum { ReporterSuite it ->
      it.failures
    }
  }
  int getSkipped() {
    return sum { ReporterSuite it ->
      it.skipped
    }
  }
  int getSuccessful() {
    return sum { ReporterSuite it ->
      it.success
    }
  }
  private int sum(Closure closure) {
    return testCollections.inject(0) { int total, ReporterSuite proc ->
      total + closure(proc)
    }
  }
}
