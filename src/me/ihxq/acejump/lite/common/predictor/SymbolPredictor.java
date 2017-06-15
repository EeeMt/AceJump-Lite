package me.ihxq.acejump.lite.common.predictor;

import me.ihxq.acejump.lite.util.Chars;

public class SymbolPredictor extends Predictor<Character> {

    @Override
    public boolean is(Character character) {
        return Chars.isSymbol(character);
    }
}
