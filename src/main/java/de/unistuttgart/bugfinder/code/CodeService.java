package de.unistuttgart.bugfinder.code;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import de.unistuttgart.bugfinder.code.word.WordDTO;
import de.unistuttgart.bugfinder.code.word.WordMapper;
import de.unistuttgart.bugfinder.code.word.WordService;
import de.unistuttgart.bugfinder.configuration.ConfigurationService;
import de.unistuttgart.bugfinder.solution.SolutionDTO;
import de.unistuttgart.bugfinder.solution.SolutionMapper;
import de.unistuttgart.bugfinder.solution.SolutionRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
