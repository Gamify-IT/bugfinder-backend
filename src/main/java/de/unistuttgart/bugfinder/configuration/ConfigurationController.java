package de.unistuttgart.bugfinder.configuration;

import de.unistuttgart.bugfinder.ExceptionHandlingController;
import de.unistuttgart.bugfinder.code.CodeDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class ConfigurationController extends ExceptionHandlingController {

  @Autowired
  private ConfigurationService configurationService;

  @GetMapping("/configurations")
  public List<ConfigurationDTO> getAll() {
    log.info("GET /configurations");
    return configurationService.findAll();
  }

  @GetMapping("/configurations/{id}")
  public Optional<ConfigurationDTO> get(@PathVariable final UUID id) {
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
  public void deleteConfiguration(@PathVariable final UUID id) {
    log.info("DELETE /configurations/{}", id);
    configurationService.delete(id);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/configurations/{id}/code")
  public CodeDTO addCode(@PathVariable final UUID id, @RequestBody final CodeDTO codeDTO) {
    log.info("POST /configurations/{}/code with body {}", id, codeDTO);
    return configurationService.addCode(id, codeDTO);
  }

  @DeleteMapping("/configurations/{id}/code/{codeId}")
  public CodeDTO removeCode(@PathVariable final UUID id, @PathVariable final String codeId) {
    log.info("DELETE /configurations/{}/code/{}", id, codeId);
    return configurationService.removeCode(id, codeId);
  }

  @GetMapping("/configurations/{id}/codes")
  public List<CodeDTO> getCodes(@PathVariable final UUID id) {
    log.info("GET /configurations/{}/codes", id);
    return configurationService.getCodes(id);
  }
}
