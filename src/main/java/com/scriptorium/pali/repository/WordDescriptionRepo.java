package com.scriptorium.pali.repository;

import com.scriptorium.pali.entity.WordDescription;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordDescriptionRepo extends CrudRepository<WordDescription, Long> {
    List<WordDescription> findByPaliOrderById(String pali);

    @Query("select distinct w from WordDescription w where (w.pali like ?1%) or (w.simplified like ?1%) order by w.id")
    List<WordDescription> findPaliWide(String pali);
}
