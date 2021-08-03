package com.mgufron
import com.mgufron.SlackSectionedMessage
class RunInLog {
  final SlackSectionedMessage slack
  RunInLog(slack) {
    this.slack = slack
  }
  void send(int id, String msg, Map accessory = null) {
    slack.update(id, slack.message(msg, accessory))
  }
  void run(Map msg, Closure body) {
    try {
      if(!msg["id"] || msg["id"] == null) {
        msg["id"] = slack.preserveBlock()
      }
      send(msg["id"], ":gh-loading: ${msg["message"]}")
      body.call()
      send(msg["id"], ":white_check_mark: ${msg["message"]}")
    } catch(e) {
      send(msg["id"], ":no_entry: ${msg["message"]}", map["failBtn"])
      throw e
    }
  }
}
