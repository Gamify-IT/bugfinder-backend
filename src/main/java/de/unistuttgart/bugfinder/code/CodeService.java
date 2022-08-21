package de.unistuttgart.bugfinder.code;

import de.unistuttgart.bugfinder.code.word.WordDTO;
import de.unistuttgart.bugfinder.code.word.WordMapper;
import de.unistuttgart.bugfinder.code.word.WordService;
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

  public List<CodeDTO> findAll() {
    log.debug("get all codes");
    return codeMapper.toDTO(codeRepository.findAll());
  }

  public Optional<CodeDTO> find(final UUID id) {
    log.debug("get code {}", id);
    return codeMapper.toDTO(codeRepository.findById(id));
  }

  public CodeDTO save(final CodeDTO codeDTO) {
    log.debug("create code {}", codeDTO);
    return codeMapper.toDTO(codeRepository.save(codeMapper.fromDTO(codeDTO)));
  }

  public void delete(final UUID id) {
    log.debug("delete code {}", id);
    codeRepository.deleteById(id);
  }

  public WordDTO addWord(final UUID id, final WordDTO word) {
    log.debug("add word {} to code {}", word, id);
    final Code code = codeRepository.findById(id).orElseThrow();
    code.addWord(wordMapper.fromDTO(word));
    codeRepository.save(code);
    return word;
  }

  public WordDTO removeWord(final UUID id, final UUID wordId) {
    log.debug("remove word {} from code {}", wordId, id);
    final Code code = codeRepository.findById(id).orElseThrow();
    final WordDTO word = wordService.find(wordId).orElseThrow();
    code.removeWord(wordMapper.fromDTO(word));
    codeRepository.save(code);
    return word;
  }

  public List<WordDTO> getWords(final UUID id) {
    log.debug("get words from code {}", id);
    final Code code = codeRepository.findById(id).orElseThrow();
    return wordMapper.toDTO(code.getWords());
  }
}
