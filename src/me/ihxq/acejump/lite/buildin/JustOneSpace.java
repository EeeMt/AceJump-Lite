package me.ihxq.acejump.lite.buildin;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.EditorModificationUtil;
import me.ihxq.acejump.lite.common.EmacsIdeasAction;
import me.ihxq.acejump.lite.util.AppUtil;

public class JustOneSpace extends EmacsIdeasAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        if (super.initAction(e)) {
            int offset = _editor.getCaretModel().getOffset();
            JustOneSpaceAt(offset, _document.getText());
            cleanupSetupsInAndBackToNormalEditingMode();
        }
    }

    private void JustOneSpaceAt(int offset, String doc) {
        int begin = backwardToBeginOffset(offset, doc);
        int end = forwardToEndOffset(offset, doc, doc.length());

        if (end > begin - 1) {
            makeOneSpaceBetween(begin, end);
        }
    }

    private void makeOneSpaceBetween(final int begin, final int end) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                _editor.getSelectionModel().setSelection(begin, end);
                EditorModificationUtil.deleteSelectedText(_editor);
                EditorModificationUtil.insertStringAtCaret(_editor, " ");
            }
        };
        AppUtil.runWriteAction(runnable, _editor);
    }

    private int forwardToEndOffset(int end, String doc, int textLength) {
        while (end < textLength && Character.isWhitespace(doc.charAt(end))) {
            end++;
        }
        return end;
    }

    private int backwardToBeginOffset(int offset, String doc) {
        int begin = offset;
        boolean atLineBeginning = isAtLineBeginning(offset, doc);
        while (begin > 0 && Character.isWhitespace(doc.charAt(begin - 1)) && (atLineBeginning || doc.charAt(begin - 1) != '\n')) {
            begin--;
        }
        return begin;
    }

    private boolean isAtLineBeginning(int offset, String doc) {
        boolean isAtLineBeginning = false;
        int lineNumber = _document.getLineNumber(offset);
        int lineStartOffset = _document.getLineStartOffset(lineNumber);
        if (offset - lineStartOffset == 1 && !Character.isWhitespace(doc.charAt(offset))) {
            isAtLineBeginning = true;
        }
        return isAtLineBeginning;
    }
}
