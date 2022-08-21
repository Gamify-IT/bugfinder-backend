package de.unistuttgart.bugfinder.code;

import de.unistuttgart.bugfinder.configuration.Configuration;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeRepository extends JpaRepository<Code, UUID> {}
