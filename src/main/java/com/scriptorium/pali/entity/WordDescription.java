package com.scriptorium.pali.entity;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private Long id;

    private String pali;

    private String simplified;

    @Column(length = MAX_VARCHAR)
    private String translation;
}
