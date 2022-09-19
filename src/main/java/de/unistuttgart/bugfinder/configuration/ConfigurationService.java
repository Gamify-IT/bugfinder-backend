package de.unistuttgart.bugfinder.configuration;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import de.unistuttgart.bugfinder.code.CodeDTO;
import de.unistuttgart.bugfinder.code.CodeMapper;
import de.unistuttgart.bugfinder.code.CodeService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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

  /**
   * Get a configuration by its id.
   * @param id the id of the configuration
   * @throws ResponseStatusException (404) when configuration with its id does not exist
   * @return the found configuration
   */
  public Configuration getConfiguration(final UUID id) {
    return configurationRepository
      .findById(id)
      .orElseThrow(() ->
        new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Configuration with id %s not found.", id))
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
   * @param id the id of the configuration
   * @throws ResponseStatusException (404) when configuration with its id does not exist
   * @return the found configuration as DTO
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
}
