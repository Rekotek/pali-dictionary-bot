package com.scriptorium.pali.engine.gramma.enums;

public enum WordCase {
    NOM("N", "Nominativus", "Именительный"),
    ACC("A", "Accusativus", "Винительный"),
    INS("I", "Instrumentalis", "Творительный"),
    DAT("D", "Dativus", "Дательный"),
    ABL("Abl", "Ablativus", "Отложительный"),
    GER("G", "Genetivus", "Родительный"),
    LOC("L", "Locativus", "Местный"),
    VOC("Voc", "Vocativus", "Звательный");

    WordCase(String symbol, String longDefinition, String rusDefinition) {
    }
}
