package de.unistuttgart.bugfinder.configuration;

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
  private CodeService codeService;

  @Autowired
  private ConfigurationRepository configurationRepository;

  @Autowired
  private ConfigurationMapper configurationMapper;

  @Autowired
  private CodeMapper codeMapper;

  public List<ConfigurationDTO> findAll() {
    log.info("get all configurations");
    return configurationMapper.toDTO(configurationRepository.findAll());
  }

  public Optional<ConfigurationDTO> find(final UUID id) {
    log.info("get configuration {}", id);
    return configurationMapper.toDTO(configurationRepository.findById(id));
  }

  public ConfigurationDTO save(final ConfigurationDTO configurationDTO) {
    log.info("create configuration {}", configurationDTO);
    return configurationMapper.toDTO(configurationRepository.save(configurationMapper.fromDTO(configurationDTO)));
  }

  public ConfigurationDTO delete(final UUID id) {
    log.info("delete configuration {}", id);
    Configuration configuration = configurationRepository
      .findById(id)
      .orElseThrow(() ->
        new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Configuration with id %s not found.", id))
      );
    configurationRepository.deleteById(id);
    return configurationMapper.toDTO(configuration);
  }

  public CodeDTO addCode(final UUID id, final CodeDTO code) {
    log.info("add code {} to configuration {}", code, id);
    final Configuration configuration = configurationRepository.findById(id).orElseThrow();
    final CodeDTO savedCode = codeService.save(code);
    configuration.addCode(codeMapper.fromDTO(savedCode));
    configurationRepository.save(configuration);
    return savedCode;
  }

  public CodeDTO removeCode(final UUID id, final String codeId) {
    log.info("remove code {} from configuration {}", codeId, id);
    final Configuration configuration = configurationRepository.findById(id).orElseThrow();
    final CodeDTO persistedCode = codeService.find(id).orElseThrow();
    configuration.removeCode(codeMapper.fromDTO(persistedCode));
    configurationRepository.save(configuration);
    return null;
  }

  public List<CodeDTO> getCodes(final UUID id) {
    log.info("get codes from configuration {}", id);
    final Configuration configuration = configurationRepository.findById(id).orElseThrow();
    return codeMapper.toDTO(configuration.getCodes());
  }
}
