package me.ihxq.acejump.lite.acejump.command;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.Editor;
import me.ihxq.acejump.lite.acejump.marker.JOffset;
import me.ihxq.acejump.lite.options.PluginConfig;

public class CommandAroundJump {
    protected Editor _se; /*source editor*/
    protected int _soff;

    protected Editor _te; /*target editor*/
    protected int _toff;

    final PluginConfig _config = ServiceManager.getService(PluginConfig.class);

    public CommandAroundJump(Editor editor) {
        _se = editor;
    }

    public void beforeJump(final JOffset jumpTargetOffset) {
        _soff = _se.getCaretModel().getOffset();
    }

    public void preAfterJump(final JOffset jumpTargetOffset) {
        _te = jumpTargetOffset.editor;
        _toff = jumpTargetOffset.offset;
    }

    public void afterJump() {
    }

    public void focusSourceCaret() {
        _se.getContentComponent().requestFocus();
        _se.getCaretModel().moveToOffset(_soff);
    }

    public void focusTargetCaret() {
        _te.getContentComponent().requestFocus();
        _te.getCaretModel().moveToOffset(_toff);
    }

    protected void selectJumpArea() {
        if (inSameEditor()) {
            if (_soff < _toff)
                _se.getSelectionModel().setSelection(_soff, _toff);
            else
                _se.getSelectionModel().setSelection(_toff, _soff);
        }
    }

    public boolean inSameEditor() {
        return _se == _te;
    }
}

