package de.unistuttgart.bugfinder.configuration.code.word;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service for {@code Word}.
 */
@Service
@Slf4j
public class WordService {

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private WordMapper wordMapper;

    /**
     * Get all words.
     *
     * @return all words
     */
    public List<WordDTO> findAll() {
        log.debug("get all words");
        return wordMapper.toDTO(wordRepository.findAll());
    }

    /**
     * Get a word by its id.
     *
     * @param id the id of the word
     * @return the word
     */
    public Optional<WordDTO> find(final UUID id) {
        log.debug("get word {}", id);
        return wordMapper.toDTO(wordRepository.findById(id));
    }

    /**
     * Saves a word.
     * <p>
     * If the word already exists, it will be updated.
     * </p>
     *
     * @param wordDTO the word to save
     * @return the saved word
     */
    public WordDTO save(final WordDTO wordDTO) {
        log.debug("create word {}", wordDTO);
        return wordMapper.toDTO(wordRepository.save(wordMapper.fromDTO(wordDTO)));
    }

    /**
     * Deletes a word by its id.
     *
     * @param id the id of the word
     */
    public void delete(final UUID id) {
        log.debug("delete word {}", id);
        wordRepository.deleteById(id);
    }
}
