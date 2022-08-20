package de.unistuttgart.bugfinder.solution.bug;

import de.unistuttgart.bugfinder.code.word.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BugRepository extends JpaRepository<Bug, UUID> {}
