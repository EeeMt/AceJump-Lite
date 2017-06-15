package me.ihxq.acejump.lite.acejump.offsets;

public class CharOffsetsFinder extends OffsetsFinder {

    @Override
    protected boolean isValidOffset(char c, String visibleText, int index, int offset, int caretOffset) {
        return !isSpaceAndShouldIgnore(c, visibleText, index);
    }

    private  boolean isSpaceAndShouldIgnore(char c, String visibleText, int index) {
        if (isSpace(c)) {
            boolean isAfterSpace = (index != 0) && (isSpace(visibleText.charAt(index - 1)));
            if (isAfterSpace) {
                return true;
            }
        }
        return false;
    }

    private  boolean isSpace(char c) {
        return c == ' ' || c == '\t';
    }

}
