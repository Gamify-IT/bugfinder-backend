package de.unistuttgart.bugfinder.code.word;

import de.unistuttgart.bugfinder.code.Code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WordRepository extends JpaRepository<Word, UUID> {}
