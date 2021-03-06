package com.scriptorium.pali.entity;

import com.scriptorium.pali.engine.PaliCharsConverter;
import com.scriptorium.pali.enums.WordForm;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WordDescription {
    private static final int MAX_VARCHAR = 1024*1024;

    @SequenceGenerator(name = "gen_word_desc", sequenceName = "word_description_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_word_desc")
    @Id
    private Long id;

    private String pali;

    private String simplified;

    @Column(length = MAX_VARCHAR)
    private String translation;

    private Integer level;

    @Enumerated(EnumType.STRING)
    private WordForm wordForm;

    public void setPali(String pali) {
        this.pali = pali;
        this.simplified = PaliCharsConverter.simplify(pali);
    }

}
