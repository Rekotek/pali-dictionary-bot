package com.scriptorium.pali.comparator;

import com.scriptorium.pali.entity.WordDescription;

import java.util.Comparator;

public class WordDescriptionComparator implements Comparator<WordDescription> {
    @Override
    public int compare(WordDescription w1, WordDescription w2) {
        String pali1 = w1.getPali();
        String pali2 = w2.getPali();

        int compareByPali = new PaliStringComparator().compare(pali1, pali2);
        if (compareByPali == 0) {
            return (int) (w1.getId() - w2.getId());
        } else {
            return compareByPali;
        }
    }
}
