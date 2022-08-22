package de.unistuttgart.bugfinder.solution.bug;

import de.unistuttgart.bugfinder.code.word.WordMapper;
import de.unistuttgart.bugfinder.code.word.WordRepository;
import de.unistuttgart.bugfinder.util.UuidUtil;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@FieldDefaults(level = AccessLevel.PUBLIC)
public class BugMapper {

    @Autowired
    private WordMapper wordMapper;

    @Autowired
    private WordRepository wordRepository;

    public BugDTO toDTO(final Bug bug) {
        return new BugDTO(
                bug.getId().toString(),
                bug.getWord().getId().toString(),
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
                UuidUtil.ofNullableFallbackNull(bugDTO.getId()),
                wordRepository.findById(UuidUtil.ofNullableFallbackNull(bugDTO.getWordId())).orElseThrow(),
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
