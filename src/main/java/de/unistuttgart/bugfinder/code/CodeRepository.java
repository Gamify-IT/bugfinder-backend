package de.unistuttgart.bugfinder.code;

import de.unistuttgart.bugfinder.configuration.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CodeRepository extends JpaRepository<Code, UUID> {}
