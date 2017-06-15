package me.ihxq.acejump.lite.common;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;

public class ChainActionEvent extends AnActionEvent {
    private Runnable _pendingAction;
    private final Editor _editor;
    private final Project _project;

    public ChainActionEvent(AnActionEvent e, Runnable runnable, Editor _editor, Project _project) {
        super(e.getInputEvent(), e.getDataContext(), e.getPlace(), e.getPresentation(), e.getActionManager(), e.getModifiers());
        this._pendingAction = runnable;
        this._editor = _editor;
        this._project = _project;
    }

    public Runnable getPendingAction() {
        return _pendingAction;
    }

    public Editor getEditor() {
        return _editor;
    }

    public Project getProject() {
        return _project;
    }
}
