package de.unistuttgart.bugfinder.configuration;

import de.unistuttgart.bugfinder.code.Code;
import de.unistuttgart.bugfinder.code.CodeDTO;
import de.unistuttgart.bugfinder.code.CodeMapper;
import de.unistuttgart.bugfinder.code.CodeService;
import de.unistuttgart.bugfinder.code.word.WordRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

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
    log.debug("get all configurations");
    return configurationMapper.toDTO(configurationRepository.findAll());
  }

  public Optional<ConfigurationDTO> find(final UUID id) {
    log.debug("get configuration {}", id);
    return configurationMapper.toDTO(configurationRepository.findById(id));
  }

  public ConfigurationDTO save(final ConfigurationDTO configurationDTO) {
    log.debug("create configuration {}", configurationDTO);
    return configurationMapper.toDTO(configurationRepository.save(configurationMapper.fromDTO(configurationDTO)));
  }

  public void delete(final UUID id) {
    log.debug("delete configuration {}", id);
    configurationRepository.deleteById(id);
  }

  public CodeDTO addCode(final UUID id, final CodeDTO code) {
    log.debug("add code {} to configuration {}", code, id);
    final Configuration configuration = configurationRepository.findById(id).orElseThrow();
    configuration.addCode(codeMapper.fromDTO(code));
    configurationRepository.save(configuration);
    return code;
  }

  public CodeDTO removeCode(final UUID id, final UUID codeId) {
    log.debug("remove code {} from configuration {}", codeId, id);
    final Configuration configuration = configurationRepository.findById(id).orElseThrow();
    final CodeDTO persistedCode = codeService.find(id).orElseThrow();
    configuration.removeCode(codeMapper.fromDTO(persistedCode));
    configurationRepository.save(configuration);
    return null;
  }

  public List<CodeDTO> getCodes(final UUID id) {
    log.debug("get codes from configuration {}", id);
    final Configuration configuration = configurationRepository.findById(id).orElseThrow();
    return codeMapper.toDTO(configuration.getCodes());
  }
}
