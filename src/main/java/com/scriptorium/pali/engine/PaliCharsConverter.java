package com.scriptorium.pali.engine;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map;

public class PaliCharsConverter {
    private static final Map<String, String> toLatinMap = Map.ofEntries(
        new SimpleImmutableEntry<>("ā", "a"),
        new SimpleImmutableEntry<>("A", "a"),
        new SimpleImmutableEntry<>("â", "a"),
        new SimpleImmutableEntry<>("ī", "i"),
        new SimpleImmutableEntry<>("I", "i"),
        new SimpleImmutableEntry<>("ū", "u"),
        new SimpleImmutableEntry<>("U", "u"),
        new SimpleImmutableEntry<>("ñ", "n"),
        new SimpleImmutableEntry<>("ṇ", "n"),
        new SimpleImmutableEntry<>("ṅ", "n"),
        new SimpleImmutableEntry<>("N", "n"),
        new SimpleImmutableEntry<>("ṃ", "m"),
        new SimpleImmutableEntry<>("M", "m"),
        new SimpleImmutableEntry<>("ṭ", "t"),
        new SimpleImmutableEntry<>("T", "t"),
        new SimpleImmutableEntry<>("D", "d"),
        new SimpleImmutableEntry<>("ḍ", "d"),
        new SimpleImmutableEntry<>("L", "l"),
        new SimpleImmutableEntry<>("ḷ", "l")
    );

    public static final Map<String, String> fromLatinMap = Map.ofEntries(
            new SimpleImmutableEntry<>("A", "ā"),
            new SimpleImmutableEntry<>("I", "ī"),
            new SimpleImmutableEntry<>("U", "ū"),
            new SimpleImmutableEntry<>("T", "ṭ"),
            new SimpleImmutableEntry<>("D", "ḍ"),
            new SimpleImmutableEntry<>("M", "ṃ"),
            new SimpleImmutableEntry<>("G", "ṅ"),
            new SimpleImmutableEntry<>("N", "ṇ"),
            new SimpleImmutableEntry<>("J", "ñ"),
            new SimpleImmutableEntry<>("L", "ḷ")
    );

    public static String simplify(String original) {
        String result = original.trim();
        for (var entry : toLatinMap.entrySet()) {
            result = result.replaceAll(entry.getKey(), entry.getValue());
        }
        return result;
    }

    public static String convertToDiacritic(String original) {
        String result = original.trim();
        for (var entry : fromLatinMap.entrySet()) {
            result = result.replaceAll(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
