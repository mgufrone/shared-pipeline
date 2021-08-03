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
    def idx = blocks.size()
    blocks.push([:])
    return idx
  }
  void send(String color = "", boolean asComment = false) {
    sendAttachments(this.blocks, color, asComment)
  }
  private void sendAttachments(List blocks, String color = "", boolean asComment = false) {
    def attachments = [
      "blocks": blocks
    ]
    if (color != "") {
      attachments["color"] = color
    }
    def payload = [
      "attachments": [attachments],
      "channel": defaultChannel,
      "color": color
    ]
    if(thread) {
      payload["channel"] = thread.threadId
      if (!asComment) {
        payload["timestamp"] = thread.ts
      }
    }
    def res = script.slackSend(payload)
    if(!thread) {
      thread = res
    }
  }
  void sendMessage(Map msg, boolean asComment = false) {
    if (asComment) {
      this.sendAttachments([msg], "", asComment)
      return
    }
    blocks.push(msg)
    send("", asComment)
  }
  void update(int idx, Map msg, String color = "") {
    blocks[idx] = msg
    send(color)
  }
  void success(String color = "#017312") {
    send(color)
  }
  void fail(String color = '#a22725') {
    send(color)
  }
}
