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
    @Query("update Note n set n.title = ?1 where n.id = ?2")
    int updateTitleById(String title, @NonNull Long id);

    @Transactional
    @Modifying
    @Query("update Note n set n.content = ?1 where n.id = ?2")
    int updateContentById(String content, @NonNull Long id);

    @Query("select n from Note n where n.title = :title")
    Note findByTitle(String title);
}
