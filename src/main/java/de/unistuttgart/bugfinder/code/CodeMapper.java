package de.unistuttgart.bugfinder.code;

import de.unistuttgart.bugfinder.code.word.WordMapper;
import de.unistuttgart.bugfinder.util.UuidUtil;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PUBLIC)
public class CodeMapper {

  @Autowired
  private WordMapper wordMapper;

  public CodeDTO toDTO(final Code code) {
    return new CodeDTO(code.getId(), wordMapper.toDTO(code.getWords()));
  }

  public List<CodeDTO> toDTO(final Collection<Code> codes) {
    return codes.stream().map(this::toDTO).collect(Collectors.toList());
  }

  public Optional<CodeDTO> toDTO(final Optional<Code> code) {
    return code.isEmpty() ? Optional.empty() : Optional.of(toDTO(code.get()));
  }

  public Code fromDTO(final CodeDTO codeDTO) {
    return new Code(codeDTO.getId(), wordMapper.fromDTO(codeDTO.getWords()));
  }

  public List<Code> fromDTO(final Collection<CodeDTO> codeDTOs) {
    return codeDTOs.stream().map(this::fromDTO).collect(Collectors.toList());
  }

  public Optional<Code> fromDTO(final Optional<CodeDTO> codeDTO) {
    return codeDTO.isEmpty() ? Optional.empty() : Optional.of(fromDTO(codeDTO.get()));
  }
}
