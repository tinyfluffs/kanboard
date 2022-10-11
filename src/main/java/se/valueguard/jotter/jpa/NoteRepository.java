package se.valueguard.jotter.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface NoteRepository extends CrudRepository<Note, Long> {

    @Transactional
    @Modifying
    @Query("update Note p set p.title = ?1 where p.id = ?2")
    int updateTitleById(String title, @NonNull Long id);

    @Query("select p from Note p where p.title = :title")
    Note findByTitle(String title);
}
