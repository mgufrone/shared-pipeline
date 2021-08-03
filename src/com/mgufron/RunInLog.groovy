package com.mgufron
import com.mgufron.SlackSectionedMessage
import groovy.time.TimeCategory

class RunInLog {
  final SlackSectionedMessage slack
  final boolean withBenchmark
  RunInLog(slack, boolean withBenchmark = false) {
    this.slack = slack
    this.withBenchmark = withBenchmark
  }
  void send(int id, String msg, Map accessory = null) {
    slack.update(id, slack.message(msg, accessory))
  }
  private String runDuration(Date start) {
    def dur = groovy.time.TimeCategory.minus(
      new Date(),
      start
    )
    def durations = []
    if (dur.minutes > 0) {
      durations.push("${dur.minutes}m")
    }
    if (dur.seconds > 0) {
      durations.push("${dur.seconds}s")
    }
    if (dur.millis > 0) {
      durations.push("${dur.millis}ms")
    }
    return durations.join(" ")
  }
  void run(Map msg, Closure body) {
    def start = new Date()
    try {
      if(!msg["id"] || msg["id"] == null) {
        msg["id"] = slack.preserveBlock()
      }
      send(msg["id"], ":gh-loading: ${msg["message"]}")
      body.call()
      send(msg["id"], ":white_check_mark: ${msg["message"]} (${runDuration(start)})")
    } catch(e) {
      send(msg["id"], ":no_entry: ${msg["message"]} (${runDuration(start)})", msg["failBtn"])
      throw e
    }
  }
}
