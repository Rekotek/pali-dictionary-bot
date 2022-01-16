package com.scriptorium.pali.engine.gramma.enums;

public enum WordForm {
    NOUN("", "noun", "существительное"),
    ADJ("adj", "adjective", "прилагательное"),
    ADV("adv", "adverb", "наречие"),
    PRAEP("praep", "praeposition", "предлог"),
    CONJ("conj", "conjuction", "союз"),
    PCL("pcl", "particule", "частица"),
    IJ("ij", "interjection", "междометие"),
    PR("pr", "present", "настоящее время"),
    GER("ger", "gerund", "герундий"),
    INF("inf", "infinitive", "инфинитив"),
    FUT("fut", "future", "будущее время"),
    VERB("v", "verb", "глагол")
    ;

    WordForm(String symbol, String longDefinition, String rusDefinition) {
    }
}
