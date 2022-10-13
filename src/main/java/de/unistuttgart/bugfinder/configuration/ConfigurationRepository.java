package de.unistuttgart.bugfinder.configuration;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@code Configuration}.
 */
@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, UUID> {}
