package de.unistuttgart.bugfinder.configuration;

import de.unistuttgart.bugfinder.code.CodeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @GetMapping("/configuration/{id}")
    public Optional<ConfigurationDTO> get(@PathVariable final UUID id) {
        log.debug("GET /configuration/{}", id);
        return configurationService.find(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/configuration")
    public ConfigurationDTO createConfiguration(@RequestBody final ConfigurationDTO configurationDTO) {
        log.debug("POST /configuration with body {}", configurationDTO);
        return configurationService.save(configurationDTO);
    }

    @PutMapping("/configuration/{id}")
    public ConfigurationDTO updateConfiguration(@PathVariable final UUID id, @RequestBody final ConfigurationDTO configurationDTO) {
        log.debug("PUT /configuration/{} with body {}", id, configurationDTO);
        return configurationService.save(configurationDTO);
    }

    @DeleteMapping("/configuration/{id}")
    public void deleteConfiguration(@PathVariable final UUID id) {
        log.debug("DELETE /configuration/{}", id);
        configurationService.delete(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/configuration/{id}/code")
    public CodeDTO addCode(@PathVariable final UUID id, @RequestBody final CodeDTO codeDTO) {
        log.debug("POST /configuration/{}/code with body {}", id, codeDTO);
        return configurationService.addCode(id, codeDTO);
    }

    @DeleteMapping("/configuration/{id}/code/{codeId}")
    public CodeDTO removeCode(@PathVariable final UUID id, @PathVariable final UUID codeId) {
        log.debug("DELETE /configuration/{}/code/{}", id, codeId);
        return configurationService.removeCode(id, codeId);
    }

    @GetMapping("/configuration/{id}/codes")
    public List<CodeDTO> getCodes(@PathVariable final UUID id) {
        log.debug("GET /configuration/{}/codes", id);
        return configurationService.getCodes(id);
    }
}
