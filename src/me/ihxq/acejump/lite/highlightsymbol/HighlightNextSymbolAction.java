package me.ihxq.acejump.lite.highlightsymbol;

import com.intellij.openapi.actionSystem.AnActionEvent;

public class HighlightNextSymbolAction extends HighlightSymbolAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        super.actionPerformed(e, true);
    }

}

