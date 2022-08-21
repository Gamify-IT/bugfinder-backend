package de.unistuttgart.bugfinder.solution.bug;

import de.unistuttgart.bugfinder.code.word.WordMapper;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PUBLIC)
public class BugMapper {

  @Autowired
  private WordMapper wordMapper;

  public BugDTO toDTO(final Bug bug) {
    return new BugDTO(
      bug.getId().toString(),
      wordMapper.toDTO(bug.getWord()),
      bug.getErrorType(),
      bug.getCorrectValue()
    );
  }

  public List<BugDTO> toDTO(final List<Bug> bugs) {
    return bugs.stream().map(this::toDTO).collect(Collectors.toList());
  }

  public Optional<BugDTO> toDTO(final Optional<Bug> bug) {
    return bug.isEmpty() ? Optional.empty() : Optional.of(toDTO(bug.get()));
  }

  public Bug fromDTO(final BugDTO bugDTO) {
    return new Bug(
      UUID.fromString(bugDTO.getId()),
      wordMapper.fromDTO(bugDTO.getWord()),
      bugDTO.getErrorType(),
      bugDTO.getCorrectValue()
    );
  }

  public List<Bug> fromDTO(final List<BugDTO> bugDTOs) {
    return bugDTOs.stream().map(this::fromDTO).collect(Collectors.toList());
  }

  public Optional<Bug> fromDTO(final Optional<BugDTO> bugDTO) {
    return bugDTO.isEmpty() ? Optional.empty() : Optional.of(fromDTO(bugDTO.get()));
  }
}
