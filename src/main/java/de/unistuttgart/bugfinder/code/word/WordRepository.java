package de.unistuttgart.bugfinder.code.word;

import de.unistuttgart.bugfinder.code.Code;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepository extends JpaRepository<Word, UUID> {}
