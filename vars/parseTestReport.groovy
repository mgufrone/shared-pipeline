import com.mgufron.ReporterProcessor

ReporterProcessor call(List<String> reportPaths) {
  def rp = new TestReporter(reportPaths)
  rp.process()
  return rp
}
