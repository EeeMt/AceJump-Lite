package me.ihxq.acejump.lite.common;

public class CommandContext {
    private int _spacePrefixCount;
    private char _lastCmdKey;

    public int getPrefixCount() {
        return _spacePrefixCount;
    }

    public boolean consume(char key) {
        if (key == ' ') {
            _spacePrefixCount++;
            return true;
        }

        _lastCmdKey = key;
        return false;
    }

    public char getLastCmdKey() {
        return _lastCmdKey;
    }
}
