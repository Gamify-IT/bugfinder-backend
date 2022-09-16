package de.unistuttgart.bugfinder.code;

import de.unistuttgart.bugfinder.code.word.WordDTO;
import de.unistuttgart.bugfinder.code.word.WordMapper;
import de.unistuttgart.bugfinder.code.word.WordService;
import de.unistuttgart.bugfinder.solution.SolutionDTO;
import de.unistuttgart.bugfinder.solution.SolutionMapper;
import de.unistuttgart.bugfinder.solution.SolutionRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CodeService {

  @Autowired
  private WordService wordService;

  @Autowired
  private CodeRepository codeRepository;

  @Autowired
  private CodeMapper codeMapper;

  @Autowired
  private WordMapper wordMapper;

  @Autowired
  private SolutionRepository solutionRepository;

  @Autowired
  private SolutionMapper solutionMapper;

  public List<CodeDTO> findAll() {
    log.info("get all codes");
    return codeMapper.toDTO(codeRepository.findAll());
  }

  public Optional<CodeDTO> find(final UUID id) {
    log.info("get code {}", id);
    return codeMapper.toDTO(codeRepository.findById(id));
  }

  public CodeDTO save(final CodeDTO codeDTO) {
    log.info("create code {}", codeDTO);
    return codeMapper.toDTO(codeRepository.save(codeMapper.fromDTO(codeDTO)));
  }

  public void delete(final UUID id) {
    log.info("delete code {}", id);
    codeRepository.deleteById(id);
  }

  public WordDTO addWord(final UUID id, final WordDTO word) {
    log.info("add word {} to code {}", word, id);
    final Code code = codeRepository.findById(id).orElseThrow();
    final WordDTO savedWord = wordService.save(word);
    code.addWord(wordMapper.fromDTO(savedWord));
    codeRepository.save(code);
    return savedWord;
  }

  public WordDTO removeWord(final UUID id, final UUID wordId) {
    log.info("remove word {} from code {}", wordId, id);
    final Code code = codeRepository.findById(id).orElseThrow();
    final WordDTO word = wordService.find(wordId).orElseThrow();
    code.removeWord(wordMapper.fromDTO(word));
    codeRepository.save(code);
    return word;
  }

  public List<WordDTO> getWords(final UUID id) {
    log.info("get words from code {}", id);
    final Code code = codeRepository.findById(id).orElseThrow();
    return wordMapper.toDTO(code.getWords());
  }

  public SolutionDTO getSolution(UUID id) {
    return solutionMapper.toDTO(solutionRepository.findByCodeId(id).orElseThrow());
  }
}
