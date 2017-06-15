package me.ihxq.acejump.lite.util;

public class Chars {

    public static boolean isSymbol(Character character) {
        return Chars.isSymbol(character);
    }

    public static boolean isSymbol(char c) {
        return c == '_' || Character.isLowerCase(c) || Character.isUpperCase(c) || Character.isDigit(c);
    }
}
