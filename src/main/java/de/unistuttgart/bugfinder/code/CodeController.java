package de.unistuttgart.bugfinder.code;

import de.unistuttgart.bugfinder.configuration.ConfigurationService;
import de.unistuttgart.bugfinder.solution.SolutionDTO;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/configurations/{configurationId}")
public class CodeController {

  @Autowired
  private CodeService codeService;

  @Autowired
  private ConfigurationService configurationService;

  @GetMapping("/codes")
  public List<CodeDTO> getAll(@PathVariable final UUID configurationId) {
    log.debug("GET /configurations/{}/codes", configurationId);
    return configurationService.getCodes(configurationId);
  }

  @GetMapping("/codes/{codeId}/solutions")
  public SolutionDTO getSolution(@PathVariable final UUID configurationId, @PathVariable final UUID codeId) {
    log.debug("GET /configurations/{}/codes/{}/solutions", configurationId, codeId);
    return codeService.getSolution(codeId);
  }
}
