package de.unistuttgart.bugfinder.solution;

import de.unistuttgart.bugfinder.ExceptionHandlingController;
import de.unistuttgart.bugfinder.solution.bug.BugDTO;
import java.util.List;
import java.util.Optional;
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

  @GetMapping("/solution/{id}")
  public Optional<SolutionDTO> get(@PathVariable final String id) {
    log.info("GET /solution/{}", id);
    return solutionService.find(id);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/solution")
  public SolutionDTO createSolution(@RequestBody final SolutionDTO solutionDTO) {
    log.info("POST /solution with body {}", solutionDTO);
    return solutionService.save(solutionDTO);
  }

  @PutMapping("/solution/{id}")
  public SolutionDTO updateSolution(@PathVariable final String id, @RequestBody final SolutionDTO solutionDTO) {
    log.info("PUT /solution/{} with body {}", id, solutionDTO);
    return solutionService.save(solutionDTO);
  }

  @DeleteMapping("/solution/{id}")
  public void deleteSolution(@PathVariable final String id) {
    log.info("DELETE /solution/{}", id);
    solutionService.delete(id);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/solution/{id}/bug")
  public BugDTO addBug(@PathVariable final String id, @RequestBody final BugDTO bugDTO) {
    log.info("POST /solution/{}/bug with body {}", id, bugDTO);
    return solutionService.addBug(id, bugDTO);
  }

  @DeleteMapping("/solution/{id}/bug/{bugId}")
  public BugDTO removeBug(@PathVariable final String id, @PathVariable final String bugId) {
    log.info("DELETE /solution/{}/bug/{}", id, bugId);
    return solutionService.removeBug(id, bugId);
  }

  @GetMapping("/solution/{id}/bugs")
  public List<BugDTO> getBugs(@PathVariable final String id) {
    log.info("GET /solution/{}/bugs", id);
    return solutionService.getBugs(id);
  }
}
