package me.ihxq.acejump.lite.acejump.runnable;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import me.ihxq.acejump.lite.acejump.AceJumpAction;
import me.ihxq.acejump.lite.acejump.marker.JOffset;
import me.ihxq.acejump.lite.acejump.marker.MarkerCollection;
import me.ihxq.acejump.lite.acejump.marker.MarkersPanel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ShowMarkersRunnable implements Runnable {
    public static final char INFINITE_JUMP_CHAR = '/';
    private static final String MARKER_CHARSET =     "asdfjeghiybcmnopqrtuvwkl";   //TODO: customizable
    private final List<JOffset> _offsets;
    private final AceJumpAction _action;
    private final Editor _editor;
    private final ArrayList<Editor> _editors;
    private MarkerCollection _markerCollection;

    public ShowMarkersRunnable(List<JOffset> offsets, AceJumpAction currentExecutingAction) {
        _offsets = offsets;
        _editor = currentExecutingAction.getEditor();
        _editors = currentExecutingAction.getEditors();
        this._action = currentExecutingAction;
        _markerCollection = _action.getMarkerCollection();
    }

    @Override
    public void run() {
        if (_offsets.isEmpty()) {
            return;
        }

        int caretOffset = _editor.getCaretModel().getOffset();
        sortOffsetsByDistanceToCaret(caretOffset);
        sortOffsetsToImprovePriorityOfLineEnd();

        int twiceJumpGroupCount = calcTwiceJumpGroupCount();
        int singleJumpCount = Math.min(MARKER_CHARSET.length() - twiceJumpGroupCount, _offsets.size());

        createSingleJumpMarkers(singleJumpCount);
        if (twiceJumpGroupCount > 0) {
            createMultipleJumpMarkers(singleJumpCount);
        }

        ArrayList<MarkersPanel> panels = new ArrayList<MarkersPanel>();
        for (Editor editor : _editors) {
            MarkersPanel markersPanel = new MarkersPanel(editor, _markerCollection);
            panels.add(markersPanel);
        }

        _action.showNewMarkersPanel(panels);
    }

    private void createSingleJumpMarkers(int singleJumpCount) {
        for (int i = 0; i < singleJumpCount ; i++) {
            String marker = String.valueOf(MARKER_CHARSET.charAt(i));
            _markerCollection.addMarker(marker, _offsets.get(i));
        }
    }

    private void createMultipleJumpMarkers(int singleJumpCount) {
        int i = singleJumpCount;

        for (;i < _offsets.size(); i++) {
            int group = (i - singleJumpCount) / MARKER_CHARSET.length();
            int markerCharIndex = singleJumpCount + group;

            if (markerCharIndex > MARKER_CHARSET.length() - 1) {
                break;
            }

            char markerChar = MARKER_CHARSET.charAt(markerCharIndex);
            char secondJumpMarkerChar = MARKER_CHARSET.charAt((i - singleJumpCount) % MARKER_CHARSET.length());

            String marker = "" + markerChar + secondJumpMarkerChar;
            _markerCollection.addMarker(marker, _offsets.get(i));
        }


        boolean hasMarkersNeedMoreJumps = i < _offsets.size();
        if (hasMarkersNeedMoreJumps) {
            for (; i < _offsets.size(); i++) {
                _markerCollection.addMarker(String.valueOf(INFINITE_JUMP_CHAR), _offsets.get(i));
            }
        }
    }

    private int calcTwiceJumpGroupCount() {
        int makerCharSetSize = MARKER_CHARSET.length();

        for (int groupsNeedMultipleJump = 0; groupsNeedMultipleJump <= makerCharSetSize; groupsNeedMultipleJump++) {
            int oneJumpMarkerCount = makerCharSetSize - groupsNeedMultipleJump;
            if (groupsNeedMultipleJump * makerCharSetSize + oneJumpMarkerCount >= _offsets.size()) {
                return groupsNeedMultipleJump;
            }
        }

        return makerCharSetSize;
    }

    private void sortOffsetsByDistanceToCaret(final int caretOffset) {
        Collections.sort(_offsets, new Comparator<JOffset>() {
            @Override
            public int compare(JOffset joA, JOffset joB) {
                if (joA.editor != joB.editor) {
                    return joA.editor.hashCode() - joB.editor.hashCode();
                }

                Integer oA = joA.offset;
                Integer oB = joB.offset;

                int distA = Math.abs(oA - caretOffset);
                int distB = Math.abs(oB - caretOffset);

                if (distA == distB) {
                    return oA - oB;
                }

                return distA - distB;
            }
        });
    }

    private void sortOffsetsToImprovePriorityOfLineEnd() {
        Collections.sort(_offsets, new Comparator<JOffset>() {
            @Override
            public int compare(JOffset joA, JOffset joB) {
                if (joA.editor != joB.editor) {
                    return joA.editor.hashCode() - joB.editor.hashCode();
                }

                Document document = joA.editor.getDocument();
                boolean oAIsLineEndOffset = isLineEndOffset(joA.offset, document);
                boolean oBIsLineEndOffset = isLineEndOffset(joB.offset, document);

                if (!(oAIsLineEndOffset ^ oBIsLineEndOffset)) {
                    return 0;
                }

                return oAIsLineEndOffset ? -1 : 1;
            }

            private boolean isLineEndOffset(Integer oA, Document document) {
                int lineA = document.getLineNumber(oA);
                int lineEndOffset = document.getLineEndOffset(lineA);
                return oA == lineEndOffset;
            }
        });
    }
}
