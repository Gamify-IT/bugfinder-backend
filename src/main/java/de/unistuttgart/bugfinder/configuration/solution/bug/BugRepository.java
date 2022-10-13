package de.unistuttgart.bugfinder.configuration.solution.bug;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@code Bug}.
 */
@Repository
public interface BugRepository extends JpaRepository<Bug, UUID> {}
