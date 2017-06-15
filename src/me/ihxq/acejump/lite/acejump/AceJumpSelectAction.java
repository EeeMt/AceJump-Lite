package me.ihxq.acejump.lite.acejump;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import me.ihxq.acejump.lite.acejump.command.SelectAfterJumpCommand;

public class AceJumpSelectAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        AceJumpAction.getInstance().switchEditorIfNeed(e);
        AceJumpAction.getInstance().addCommandAroundJump(new SelectAfterJumpCommand(editor));
        AceJumpAction.getInstance().performAction(e);
    }
}
