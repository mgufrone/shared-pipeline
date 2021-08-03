package com.mgufron

class SlackSectionedMessage {
  final Script script
  final String defaultChannel
  Object thread
  List blocks = []
  SlackSectionedMessage(script, String defaultChannel) {
    this.script = script
    this.defaultChannel = defaultChannel
  }
  Map button(String label, String value, String style = "") {
    def isUrl = value.indexOf("http") == 0
    def btn = [
      "type"     : "button",
      "text"     : [
        "type" : "plain_text",
        "emoji": true,
        "text" : label
      ],
      "action_id": value,
      "value"    : value,
    ]
    if (style != "") {
      btn["style"] = style
    }
    if (isUrl) {
      btn["url"] = value
    }
    return btn
  }
  Map message(String msgString, Map accessory = null) {
    def msg =[
      "text": [
        "type" : "mrkdwn" ,
        "text" : msgString
      ]
    ]
    if (accessory) {
      msg["accessory"] = accessory
    }
    return section(msg)
  }
  Map section(Map composable) {
    def _section = [
      "type": "section"
    ]
    if (composable["text"]) {
      _section["text"] = composable["text"]
    }
    if(composable["accessory"]) {
      _section["accessory"] = composable["accessory"]
    }
    return _section
  }
  Map actions(List<Map> buttons) {
    return [
      "type": "actions",
      "elements": buttons,
    ]
  }
  int preserveBlock() {
    def idx = this.blocks.size()
    this.blocks.push([:])
    return idx
  }
  void send(String color = "") {
    def attachments = [
      "blocks": this.blocks
    ]
    if (color != "") {
      attachments["color"] = color
    }
    if(this.thread) {
      script.slackSend([
        "channel": thread.threadId,
        "attachments": [attachments],
        "timestamp": thread.ts,
      ])
      return
    }
    this.thread = script.slackSend([
      "attachments": [attachments],
      "channel": defaultChannel,
    ])
  }
  void sendMessage(Map msg) {
    this.blocks.push(msg)
    this.send()
  }
  void update(int idx, Map msg, String color = "") {
    this.blocks[idx] = msg
    this.send(color)
  }
  void success(String color = "#0064ae") {
    this.send(color)
  }
  void fail(String color = '#a22725') {
    this.send(color)
  }
}
