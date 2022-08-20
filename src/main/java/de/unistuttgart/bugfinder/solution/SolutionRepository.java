package de.unistuttgart.bugfinder.solution;

import de.unistuttgart.bugfinder.configuration.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SolutionRepository extends JpaRepository<Solution, UUID> {}
