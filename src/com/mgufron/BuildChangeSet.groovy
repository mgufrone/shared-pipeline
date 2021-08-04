package com.mgufron

class BuildChangeSet {
  List changedFiles = []
  BuildChangeSet(changedFiles) {
    this.changedFiles = changedFiles
  }
  boolean contains(List pattern) {
    if (pattern instanceof Iterable || pattern instanceof List) {
      for(def i = 0; i < pattern.size(); i++) {
        if (match(pattern[i])) {
          return true
        }
      }
    }
    return false
  }
  boolean contains(String pattern) {
    return match(pattern)
  }
  private boolean match(String pattern) {
    def converted = createRegexFromGlob(pattern)
    for (def i = 0; i < changedFiles.size(); i ++) {
      if (changedFiles[i] =~ converted) {
        return true
      }
    }
    return false
  }
  private static String createRegexFromGlob(String glob)
  {
    String out = "^";
    for(int i = 0; i < glob.length(); ++i)
    {
      final char c = glob.charAt(i);
      switch(c)
      {
        case '*': out += ".*"; break;
        case '?': out += '.'; break;
        case '.': out += "\\."; break;
        case '\\': out += "\\\\"; break;
        default: out += c;
      }
    }
    out += '$';
    return out;
  }
}
