package de.unistuttgart.bugfinder.configuration.code;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import de.unistuttgart.bugfinder.configuration.ConfigurationService;
import de.unistuttgart.bugfinder.configuration.solution.SolutionDTO;
import de.unistuttgart.bugfinder.configuration.solution.SolutionMapper;
import de.unistuttgart.bugfinder.configuration.solution.SolutionRepository;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * Service for {@code Code}.
 */
@Service
@Slf4j
public class CodeService {

  @Autowired
  public ConfigurationService configurationService;

  @Autowired
  private SolutionRepository solutionRepository;

  @Autowired
  private SolutionMapper solutionMapper;

  /**
   * Get solution of a code.
   *
   * @param codeId the id of the code of the solution
   * @return the solution of the code
   */
  public SolutionDTO getSolution(UUID codeId) {
    return solutionMapper.toDTO(
      solutionRepository
        .findByCodeId(codeId)
        .orElseThrow(() ->
          new ResponseStatusException(NOT_FOUND, String.format("Unable to find solution with id %s", codeId))
        )
    );
  }
}
