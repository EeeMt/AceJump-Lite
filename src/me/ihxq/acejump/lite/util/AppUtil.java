package me.ihxq.acejump.lite.util;

import com.intellij.codeInsight.hint.HintManager;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.editor.Editor;

public class AppUtil {
    private static Runnable getRunnableWrapper(final Runnable runnable, final Editor editor) {
        return new Runnable() {
            @Override
            public void run() {
                CommandProcessor.getInstance().executeCommand(editor.getProject(), runnable, "", ActionGroup.EMPTY_GROUP);
            }
        };
    }

    public static void runWriteAction(final Runnable runnable, final Editor editor) {
        try {
            ApplicationManager.getApplication().runWriteAction(AppUtil.getRunnableWrapper(runnable, editor));
        } catch (Exception e) {
            HintManager.getInstance().showInformationHint(editor, e.getMessage());
        }
    }
}
