package se.valueguard.kanboard.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

    @Transactional
    @Modifying
    @Query("update Project p set p.name = ?1 where p.id = ?2")
    int updateNameById(String name, @NonNull Long id);

    @Query("select p from Project p where p.name = :name")
    Project findByName(String name);
}
