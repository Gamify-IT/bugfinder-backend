package de.unistuttgart.bugfinder.code.word;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Mapper for {@code Word}.
 */
@Component
public class WordMapper {

    /**
     * Map a {@code Word} to a {@code WordDTO}.
     *
     * @param word the word to map
     * @return the mapped word
     */
    public WordDTO toDTO(final Word word) {
        return new WordDTO(word.getId(), word.getWord());
    }

    /**
     * Map a list of {@code Word}s to a list of {@code WordDTO}s.
     *
     * @param words the words to map
     * @return the mapped words
     */
    public List<WordDTO> toDTO(final List<Word> words) {
        return words.parallelStream().map(this::toDTO).collect(Collectors.toList());
    }

    /**
     * Map an optional {@code Word} to an optional {@code WordDTO}.
     *
     * @param word the word to map
     * @return the mapped word
     */
    public Optional<WordDTO> toDTO(final Optional<Word> word) {
        return word.map(this::toDTO);
    }

    /**
     * Map a {@code WordDTO} to a {@code Word}.
     *
     * @param wordDTO the word to map
     * @return the mapped word
     */
    public Word fromDTO(final WordDTO wordDTO) {
        return new Word(wordDTO.getId(), wordDTO.getWord());
    }

    /**
     * Map a list of {@code WordDTO}s to a list of {@code Word}s.
     *
     * @param wordDTOs the words to map
     * @return the mapped words
     */
    public List<Word> fromDTO(final List<WordDTO> wordDTOs) {
        return wordDTOs.parallelStream().map(this::fromDTO).collect(Collectors.toList());
    }

    /**
     * Map an optional {@code WordDTO} to an optional {@code Word}.
     *
     * @param wordDTO the word to map
     * @return the mapped word
     */
    public Optional<Word> fromDTO(final Optional<WordDTO> wordDTO) {
        return wordDTO.map(this::fromDTO);
    }
}
