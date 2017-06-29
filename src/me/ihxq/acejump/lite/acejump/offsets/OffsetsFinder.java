package me.ihxq.acejump.lite.acejump.offsets;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.TextRange;
import me.ihxq.acejump.lite.options.PluginConfig;
import me.ihxq.acejump.lite.util.EditorUtils;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class OffsetsFinder {
    private final PluginConfig _config = ServiceManager.getService(PluginConfig.class);

    public List<Integer> getOffsets(char key, Editor editor, Editor selectedEditor) {
        Document document = editor.getDocument();
        TextRange visibleRange = EditorUtils.getVisibleTextRange(editor);
        List<Integer> offsets = getOffsetsOfCharIgnoreCase(String.valueOf(key), visibleRange, document, editor, selectedEditor);

        if (key == KeyEvent.VK_SPACE) {
            offsets.addAll(getOffsetsOfCharIgnoreCase("\t\r\n", visibleRange, document, editor, selectedEditor));
            addStartLineOffsetsTo(offsets, editor);
        } else if (key == ',') {
            offsets.addAll(getOffsetsOfCharIgnoreCase("|`/\\;.{}()[]<>?_=-+'\"!@#$%^&*", visibleRange, document, editor, selectedEditor));
        }

        return offsets;
    }

    private void addStartLineOffsetsTo(List<Integer> offsets, Editor editor) {
        ArrayList<Integer> visibleLineStartOffsets = EditorUtils.getVisibleLineStartOffsets(editor);
        for (Integer i : visibleLineStartOffsets) {
            if (!offsets.contains(i)) {
                offsets.add(i);
            }
        }
    }

    private ArrayList<Integer> getOffsetsOfCharIgnoreCase(String charSet, TextRange markerRange, Document document, Editor editor, Editor selectedEditor) {
        ArrayList<Integer> offsets = new ArrayList<Integer>();
        String visibleText = document.getText(markerRange);

        for (char charToFind : charSet.toCharArray()) {
            char lowCase = Character.toLowerCase(charToFind);
            char upperCase = Character.toUpperCase(charToFind);

            offsets.addAll(getOffsetsOfChar(markerRange.getStartOffset(), lowCase, visibleText, editor, selectedEditor));
            if (upperCase != lowCase) {
                offsets.addAll(getOffsetsOfChar(markerRange.getStartOffset(), upperCase, visibleText, editor, selectedEditor));
            }
        }

        return offsets;
    }

    private ArrayList<Integer> getOffsetsOfChar(int startOffset, char c, String visibleText, Editor editor, Editor selectedEditor) {
        int caretOffset = editor.getCaretModel().getOffset();

        ArrayList<Integer> offsets = new ArrayList<Integer>();

        int index = visibleText.indexOf(c);
        while (index >= 0) {
            int offset = startOffset + index;

            if (isValidOffset(c, visibleText, index, offset, caretOffset)) {
                // check if current position is the input char
                boolean currentPosition;
                if (_config._jumpBehind) {
                    currentPosition = offset != caretOffset - 1;
                } else {
                    currentPosition = offset != caretOffset;
                }
                if (editor != selectedEditor || currentPosition)
                    offsets.add(offset);
            }

            index = visibleText.indexOf(c, index + 1);
        }

        return offsets;
    }

    protected boolean isValidOffset(char c, String visibleText, int index, int offset, int caretOffset) {
        return true;
    }
}
