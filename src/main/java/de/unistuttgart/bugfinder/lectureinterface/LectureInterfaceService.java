package de.unistuttgart.bugfinder.lectureinterface;

import de.unistuttgart.bugfinder.configuration.*;
import de.unistuttgart.bugfinder.configuration.code.Code;
import de.unistuttgart.bugfinder.configuration.code.CodeRepository;
import de.unistuttgart.bugfinder.configuration.solution.SolutionRepository;
import de.unistuttgart.bugfinder.lectureinterface.dto.LectureInterfaceCodeDTO;
import de.unistuttgart.bugfinder.lectureinterface.dto.LectureInterfaceDTO;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for the lecture interface that performs a get and a save operation and acts like a facade for the domain models.
 */
@Service
@Slf4j
public class LectureInterfaceService {

  @Autowired
  private ConfigurationRepository configurationRepository;

  @Autowired
  private ConfigurationMapper configurationMapper;

  @Autowired
  private CodeRepository codeRepository;

  @Autowired
  private SolutionRepository solutionRepository;

  @Autowired
  private LectureInterfaceMapper lectureInterfaceMapper;

  @Autowired
  private ConfigurationService configurationService;

  /**
   * Get a {@code Configuration} by its id and maps it to a {@code LectureInterfaceDTO}.
   *
   * @param id the id of the configuration
   * @return the found configuration as {@code LectureInterfaceDTO}
   */
  public LectureInterfaceDTO get(UUID id) {
    log.debug("get lecture interface {}", id);
    final Configuration configuration = configurationService.getConfiguration(id);
    return lectureInterfaceMapper.configurationToLectureInterfaceDTO(configuration);
  }

  /**
   * Saves a {@code LectureInterfaceDTO} to a {@code Configuration}.
   *
   * @param lectureInterfaceDTO the {@code LectureInterfaceDTO} to save
   * @return the saved {@code ConfigurationDTO}
   */
  public ConfigurationDTO save(final LectureInterfaceDTO lectureInterfaceDTO) {
    log.debug("save lecture interface dto {}", lectureInterfaceDTO);
    Set<Code> codesToPersist = new HashSet<>(lectureInterfaceDTO.getCodes().size());
    for (LectureInterfaceCodeDTO lectureInterfaceCodeDTO : lectureInterfaceDTO.getCodes()) {
      LectureInterfaceMapper.CodeAndSolution codeAndSolution = lectureInterfaceMapper.lectureInterfaceToCodeSolutionPair(
        lectureInterfaceCodeDTO
      );

      codeRepository.save(codeAndSolution.getCode());
      solutionRepository.save(codeAndSolution.getSolution());

      codesToPersist.add(codeAndSolution.getCode());
    }
    Configuration configuration = new Configuration(null, codesToPersist);
    return configurationMapper.toDTO(configurationRepository.save(configuration));
  }
}
