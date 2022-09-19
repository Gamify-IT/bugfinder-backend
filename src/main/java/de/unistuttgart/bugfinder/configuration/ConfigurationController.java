package de.unistuttgart.bugfinder.configuration;

import java.util.List;
import java.util.Optional;
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
    log.info("GET /configurations");
    return configurationService.findAll();
  }

  @GetMapping("/configurations/{id}")
  public ConfigurationDTO get(@PathVariable final UUID id) {
    log.info("GET /configurations/{}", id);
    return configurationService.find(id);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/configurations")
  public ConfigurationDTO createConfiguration(@RequestBody final ConfigurationDTO configurationDTO) {
    log.info("POST /configurations with body {}", configurationDTO);
    return configurationService.save(configurationDTO);
  }

  @PutMapping("/configurations/{id}")
  public ConfigurationDTO updateConfiguration(
    @PathVariable final UUID id,
    @RequestBody final ConfigurationDTO configurationDTO
  ) {
    log.info("PUT /configurations/{} with body {}", id, configurationDTO);
    return configurationService.save(configurationDTO);
  }

  @DeleteMapping("/configurations/{id}")
  public ConfigurationDTO deleteConfiguration(@PathVariable final UUID id) {
    log.info("DELETE /configurations/{}", id);
    return configurationService.delete(id);
  }
}
