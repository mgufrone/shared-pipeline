package com.mgufron
import java.security.*
import java.math.*


class Cache {
  String mainChecksum
  String fallbackChecksum
  Closure archiver
  Closure restorer
  public Cache(String repoName, String branch, String fallbackBranch, Closure archiver, Closure restorer) {
    MessageDigest sha1 = MessageDigest.getInstance("SHA1")
    String password = "$repoName-$branch".toString()
    byte[] digest  = sha1.digest(password.getBytes())
    String finalSum = new BigInteger(1, digest).toString(16)
    this.archiver = archiver
    this.restorer = restorer
    this.setMainChecksum(finalSum)
    this.setFallbackChecksum(finalSum)
    if (branch != fallbackBranch) {
      String fallString = "${repoName}-${fallbackBranch}".toString()
      byte[] fallback = sha1.digest(fallString.getBytes())
      String fallbackSum = new BigInteger(1, fallback).toString(16)
      this.setFallbackChecksum(fallbackSum)
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
