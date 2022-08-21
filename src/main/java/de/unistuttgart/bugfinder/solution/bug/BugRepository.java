package de.unistuttgart.bugfinder.solution.bug;

import de.unistuttgart.bugfinder.code.word.Word;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BugRepository extends JpaRepository<Bug, UUID> {}
