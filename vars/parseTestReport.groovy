import com.mgufron.ReporterProcessor

ReporterProcessor call(List<String> reportPaths) {
  def rp = new ReporterProcessor(reportPaths)
  def files = reportPaths.each {
    new File(it)
  }
  rp.generateReportSuite(files)
  return rp
}
