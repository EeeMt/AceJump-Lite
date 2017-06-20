package me.ihxq.acejump.lite.acejump.offsets;

public class WordOffsetsFinder extends OffsetsFinder {
    @Override
    protected boolean isValidOffset(char c, String visibleText, int index, int offset, int caretOffset) {
        if (!Character.isLetterOrDigit(c)) {
            return true;
        }

        boolean isBeginningChar = index == 0;
        if (isBeginningChar) {
            return true;
        }

        char charBeforeOffset = visibleText.charAt(index - 1);
        if (isCharInDifferentCategory(c, charBeforeOffset)) {
            return true;
        }

        char charAtOffset = visibleText.charAt(index);
        return Character.isUpperCase(charAtOffset) && Character.isLowerCase(charBeforeOffset);

    }

    private boolean isCharInDifferentCategory(char c, char charBeforeOffset) {
        return Character.isLetter(c) ^ Character.isLetter(charBeforeOffset)
                || Character.isDigit(c) ^ Character.isDigit(charBeforeOffset);
    }
}
