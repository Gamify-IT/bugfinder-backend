package de.unistuttgart.bugfinder.code.word;

import de.unistuttgart.bugfinder.util.UuidUtil;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class WordMapper {

    public WordDTO toDTO(final Word word) {
        return new WordDTO(word.getId().toString(), word.getWord());
    }

    public List<WordDTO> toDTO(final List<Word> words) {
        return words.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Optional<WordDTO> toDTO(final Optional<Word> word) {
        return word.isEmpty() ? Optional.empty() : Optional.of(toDTO(word.get()));
    }

    public Word fromDTO(final WordDTO wordDTO) {
        return new Word(UuidUtil.ofNullableFallbackNull(wordDTO.getId()), wordDTO.getWord());
    }

    public List<Word> fromDTO(final List<WordDTO> wordDTOs) {
        return wordDTOs.stream().map(this::fromDTO).collect(Collectors.toList());
    }

    public Optional<Word> fromDTO(final Optional<WordDTO> wordDTO) {
        return wordDTO.isEmpty() ? Optional.empty() : Optional.of(fromDTO(wordDTO.get()));
    }
}
