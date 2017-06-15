package me.ihxq.acejump.lite.acejump;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class AceJumpCharAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        AceJumpAction.getInstance().performAction(e);
    }
}

