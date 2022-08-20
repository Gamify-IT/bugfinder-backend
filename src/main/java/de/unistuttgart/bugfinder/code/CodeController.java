package de.unistuttgart.bugfinder.code;

import de.unistuttgart.bugfinder.code.word.WordDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@Slf4j
public class CodeController {

    @Autowired
    private CodeService codeService;

    @GetMapping("/codes")
    public List<CodeDTO> getAll() {
        log.debug("GET /codes");
        return codeService.findAll();
    }

    @GetMapping("/code/{id}")
    public Optional<CodeDTO> get(@PathVariable final UUID id) {
        log.debug("GET /code/{}", id);
        return codeService.find(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/code")
    public CodeDTO createCode(@RequestBody final CodeDTO codeDTO) {
        log.debug("POST /code with body {}", codeDTO);
        return codeService.save(codeDTO);
    }

    @PutMapping("/code/{id}")
    public CodeDTO updateCode(@PathVariable final UUID id, @RequestBody final CodeDTO codeDTO) {
        log.debug("PUT /code/{} with body {}", id, codeDTO);
        return codeService.save(codeDTO);
    }

    @DeleteMapping("/code/{id}")
    public void deleteCode(@PathVariable final UUID id) {
        log.debug("DELETE /code/{}", id);
        codeService.delete(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/code/{id}/word")
    public WordDTO addWord(@PathVariable final UUID id, @RequestBody final WordDTO wordDTO) {
        log.debug("POST /code/{}/word with body {}", id, wordDTO);
        return codeService.addWord(id, wordDTO);
    }

    @DeleteMapping("/code/{id}/word/{wordId}")
    public WordDTO removeWord(@PathVariable final UUID id, @PathVariable final UUID wordId) {
        log.debug("DELETE /code/{}/word/{}", id, wordId);
        return codeService.removeWord(id, wordId);
    }

    @GetMapping("/code/{id}/words")
    public List<WordDTO> getWords(@PathVariable final UUID id) {
        log.debug("GET /code/{}/words", id);
        return codeService.getWords(id);
    }
}
