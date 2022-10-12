package de.unistuttgart.bugfinder.configuration.solution;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository for Solution.
 */
@Repository
public interface SolutionRepository extends JpaRepository<Solution, UUID> {
    @Query("select s from Solution s where s.code.id = :codeId")
    Optional<Solution> findByCodeId(@Param("codeId") final UUID codeId);
}
