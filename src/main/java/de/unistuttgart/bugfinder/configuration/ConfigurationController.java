package de.unistuttgart.bugfinder.configuration;

import de.unistuttgart.bugfinder.ExceptionHandlingController;
import de.unistuttgart.bugfinder.code.CodeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/configuration/{id}")
    public Optional<ConfigurationDTO> get(@PathVariable final String id) {
        log.info("GET /configuration/{}", id);
        return configurationService.find(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/configuration")
    public ConfigurationDTO createConfiguration(@RequestBody final ConfigurationDTO configurationDTO) {
        log.info("POST /configuration with body {}", configurationDTO);
        return configurationService.save(configurationDTO);
    }

    @PutMapping("/configuration/{id}")
    public ConfigurationDTO updateConfiguration(
            @PathVariable final String id,
            @RequestBody final ConfigurationDTO configurationDTO
    ) {
        log.info("PUT /configuration/{} with body {}", id, configurationDTO);
        return configurationService.save(configurationDTO);
    }

    @DeleteMapping("/configuration/{id}")
    public void deleteConfiguration(@PathVariable final String id) {
        log.info("DELETE /configuration/{}", id);
        configurationService.delete(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/configuration/{id}/code")
    public CodeDTO addCode(@PathVariable final String id, @RequestBody final CodeDTO codeDTO) {
        log.info("POST /configuration/{}/code with body {}", id, codeDTO);
        return configurationService.addCode(id, codeDTO);
    }

    @DeleteMapping("/configuration/{id}/code/{codeId}")
    public CodeDTO removeCode(@PathVariable final String id, @PathVariable final String codeId) {
        log.info("DELETE /configuration/{}/code/{}", id, codeId);
        return configurationService.removeCode(id, codeId);
    }

    @GetMapping("/configuration/{id}/codes")
    public List<CodeDTO> getCodes(@PathVariable final String id) {
        log.info("GET /configuration/{}/codes", id);
        return configurationService.getCodes(id);
    }
}
