package de.unistuttgart.bugfinder.configuration;

import static de.unistuttgart.bugfinder.data.Roles.LECTURER_ROLE;

import de.unistuttgart.bugfinder.configuration.vm.ConfigurationVM;
import de.unistuttgart.gamifyit.authentificationvalidator.JWTValidatorService;
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
  private JWTValidatorService jwtValidatorService;

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
  public ConfigurationDTO createConfiguration(
    @RequestBody final ConfigurationDTO configurationDTO,
    @CookieValue("access_token") final String accessToken
  ) {
    jwtValidatorService.validateTokenOrThrow(accessToken);
    jwtValidatorService.hasRolesOrThrow(accessToken, LECTURER_ROLE);
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
  public ConfigurationDTO buildConfiguration(
    @RequestBody final ConfigurationVM configurationBuilderCodeDTO,
    @CookieValue("access_token") final String accessToken
  ) {
    jwtValidatorService.validateTokenOrThrow(accessToken);
    jwtValidatorService.hasRolesOrThrow(accessToken, LECTURER_ROLE);
    log.debug("POST /configurations/builder with body {}", configurationBuilderCodeDTO);
    return configurationService.build(configurationBuilderCodeDTO);
  }

  @PutMapping("/configurations/{id}")
  public ConfigurationDTO updateConfiguration(
    @PathVariable final UUID id,
    @RequestBody final ConfigurationDTO configurationDTO,
    @CookieValue("access_token") final String accessToken
  ) {
    jwtValidatorService.validateTokenOrThrow(accessToken);
    jwtValidatorService.hasRolesOrThrow(accessToken, LECTURER_ROLE);
    log.debug("PUT /configurations/{} with body {}", id, configurationDTO);
    return configurationService.save(configurationDTO);
  }

  @DeleteMapping("/configurations/{id}")
  public ConfigurationDTO deleteConfiguration(
    @PathVariable final UUID id,
    @CookieValue("access_token") final String accessToken
  ) {
    jwtValidatorService.validateTokenOrThrow(accessToken);
    jwtValidatorService.hasRolesOrThrow(accessToken, LECTURER_ROLE);
    log.debug("DELETE /configurations/{}", id);
    return configurationService.delete(id);
  }

  @PostMapping("/configurations/{id}/clone")
  public UUID cloneConfiguration(@PathVariable final UUID id, @CookieValue("access_token") final String accessToken) {
    jwtValidatorService.validateTokenOrThrow(accessToken);
    jwtValidatorService.hasRolesOrThrow(accessToken, LECTURER_ROLE);
    log.debug("Clone /configurations/{}", id);
    return configurationService.cloneConfiguration(id);
  }
}
