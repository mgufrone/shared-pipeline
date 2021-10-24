import com.mgufron.Cache
def call(String repoName, String branch, String fallbackBranch, Closure archiver, Closure restorer) {
  def cacher = new Cache(repoName, branch, fallbackBranch, archiver, restorer)
  cacher.archive()
}
