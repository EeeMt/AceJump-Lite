package me.ihxq.acejump.lite.acejump.command;

import com.intellij.openapi.editor.Editor;
import me.ihxq.acejump.lite.acejump.marker.JOffset;

public class SelectAfterJumpCommand extends CommandAroundJump {
    public SelectAfterJumpCommand(Editor editor) {
        super(editor);
    }

    @Override
    public void beforeJump(final JOffset jumpTargetOffset) {
        super.beforeJump(jumpTargetOffset);
    }

    @Override
    public void afterJump() {
        selectJumpArea();
    }
}
