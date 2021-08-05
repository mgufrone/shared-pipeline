import com.mgufron.ReporterProcessor

ReporterProcessor call(List<String> reportPaths) {
  def rp = new ReporterProcessor()
  rp.withTexts(reportPaths)
  return rp
}
