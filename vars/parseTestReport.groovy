import com.mgufron.TestReporter

static TestReporter call(List<String> reportPaths) {
  def rp = new TestReporter(reportPaths)
  rp.process()
  return rp
}
