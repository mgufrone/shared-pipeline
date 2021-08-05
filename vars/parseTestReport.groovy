import com.mgufron.ReporterProcessor

ReporterProcessor call(List<String> reportPaths) {
  def rp = new ReporterProcessor()
  def files = reportPaths.each {
    new File(it)
  }
  rp.withFiles(files)
  return rp
}
