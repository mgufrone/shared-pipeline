import com.mgufron.ReporterProcessor

ReporterProcessor call(List<String> reportPaths) {
  def rp = new ReporterProcessor(reportPaths)
  rp.generateReportSuite()
  return rp
}
