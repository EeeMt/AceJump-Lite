package me.ihxq.acejump.lite.common;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.TextEditor;
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.project.Project;
import me.ihxq.acejump.lite.acejump.runnable.ShowMarkersRunnable;

import javax.swing.*;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public abstract class EmacsIdeasAction extends AnAction {
    private volatile boolean _isStillRunning = false;
    protected EmacsIdeasAction _action;
    protected Editor _editor;
    protected ArrayList<Editor> _editors;
    protected JComponent _contentComponent;
    protected Document _document;
    private KeyListener[] _keyListeners;
    private AnActionEvent _event;
    private Project _project;

    public void cleanupSetupsInAndBackToNormalEditingMode() {
        restoreOldKeyListeners();
        _contentComponent.repaint();
        _isStillRunning = false;
    }

    private void restoreOldKeyListeners() {
        for (KeyListener kl : _keyListeners) {
            _contentComponent.addKeyListener(kl);
        }
    }

    protected boolean initAction(AnActionEvent e) {
        if (getEditorFrom(e) == null) {
            return false;
        }

        switchEditorIfNeed(e);

        if (_isStillRunning) {
            cleanupSetupsInAndBackToNormalEditingMode();
        }

        initMemberVariableForConvenientAccess(e);
        disableAllExistingKeyListeners();

        return true;
    }

    private Editor getEditorFrom(AnActionEvent e) {
        if (e instanceof ChainActionEvent) {
            ChainActionEvent chainActionEvent = (ChainActionEvent) e;
            Editor editor = chainActionEvent.getEditor();
            if (editor != null) {
                return editor;
            }
        }

        return e.getData(PlatformDataKeys.EDITOR);
    }

    public void switchEditorIfNeed(AnActionEvent e) {
        Editor newEditor = getEditorFrom(e);
        if (_editor != null && _editor != newEditor) {
            cleanupSetupsInAndBackToNormalEditingMode();
        }

        _editor = newEditor;
        _editors = collect_active_editors(e);
    }

    @SuppressWarnings("all")
    private ArrayList<Editor> collect_active_editors(AnActionEvent e) {
        ArrayList<Editor> editors = new ArrayList<>();

        final Project project = e.getData(CommonDataKeys.PROJECT);
        final FileEditorManagerEx fileEditorManager = FileEditorManagerEx.getInstanceEx(project);
        FileEditor[] selectedEditors = fileEditorManager.getSelectedEditors();

        for (FileEditor selectedEditor : selectedEditors) {
            if (selectedEditor instanceof TextEditor) {
                Editor editor = ((TextEditor) selectedEditor).getEditor();
                editors.add(editor);
            }
        }

        return editors;
    }

    protected void initMemberVariableForConvenientAccess(AnActionEvent e) {
        _event = e;
        _isStillRunning = true;
        _document = _editor.getDocument();
        _action = this;
        _contentComponent = _editor.getContentComponent();
        _project = getProjectFrom(e);
    }

    private void disableAllExistingKeyListeners() {
        _keyListeners = _contentComponent.getKeyListeners();
        for (KeyListener kl : _keyListeners) {
            _contentComponent.removeKeyListener(kl);
        }
    }

    public Editor getEditor() {
        return _editor;
    }

    public ArrayList<Editor> getEditors() {
        return _editors;
    }

    protected void runReadAction(ShowMarkersRunnable action) {
        ApplicationManager.getApplication().runReadAction(action);
    }

    protected void handlePendingActionOnSuccess() {
        if (_event instanceof ChainActionEvent) {
            ChainActionEvent chainActionEvent = (ChainActionEvent) _event;
            chainActionEvent.getPendingAction().run();
        }
    }

    protected Project getProjectFrom(AnActionEvent e) {
        if (e instanceof ChainActionEvent) {
            ChainActionEvent chainActionEvent = (ChainActionEvent) e;
            Project project = chainActionEvent.getProject();
            if (project != null) {
                return project;
            }
        }

        return e.getData(PlatformDataKeys.PROJECT);
    }
}
