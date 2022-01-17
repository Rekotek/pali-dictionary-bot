package com.scriptorium.pali.repository;

import com.scriptorium.pali.entity.WordDescription;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.List;

import static org.hibernate.jpa.QueryHints.HINT_CACHEABLE;

@Repository
public interface WordDescriptionRepo extends CrudRepository<WordDescription, Long> {
    List<WordDescription> findByPaliOrderById(String pali);

    @QueryHints(value = { @QueryHint(name = HINT_CACHEABLE, value = "true")})
    @Query("select distinct w from WordDescription w where (w.pali like ?1%) or (w.simplified like ?1%) order by w.id")
    List<WordDescription> findPaliWide(String pali);

    @QueryHints(value = {@QueryHint(name = HINT_CACHEABLE, value = "true")})
    @Query(value = "select * from word_description as w where w.translation ~* ?1", nativeQuery = true)
    List<WordDescription> findInsideTranslation(String word);
}
