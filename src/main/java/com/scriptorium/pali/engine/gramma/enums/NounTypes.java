package com.scriptorium.pali.engine.gramma.enums;

import java.util.HashMap;
import java.util.Map;

import static com.scriptorium.pali.engine.gramma.enums.Gender.*;

public enum NounTypes {
    A_MUS("a", "o", MUSCLE),
    AM_NEU("aṃ", "aṃ", NEUTRAL),
    AA_FEM("ā", "ā", FEMALE),
    I_FEM("i", "i", FEMALE),
    II_FEM("ī", "ī", FEMALE),
    U_MUS("u", "u", MUSCLE),
    U_NEU("u", "uṃ", MUSCLE),
    ;

    private static final Map<String, NounTypes> VOCABULARY_FORM = new HashMap<>();
    static {
        for (var e : values()) {
            VOCABULARY_FORM.put(e.vocabularyForm, e);
        }
    }

    private final String endForm;
    private final String vocabularyForm;
    private final Gender gender;

    NounTypes(String endingForm, String vocabularyForm, Gender gender) {
        this.endForm = endingForm;
        this.vocabularyForm = vocabularyForm;
        this.gender = gender;
    }

    public static NounTypes getNounTypeByForm(String vocabularyForm) {
        return VOCABULARY_FORM.get(vocabularyForm);
    }
}
