package com.scriptorium.pali.comparator;

import com.scriptorium.pali.entity.dto.WordDescriptionDto;

import java.util.Comparator;

public class WordDescriptionDtoComparator implements Comparator<WordDescriptionDto> {
    @Override
    public int compare(WordDescriptionDto w1, WordDescriptionDto w2) {
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
