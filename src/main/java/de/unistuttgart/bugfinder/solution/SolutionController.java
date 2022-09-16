package de.unistuttgart.bugfinder.solution;

import de.unistuttgart.bugfinder.ExceptionHandlingController;
import de.unistuttgart.bugfinder.solution.bug.BugDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class SolutionController extends ExceptionHandlingController {

  @Autowired
  private SolutionService solutionService;

  @GetMapping("/solutions")
  public List<SolutionDTO> getAll() {
    log.info("GET /solutions");
    return solutionService.findAll();
  }

  @GetMapping("/solutions/{id}")
  public Optional<SolutionDTO> get(@PathVariable final UUID id) {
    log.info("GET /solutions/{}", id);
    return solutionService.find(id);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/solutions")
  public SolutionDTO createSolution(@RequestBody final SolutionDTO solutionDTO) {
    log.info("POST /solutions with body {}", solutionDTO);
    return solutionService.save(solutionDTO);
  }

  @PutMapping("/solutions/{id}")
  public SolutionDTO updateSolution(@PathVariable final UUID id, @RequestBody final SolutionDTO solutionDTO) {
    log.info("PUT /solutions/{} with body {}", id, solutionDTO);
    return solutionService.save(solutionDTO);
  }

  @DeleteMapping("/solutions/{id}")
  public void deleteSolution(@PathVariable final UUID id) {
    log.info("DELETE /solutions/{}", id);
    solutionService.delete(id);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/solutions/{id}/bug")
  public BugDTO addBug(@PathVariable final UUID id, @RequestBody final BugDTO bugDTO) {
    log.info("POST /solutions/{}/bug with body {}", id, bugDTO);
    return solutionService.addBug(id, bugDTO);
  }

  @DeleteMapping("/solutions/{id}/bug/{bugId}")
  public BugDTO removeBug(@PathVariable final UUID id, @PathVariable final UUID bugId) {
    log.info("DELETE /solutions/{}/bug/{}", id, bugId);
    return solutionService.removeBug(id, bugId);
  }

  @GetMapping("/solutions/{id}/bugs")
  public List<BugDTO> getBugs(@PathVariable final UUID id) {
    log.info("GET /solutions/{}/bugs", id);
    return solutionService.getBugs(id);
  }
}
