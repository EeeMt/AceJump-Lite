package me.ihxq.acejump.lite.util;

import com.intellij.codeInsight.actions.ReformatCodeAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorModificationUtil;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.util.TextRange;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class EditorUtils {
    public static TextRange getVisibleTextRange(Editor editor) {
        Rectangle visibleArea = editor.getScrollingModel().getVisibleArea();

        LogicalPosition startLogicalPosition = editor.xyToLogicalPosition(visibleArea.getLocation());

        double endVisualX = visibleArea.getX() + visibleArea.getWidth();
        double endVisualY = visibleArea.getY() + visibleArea.getHeight();
        LogicalPosition endLogicalPosition = editor.xyToLogicalPosition(new Point((int) endVisualX, (int) endVisualY));

        return new TextRange(editor.logicalPositionToOffset(startLogicalPosition), editor.logicalPositionToOffset(endLogicalPosition));
    }


    public static boolean isPrintableChar( char c ) {
        Character.UnicodeBlock block = Character.UnicodeBlock.of( c );
        return (!Character.isISOControl(c)) &&
                c != KeyEvent.CHAR_UNDEFINED &&
                block != null &&
                block != Character.UnicodeBlock.SPECIALS;
    }
        
    public static ArrayList<Integer> getVisibleLineStartOffsets(Editor editor) {
        Document document = editor.getDocument();
        ArrayList<Integer> offsets = new ArrayList<Integer>();

        TextRange visibleTextRange = getVisibleTextRange(editor);
        int startLine = document.getLineNumber(visibleTextRange.getStartOffset());
        int endLine = document.getLineNumber(visibleTextRange.getEndOffset());

        for (int i = startLine; i < endLine; i++) {
            offsets.add(document.getLineStartOffset(i));
        }

        return offsets;
    }

    public static void reformatCode(AnActionEvent e) {
        ReformatCodeAction reformat = new ReformatCodeAction();
        reformat.actionPerformed(e);
    }

    public static void selectTextRange(Editor editor, TextRange[] tr) {
        if (editor != null && tr != null) {
            editor.getSelectionModel().setSelection(tr[0].getStartOffset(), tr[0].getEndOffset());
        }
    }

    public static void selectTextRange(Editor editor, int startOffset, int endOffset) {
        if (editor != null) {
            editor.getSelectionModel().setSelection(startOffset, endOffset);
        }
    }

    public static void deleteRange(TextRange tr, Editor editor) {
        selectTextRange(editor, new TextRange[] {tr} );
        EditorModificationUtil.deleteSelectedText(editor);
    }
}
