package com.mgufron

class Cache {
  String mainChecksum
  String fallbackChecksum
  Closure archiver
  Closure restorer
  public Cache(String repoName, String branch, String fallbackBranch, Closure archiver, Closure restorer) {
    String finalSum = "${repoName}-${branch}".digest('SHA-1')
    this.archiver = archiver
    this.restorer = restorer
    this.setMainChecksum(finalSum)
    this.setFallbackChecksum(finalSum)
    if (branch != fallbackBranch) {
      this.setFallbackChecksum("${repoName}-${fallbackBranch}".digest('SHA-1'))
    }
  }
  public void restore() {
    try {
      this.restorer(this.mainChecksum)
    } catch (e) {
      this.restorer(this.fallbackChecksum)
    }
  }
  public void archive() {
    this.archiver(this.mainChecksum)
  }
}
