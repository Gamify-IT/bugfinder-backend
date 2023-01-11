package de.unistuttgart.bugfinder.code.word;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class WordMapper {

  public WordDTO toDTO(final Word word) {
    return new WordDTO(word.getId(), word.getWord());
  }

  public List<WordDTO> toDTO(final List<Word> words) {
    return words.parallelStream().map(this::toDTO).toList();
  }

  public Optional<WordDTO> toDTO(final Optional<Word> word) {
    return word.map(this::toDTO);
  }

  public Word fromDTO(final WordDTO wordDTO) {
    return new Word(wordDTO.getId(), wordDTO.getWord());
  }

  public List<Word> fromDTO(final List<WordDTO> wordDTOs) {
    return wordDTOs.parallelStream().map(this::fromDTO).toList();
  }

  public Optional<Word> fromDTO(final Optional<WordDTO> wordDTO) {
    return wordDTO.map(this::fromDTO);
  }
}
