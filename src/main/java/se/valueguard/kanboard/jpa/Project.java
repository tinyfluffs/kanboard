package se.valueguard.kanboard.jpa;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;

@Data
@Entity
@RequiredArgsConstructor
public class Project extends AbstractPersistable<Long> {

    @Column(unique = true)
    private String name;
}
