package com.scriptorium.pali.entity.dto;

import com.scriptorium.pali.common.HtmlOutput;
import com.scriptorium.pali.enums.WordForm;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class WordDescriptionDto implements Serializable, HtmlOutput {
    private Long id;
    private String pali;
    private String translation;
    private Integer level;
    private WordForm wordForm;

    @Override
    public String toHtml() {
        return String.format("<b>%s</b>\n%s\n\n", pali, translation);
    }
}
