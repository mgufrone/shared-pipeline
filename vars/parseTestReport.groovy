import com.mgufron.TestReporter

TestReporter call(List<String> reportPaths) {
  def rp = new TestReporter(reportPaths)
  rp.process()
  return rp
}
