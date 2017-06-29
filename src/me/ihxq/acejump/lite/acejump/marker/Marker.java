package me.ihxq.acejump.lite.acejump.marker;

import java.util.ArrayList;

public class Marker {
    private String _marker;
    private ArrayList<JOffset> _offsets = new ArrayList<JOffset>();

    public Marker(String marker, JOffset offset) {
        _marker = marker;
        addOffsetToMarker(offset);
    }

    void addOffsetToMarker(JOffset offset) {
        _offsets.add(offset);
    }

    public ArrayList<JOffset> getOffsets() {
        return _offsets;
    }

    char getMarkerChar() {
        return _marker.charAt(0);
    }

    public String getMarker() {
        return _marker;
    }

    public void setMarker(String _marker) {
        this._marker = _marker;
    }

    public JOffset getOffset() {
        return _offsets.get(0);
    }

    boolean isMappingToMultipleOffset() {
        return _offsets.size() > 1;
    }
}
