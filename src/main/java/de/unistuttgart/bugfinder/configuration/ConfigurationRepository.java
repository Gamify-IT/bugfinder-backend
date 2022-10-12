package de.unistuttgart.bugfinder.configuration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository for {@code Configuration}.
 */
@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, UUID> {
}
