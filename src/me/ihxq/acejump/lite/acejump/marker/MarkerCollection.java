package me.ihxq.acejump.lite.acejump.marker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MarkerCollection extends HashMap<String, Marker> {

    public ArrayList<JOffset> getOffsetsOfKey(char key) {
        ArrayList<JOffset> ai = new ArrayList<JOffset>();

        for (Marker marker : this.values()) {
            if (marker.getMarkerChar() == key) {
                ai.addAll(marker.getOffsets());
            }
        }

        Collections.reverse(ai);
        return ai;
    }

    public void addMarker(String key, JOffset offset) {
        Marker marker = this.get(key);

        if (marker == null) {
            this.put(key, new Marker(key, offset));
            return;
        }

        marker.addOffsetToMarker(offset);
    }

    public JOffset getFirstOffset() {
        return this.values().iterator().next().getOffset();
    }

    public boolean hasOnlyOnePlaceToJump() {
        return this.size() == 1;
    }

    public boolean hasNoPlaceToJump() {
        return this.isEmpty();
    }

    public boolean containsMarkerWithKey(char key) {
        for (Marker marker : this.values()) {
            if (marker.getMarkerChar() == key) {
                return true;
            }
        }

        return false;
    }
}
