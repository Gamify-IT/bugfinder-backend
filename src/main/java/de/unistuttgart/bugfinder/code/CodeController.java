package de.unistuttgart.bugfinder.code;

import de.unistuttgart.bugfinder.ExceptionHandlingController;
import de.unistuttgart.bugfinder.code.word.WordDTO;
import de.unistuttgart.bugfinder.solution.SolutionDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class CodeController extends ExceptionHandlingController {

  @Autowired
  private CodeService codeService;

  @GetMapping("/codes")
  public List<CodeDTO> getAll() {
    log.info("GET /codes");
    return codeService.findAll();
  }

  @GetMapping("/codes/{id}")
  public Optional<CodeDTO> get(@PathVariable final UUID id) {
    log.info("GET /codes/{}", id);
    return codeService.find(id);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/codes")
  public CodeDTO createCode(@RequestBody final CodeDTO codeDTO) {
    log.info("POST /codes with body {}", codeDTO);
    return codeService.save(codeDTO);
  }

  @PutMapping("/codes/{id}")
  public CodeDTO updateCode(@PathVariable final UUID id, @RequestBody final CodeDTO codeDTO) {
    log.info("PUT /codes/{} with body {}", id, codeDTO);
    return codeService.save(codeDTO);
  }

  @DeleteMapping("/codes/{id}")
  public void deleteCode(@PathVariable final UUID id) {
    log.info("DELETE /codes/{}", id);
    codeService.delete(id);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/codes/{id}/words")
  public WordDTO addWord(@PathVariable final UUID id, @RequestBody final WordDTO wordDTO) {
    log.info("POST /codes/{}/words with body {}", id, wordDTO);
    return codeService.addWord(id, wordDTO);
  }

  @DeleteMapping("/codes/{id}/words/{wordId}")
  public WordDTO removeWord(@PathVariable final UUID id, @PathVariable final UUID wordId) {
    log.info("DELETE /codes/{}/words/{}", id, wordId);
    return codeService.removeWord(id, wordId);
  }

  @GetMapping("/codes/{id}/words")
  public List<WordDTO> getWords(@PathVariable final UUID id) {
    log.info("GET /codes/{}/words", id);
    return codeService.getWords(id);
  }

  @GetMapping("/codes/{id}/solutions")
  public SolutionDTO getSolution(@PathVariable final UUID id) {
    log.info("GET /codes/{}/solutions", id);
    return codeService.getSolution(id);
  }
}
