package de.unistuttgart.bugfinder.code.word;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WordService {

  @Autowired
  private WordRepository wordRepository;

  @Autowired
  private WordMapper wordMapper;

  public List<WordDTO> findAll() {
    log.debug("get all words");
    return wordMapper.toDTO(wordRepository.findAll());
  }

  public Optional<WordDTO> find(final UUID id) {
    log.debug("get word {}", id);
    return wordMapper.toDTO(wordRepository.findById(id));
  }

  public WordDTO save(final WordDTO wordDTO) {
    log.debug("create word {}", wordDTO);
    return wordMapper.toDTO(wordRepository.save(wordMapper.fromDTO(wordDTO)));
  }

  public void delete(final UUID id) {
    log.debug("delete word {}", id);
    wordRepository.deleteById(id);
  }
}
