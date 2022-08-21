package de.unistuttgart.bugfinder.code;

import de.unistuttgart.bugfinder.ExceptionHandlingController;
import de.unistuttgart.bugfinder.code.word.WordDTO;
import java.util.List;
import java.util.Optional;
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

  @GetMapping("/code/{id}")
  public Optional<CodeDTO> get(@PathVariable final String id) {
    log.info("GET /code/{}", id);
    return codeService.find(id);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/code")
  public CodeDTO createCode(@RequestBody final CodeDTO codeDTO) {
    log.info("POST /code with body {}", codeDTO);
    return codeService.save(codeDTO);
  }

  @PutMapping("/code/{id}")
  public CodeDTO updateCode(@PathVariable final String id, @RequestBody final CodeDTO codeDTO) {
    log.info("PUT /code/{} with body {}", id, codeDTO);
    return codeService.save(codeDTO);
  }

  @DeleteMapping("/code/{id}")
  public void deleteCode(@PathVariable final String id) {
    log.info("DELETE /code/{}", id);
    codeService.delete(id);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/code/{id}/word")
  public WordDTO addWord(@PathVariable final String id, @RequestBody final WordDTO wordDTO) {
    log.info("POST /code/{}/word with body {}", id, wordDTO);
    return codeService.addWord(id, wordDTO);
  }

  @DeleteMapping("/code/{id}/word/{wordId}")
  public WordDTO removeWord(@PathVariable final String id, @PathVariable final String wordId) {
    log.info("DELETE /code/{}/word/{}", id, wordId);
    return codeService.removeWord(id, wordId);
  }

  @GetMapping("/code/{id}/words")
  public List<WordDTO> getWords(@PathVariable final String id) {
    log.info("GET /code/{}/words", id);
    return codeService.getWords(id);
  }
}
