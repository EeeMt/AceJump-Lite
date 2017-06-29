package me.ihxq.acejump.lite.acejump.command;

import com.intellij.openapi.editor.Editor;
import me.ihxq.acejump.lite.acejump.marker.JOffset;

public class CommandAroundJump {
    private Editor _se; /*source editor*/
    private int _soff;

    private Editor _te; /*target editor*/
    private int _toff;

    CommandAroundJump(Editor editor) {
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

    void selectJumpArea() {
        if (inSameEditor()) {
            if (_soff < _toff)
                _se.getSelectionModel().setSelection(_soff, _toff);
            else
                _se.getSelectionModel().setSelection(_toff, _soff);
        }
    }

    private boolean inSameEditor() {
        return _se == _te;
    }
}

