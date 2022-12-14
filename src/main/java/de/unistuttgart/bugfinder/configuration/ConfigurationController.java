package de.unistuttgart.bugfinder.configuration;

import de.unistuttgart.bugfinder.configuration.vm.ConfigurationVM;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class ConfigurationController {

  @Autowired
  private ConfigurationService configurationService;

  @GetMapping("/configurations")
  public List<ConfigurationDTO> getAll() {
    log.debug("GET /configurations");
    return configurationService.findAll();
  }

  @GetMapping("/configurations/{id}")
  public ConfigurationDTO get(@PathVariable final UUID id) {
    log.debug("GET /configurations/{}", id);
    return configurationService.find(id);
  }

  @GetMapping("/configurations/vm/{id}")
  public ConfigurationVM getVM(@PathVariable final UUID id) {
    log.debug("GET /configurations/{}", id);
    return configurationService.getViewModel(id);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/configurations")
  public ConfigurationDTO createConfiguration(@RequestBody final ConfigurationDTO configurationDTO) {
    log.debug("POST /configurations with body {}", configurationDTO);
    return configurationService.save(configurationDTO);
  }

  /**
   * used by the lecture interface to build a configuration by the view model
   *
   * @param configurationBuilderCodeDTO
   * @return
   */
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/configurations/build")
  public ConfigurationDTO buildConfiguration(@RequestBody final ConfigurationVM configurationBuilderCodeDTO) {
    log.debug("POST /configurations/builder with body {}", configurationBuilderCodeDTO);
    return configurationService.build(configurationBuilderCodeDTO);
  }

  @PutMapping("/configurations/{id}")
  public ConfigurationDTO updateConfiguration(
    @PathVariable final UUID id,
    @RequestBody final ConfigurationDTO configurationDTO
  ) {
    log.debug("PUT /configurations/{} with body {}", id, configurationDTO);
    return configurationService.save(configurationDTO);
  }

  @DeleteMapping("/configurations/{id}")
  public ConfigurationDTO deleteConfiguration(@PathVariable final UUID id) {
    log.debug("DELETE /configurations/{}", id);
    return configurationService.delete(id);
  }
}
