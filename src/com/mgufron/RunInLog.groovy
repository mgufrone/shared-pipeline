package com.mgufron
import com.mgufron.SlackSectionedMessage
class RunInLog {
  final SlackSectionedMessage slack
  RunInLog(slack) {
    this.slack = slack
  }
  run(Map msg, Closure body) {
    try {
      if (msg["id"]) {
        slack.update(msg["id"], slack.message(":gh-loading: ${msg["message"]}"))
      }
      slack.update(msg["id"], slack.message(":white_check_mark: ${msg["message"]}"))
    } catch(e) {
      slack.update(msg["id"], slack.message(":no_entry: ${msg["message"]}"))
      throw e
    }
  }
}
