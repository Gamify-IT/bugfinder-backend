package de.unistuttgart.bugfinder.configuration;

import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for {@code Configuration}.
 */
@RestController
@Slf4j
public class ConfigurationController {

  @Autowired
  private ConfigurationService configurationService;

  /**
   * Returns all {@code Configuration}s.
   *
   * @return all {@code Configuration}s (200)
   */
  @GetMapping("/configurations")
  public List<ConfigurationDTO> getAll() {
    log.debug("GET /configurations");
    return configurationService.findAll();
  }

  /**
   * Returns the {@code Configuration} with the given id.
   *
   * @param id the id of the {@code Configuration}
   * @return the {@code Configuration} with the given id (200)
   */
  @GetMapping("/configurations/{id}")
  public ConfigurationDTO get(@PathVariable final UUID id) {
    log.debug("GET /configurations/{}", id);
    return configurationService.find(id);
  }

  /**
   * Creates a new {@code Configuration}.
   * <p>
   * The id of the {@code Configuration} must be null.
   * </p>
   *
   * @param configurationDTO the {@code Configuration} to create
   * @return the created {@code Configuration} (201)
   */
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/configurations")
  public ConfigurationDTO createConfiguration(@RequestBody final ConfigurationDTO configurationDTO) {
    log.debug("POST /configurations with body {}", configurationDTO);
    return configurationService.save(configurationDTO);
  }

  /**
   * Deletes the {@code Configuration} with the given id.
   *
   * @param id the id of the {@code Configuration} to delete
   */
  @DeleteMapping("/configurations/{id}")
  public ConfigurationDTO deleteConfiguration(@PathVariable final UUID id) {
    log.debug("DELETE /configurations/{}", id);
    return configurationService.delete(id);
  }
}
