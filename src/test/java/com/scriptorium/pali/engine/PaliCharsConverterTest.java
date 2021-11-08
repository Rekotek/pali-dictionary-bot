package com.scriptorium.pali.engine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaliCharsConverterTest {

    @Test
    void change_A() {
        String result = PaliCharsConverter.simplify("A");
        assertEquals("a", result);
    }

    @Test
    void change_multiple_A() {
        String result = PaliCharsConverter.simplify("AfterAfterApply");
        assertEquals("afterafterapply", result);
    }

    @Test
    void no_change() {
        String ORIG = "this is simple lowercase";
        String result = PaliCharsConverter.simplify(ORIG);
        assertEquals(ORIG, result);
    }

    @Test
    void change_pahanam() {
        String ORIG = "pahānaṃ";
        String result = PaliCharsConverter.simplify(ORIG);
        assertEquals("pahanam", result);
    }

    @Test
    void change_micchaditthika() {
        String ORIG = "micchādiṭṭhika";
        String result = PaliCharsConverter.simplify(ORIG);
        assertEquals("micchaditthika", result);
    }

    @Test
    void change_saranam() {
        String ORIG="saraṇaṃ";
        String result = PaliCharsConverter.simplify(ORIG);
        assertEquals("saranam", result);
    }

    @Test
    void change_valamigo() {
        String ORIG = "vāḷamigo";
        String result = PaliCharsConverter.simplify(ORIG);
        assertEquals("valamigo", result);
    }

    @Test
    void diacritic_maha() {
        String ORIG = "mAhA";
        String result = PaliCharsConverter.convertToDiacritic(ORIG);
        assertEquals("māhā", result);
    }

    @Test
    void diacritic_sama() {
        String ORIG = "sammAdiTThika";
        String result = PaliCharsConverter.convertToDiacritic(ORIG);
        assertEquals("sammādiṭṭhika", result);
    }

    @Test
    void diacritic_valanigo() {
        String ORIG = "vALamigo";
        String result = PaliCharsConverter.convertToDiacritic(ORIG);
        assertEquals("vāḷamigo", result);
    }
}