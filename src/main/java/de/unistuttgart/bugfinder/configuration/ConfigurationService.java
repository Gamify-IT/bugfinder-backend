package de.unistuttgart.bugfinder.configuration;

import de.unistuttgart.bugfinder.client.OverworldClient;
import de.unistuttgart.bugfinder.code.Code;
import de.unistuttgart.bugfinder.code.CodeDTO;
import de.unistuttgart.bugfinder.code.CodeMapper;
import de.unistuttgart.bugfinder.code.CodeRepository;
import de.unistuttgart.bugfinder.code.word.Word;
import de.unistuttgart.bugfinder.code.word.WordRepository;
import de.unistuttgart.bugfinder.configuration.vm.CodeVM;
import de.unistuttgart.bugfinder.configuration.vm.ConfigurationVM;
import de.unistuttgart.bugfinder.configuration.vm.WordVM;
import de.unistuttgart.bugfinder.solution.Solution;
import de.unistuttgart.bugfinder.solution.SolutionRepository;
import de.unistuttgart.bugfinder.solution.bug.Bug;
import de.unistuttgart.bugfinder.solution.bug.BugRepository;
import java.util.*;

import de.unistuttgart.gamifyit.authentificationvalidator.JWTValidatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
public class ConfigurationService {

  @Autowired
  private ConfigurationRepository configurationRepository;

  @Autowired
  private ConfigurationMapper configurationMapper;

  @Autowired
  private CodeMapper codeMapper;

  @Autowired
  private CodeRepository codeRepository;

  @Autowired
  private SolutionRepository solutionRepository;

  @Autowired
  private WordRepository wordRepository;

  @Autowired
  private BugRepository bugRepository;

  @Autowired
  private OverworldClient overworldClient;

  @Autowired
  private JWTValidatorService jwtValidatorService;

  /**
   * Get a configuration by its id.
   *
   * @param id the id of the configuration
   * @return the found configuration
   * @throws ResponseStatusException (404) when configuration with its id does not exist
   */
  public Configuration getConfiguration(final UUID id) {
    return configurationRepository
      .findById(id)
      .orElseThrow(() ->
        new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Configuration with id %s not found.", id))
      );
  }

  /**
   * Search a configuration by given id and get volume level from overworld-backend
   *
   * @param id the id of the configuration searching for
   * @param accessToken the users access token
   * @return the found configuration
   * @throws ResponseStatusException  when configuration by configurationName could not be found
   * @throws IllegalArgumentException if at least one of the arguments is null
   */
  public Configuration getAllConfigurations(final UUID id, final String accessToken) {
    if (id == null) {
      throw new IllegalArgumentException("id is null");
    }
    final String userId = jwtValidatorService.extractUserId(accessToken);

    KeybindingDTO keyBindingVolumeLevel = overworldClient.getKeybindingStatistic(userId, "VOLUME_LEVEL", accessToken);
    Integer volumeLevel;
    if (keyBindingVolumeLevel.getKey() == null || keyBindingVolumeLevel.getKey().isEmpty()) {
      volumeLevel = 0;
    }
    else {
      try {
        volumeLevel = Integer.parseInt(keyBindingVolumeLevel.getKey());
      } catch (NumberFormatException e) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid volume level format.");
      }
    }

    Configuration config = configurationRepository
            .findById(id)
            .orElseThrow(() ->
                    new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            String.format("There is no configuration with id %s.", id)
                    )
            );
    config.setVolumeLevel(volumeLevel);
    return configurationRepository
            .findById(id)
            .orElseThrow(() ->
                    new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            String.format("There is no configuration with id %s.", id)
                    )
            );
  }


  /**
   * @return all configurations as list of DTOs
   */
  public List<ConfigurationDTO> findAll() {
    log.debug("get all configurations");
    return configurationMapper.toDTO(configurationRepository.findAll());
  }

  /**
   * Get the configuration by its id as DTO.
   *
   * @param id the id of the configuration
   * @return the found configuration as DTO
   * @throws ResponseStatusException (404) when configuration with its id does not exist
   */
  public ConfigurationDTO find(final UUID id) {
    log.debug("get configuration {}", id);
    Configuration configuration = getConfiguration(id);
    return configurationMapper.toDTO(configuration);
  }

  /**
   * Save a configuration.
   *
   * @param configurationDTO the configuration to save
   * @return the saved configuration as DTO
   */
  public ConfigurationDTO save(final ConfigurationDTO configurationDTO) {
    log.debug("create configuration {}", configurationDTO);
    return configurationMapper.toDTO(configurationRepository.save(configurationMapper.fromDTO(configurationDTO)));
  }

  public ConfigurationDTO build(final ConfigurationVM configurationVM) { //NOSONAR
    Set<Code> codesToPersist = new HashSet<>(configurationVM.getCodes().size());
    for (CodeVM codeVM : configurationVM.getCodes()) {
      List<Word> wordsToPersistToCode = new ArrayList<>(codeVM.getWords().size());
      Set<Bug> bugsToPersistToSolution = new HashSet<>();
      for (List<WordVM> row : codeVM.getWords()) {
        // check if the row only contains blank strings
        // the first row will never contain only blank strings since it comes from the lecture interface
        if (row.stream().allMatch(wordVM -> wordVM.getCorrectValue().isBlank())) {
          continue;
        }
        // if it is not the first row in the code we add a new line
        if (codeVM.getWords().indexOf(row) != 0) {
          wordsToPersistToCode.add(wordRepository.save(new Word(null, "\n")));
        }
        for (WordVM wordVM : row) {
          // check if the word is blank
          // the first word will never be blank since it comes from the lecture interface
          if (wordVM.getCorrectValue().isBlank()) {
            continue;
          }
          // if it is not the first word in the row we add a space
          if (row.indexOf(wordVM) != 0) {
            wordsToPersistToCode.add(wordRepository.save(new Word(null, " ")));
          }
          String displayValue = wordVM.getDisplayValue() != null ? wordVM.getDisplayValue() : wordVM.getCorrectValue();
          Word word = new Word(null, displayValue);
          word = wordRepository.save(word);
          wordsToPersistToCode.add(word);
          if (wordVM.getErrorType() != null) {
            Bug bug = new Bug(word, wordVM.getErrorType(), wordVM.getCorrectValue());
            bug = bugRepository.save(bug);
            bugsToPersistToSolution.add(bug);
          }
        }
      }
      Code code = new Code(null, wordsToPersistToCode);
      codesToPersist.add(code);
      code = codeRepository.save(code);
      Solution solution = new Solution(null, bugsToPersistToSolution, code);
      solutionRepository.save(solution);
    }
    Configuration configuration = new Configuration(null, codesToPersist);
    return configurationMapper.toDTO(configurationRepository.save(configuration));
  }

  /**
   * Deletes the configuration with the given ID, if present.
   *
   * @param id the ID of the configuration to delete
   * @return the deleted configuration, if found
   * @throws ResponseStatusException (204 - NO_CONTENT) if the configuration was not found
   */
  public ConfigurationDTO delete(final UUID id) {
    log.info("delete configuration {}", id);
    Configuration configuration = configurationRepository
      .findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
    configuration
      .getCodes()
      .parallelStream()
      .map(code -> solutionRepository.findByCodeId(code.getId()))
      .filter(Optional::isPresent)
      .forEach(solution -> solutionRepository.deleteById(solution.get().getId()));
    configurationRepository.deleteById(id);
    return configurationMapper.toDTO(configuration);
  }

  /**
   * Get all codes of a configuration.
   *
   * @param id the configuration id
   * @return the codes of the specified configuration
   */
  public List<CodeDTO> getCodes(final UUID id) {
    log.debug("get codes from configuration {}", id);
    final Configuration configuration = getConfiguration(id);
    return codeMapper.toDTO(configuration.getCodes());
  }

  /**
   * Clones a configuration
   * @param id Id of the configuration
   * @return Id of the cloned configuration
   */
  public UUID cloneConfiguration(final UUID id) {
    final ConfigurationVM configuration = getViewModel(id);
    final ConfigurationDTO cloneConfiguration = build(configuration);
    return cloneConfiguration.getId();
  }

  public ConfigurationVM getViewModel(UUID id) {
    log.debug("get configuration view model {}", id);
    final Configuration configuration = getConfiguration(id);
    final ConfigurationVM configurationVM = new ConfigurationVM(new ArrayList<>());
    for (Code code : configuration.getCodes()) {
      final CodeVM codeVM = new CodeVM(new ArrayList<>());
      final Solution solution = solutionRepository
        .findByCodeId(code.getId())
        .orElseThrow(() ->
          new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            String.format("Solution with code id %s not found.", code.getId())
          )
        );

      List<WordVM> row = new ArrayList<>();
      for (Word word : code.getWords()) { //NOSONAR
        if (word.getWordContent().equals(" ")) {
          continue;
        }
        if (word.getWordContent().equals("\n")) {
          codeVM.getWords().add(row);
          row = new ArrayList<>();
          continue;
        }
        Optional<Bug> bug = solution
          .getBugs()
          .stream()
          .filter(b -> b.getWord().getId().equals(word.getId()))
          .findFirst();
        final WordVM wordVM = new WordVM();
        if (bug.isPresent()) {
          wordVM.setErrorType(bug.get().getErrorType());
          wordVM.setCorrectValue(bug.get().getCorrectValue());
          wordVM.setDisplayValue(word.getWordContent());
        } else {
          wordVM.setCorrectValue(word.getWordContent());
        }
        row.add(wordVM);
      }
      codeVM.getWords().add(row);
      configurationVM.getCodes().add(codeVM);
    }

    return configurationVM;
  }
}
