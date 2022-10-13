package de.unistuttgart.bugfinder.configuration.code.word;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@code Word}.
 */
@Repository
public interface WordRepository extends JpaRepository<Word, UUID> {}
