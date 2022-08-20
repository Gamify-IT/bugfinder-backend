package de.unistuttgart.bugfinder.solution;

import de.unistuttgart.bugfinder.solution.bug.BugDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@Slf4j
public class SolutionController {

    @Autowired
    private SolutionService solutionService;

    @GetMapping("/solutions")
    public List<SolutionDTO> getAll() {
        log.debug("GET /solutions");
        return solutionService.findAll();
    }

    @GetMapping("/solution/{id}")
    public Optional<SolutionDTO> get(@PathVariable final UUID id) {
        log.debug("GET /solution/{}", id);
        return solutionService.find(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/solution")
    public SolutionDTO Sreatesolution(@RequestBody final SolutionDTO solutionDTO) {
        log.debug("POST /solution with body {}", solutionDTO);
        return solutionService.save(solutionDTO);
    }

    @PutMapping("/solution/{id}")
    public SolutionDTO Spdatesolution(@PathVariable final UUID id, @RequestBody final SolutionDTO solutionDTO) {
        log.debug("PUT /solution/{} with body {}", id, solutionDTO);
        return solutionService.save(solutionDTO);
    }

    @DeleteMapping("/solution/{id}")
    public void Seletesolution(@PathVariable final UUID id) {
        log.debug("DELETE /solution/{}", id);
        solutionService.delete(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/solution/{id}/bug")
    public BugDTO addBug(@PathVariable final UUID id, @RequestBody final BugDTO bugDTO) {
        log.debug("POST /solution/{}/bug with body {}", id, bugDTO);
        return solutionService.addBug(id, bugDTO);
    }

    @DeleteMapping("/solution/{id}/bug/{bugId}")
    public BugDTO removeBug(@PathVariable final UUID id, @PathVariable final UUID bugId) {
        log.debug("DELETE /solution/{}/bug/{}", id, bugId);
        return solutionService.removeBug(id, bugId);
    }

    @GetMapping("/solution/{id}/bugs")
    public List<BugDTO> getBugs(@PathVariable final UUID id) {
        log.debug("GET /solution/{}/bugs", id);
        return solutionService.getBugs(id);
    }
}
