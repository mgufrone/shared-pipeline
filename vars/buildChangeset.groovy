import com.mgufron.BuildChangeSet

static BuildChangeSet call(Script script) {
  def changedFiles = []
  def changeLogSets = script.currentBuild.changeSets
  for (entries in changeLogSets) {
    for (entry in entries) {
      for (file in entry.affectedFiles) {
        changedFiles += "${file.path}"
      }
    }
  }
  return new BuildChangeSet(changedFiles)
}
