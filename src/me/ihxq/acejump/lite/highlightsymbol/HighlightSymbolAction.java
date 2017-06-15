package me.ihxq.acejump.lite.highlightsymbol;

import com.intellij.find.FindManager;
import com.intellij.find.FindModel;
import com.intellij.find.FindResult;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.ScrollType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.wm.ToolWindowManager;
import me.ihxq.acejump.lite.common.EmacsIdeasAction;

public class HighlightSymbolAction extends EmacsIdeasAction {

    public void actionPerformed(AnActionEvent e, boolean searchForward) {
        Project project = e.getData(PlatformDataKeys.PROJECT);
        if (!ToolWindowManager.getInstance(project).isEditorComponentActive()) {
            ToolWindowManager.getInstance(project).activateEditorComponent();
            return;
        }

        if (super.initAction(e)) {
            int nextSymbolOffset = getNextSymbolOffset(searchForward, project);
            if (-1 != nextSymbolOffset) {
                _editor.getCaretModel().moveToOffset(nextSymbolOffset);
                _editor.getScrollingModel().scrollToCaret(ScrollType.MAKE_VISIBLE);
                _editor.getSelectionModel().selectWordAtCaret(false);
            }
        }

        super.cleanupSetupsInAndBackToNormalEditingMode();
    }

    private int getNextSymbolOffset(boolean searchForward, Project project) {
        _editor.getSelectionModel().selectWordAtCaret(false);

        int symbolStart = _editor.getSelectionModel().getSelectionStart();
        int symbolEnd = _editor.getSelectionModel().getSelectionEnd();

        if (symbolStart >= 0 && symbolEnd > symbolStart) {
            String symbol =  _editor.getDocument().getText(new TextRange(symbolStart, symbolEnd));

            FindManager findManager = FindManager.getInstance(project);
            FindModel findModel = (FindModel) findManager.getFindInFileModel().clone();
            findModel.setFindAll(false);
            findModel.setFromCursor(true);
            findModel.setForward(searchForward);
            findModel.setRegularExpressions(false);
            findModel.setWholeWordsOnly(true);
            findModel.setCaseSensitive(true);
            findModel.setSearchHighlighters(false);
            findModel.setPreserveCase(false);

            findModel.setStringToFind(symbol);

            int startOffset = _editor.getCaretModel().getOffset();
            if (searchForward) {
                startOffset++;
            }

            FindResult findResult = findManager.findString(_editor.getDocument().getText(), startOffset, findModel);

            //fix errors in Appcode, which is the findManager.findString return 0, when string not found.
            if (findResult.getStartOffset() == 0) {
                String potentialSymbol =  _editor.getDocument().getText(new TextRange(0, symbol.length()));
                if (!potentialSymbol.equals(symbol)) {
                    return -1;
                }
            }

            return findResult.getStartOffset();
        }

        return -1;
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
    }
}
