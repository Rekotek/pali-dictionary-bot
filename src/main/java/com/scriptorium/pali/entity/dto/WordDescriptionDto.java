package com.scriptorium.pali.entity.dto;

import com.scriptorium.pali.enums.WordForm;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class WordDescriptionDto implements Serializable {
    private Long id;
    private String pali;
    private String translation;
    private Integer level;
    private WordForm wordForm;
}
