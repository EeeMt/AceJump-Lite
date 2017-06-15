package me.ihxq.acejump.lite.util;

public class Str {
    public static char getCounterCase(char c) {
        return Character.isUpperCase(c) ? Character.toLowerCase(c) : Character.toUpperCase(c);
    }

}
