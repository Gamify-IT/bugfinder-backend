package de.unistuttgart.bugfinder.solution;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SolutionRepository extends JpaRepository<Solution, UUID> {
  @Query("select s from Solution s where s.code.id = :codeId")
  Optional<Solution> findByCodeId(@Param("codeId") final UUID codeId);
}
