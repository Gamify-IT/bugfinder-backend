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

  public List<ConfigurationDTO> findAll() {
    log.debug("get all configurations");
    return configurationMapper.toDTO(configurationRepository.findAll());
  }

  public ConfigurationDTO find(final UUID id) {
    log.debug("get configuration {}", id);
    Configuration configuration = configurationRepository
      .findById(id)
      .orElseThrow(() ->
        new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Configuration with id %s not found.", id))
      );
    return configurationMapper.toDTO(configuration);
  }

  public ConfigurationDTO save(final ConfigurationDTO configurationDTO) {
    log.debug("create configuration {}", configurationDTO);
    return configurationMapper.toDTO(configurationRepository.save(configurationMapper.fromDTO(configurationDTO)));
  }

  public ConfigurationDTO delete(final UUID id) {
    log.debug("delete configuration {}", id);
    Configuration configuration = configurationRepository
      .findById(id)
      .orElseThrow(() ->
        new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Configuration with id %s not found.", id))
      );
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
    final Configuration configuration = configurationRepository
      .findById(id)
      .orElseThrow(() ->
        new ResponseStatusException(NOT_FOUND, String.format("Unable to find configuration with id %s", id))
      );
    return codeMapper.toDTO(configuration.getCodes());
  }
}
