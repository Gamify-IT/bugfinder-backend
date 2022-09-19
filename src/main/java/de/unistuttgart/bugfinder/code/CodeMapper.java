package de.unistuttgart.bugfinder.code;

import de.unistuttgart.bugfinder.code.word.WordMapper;
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
    return codes.parallelStream().map(this::toDTO).collect(Collectors.toList());
  }

  public Optional<CodeDTO> toDTO(final Optional<Code> code) {
    return code.map(this::toDTO);
  }

  public Code fromDTO(final CodeDTO codeDTO) {
    return new Code(codeDTO.getId(), wordMapper.fromDTO(codeDTO.getWords()));
  }

  public List<Code> fromDTO(final Collection<CodeDTO> codeDTOs) {
    return codeDTOs.parallelStream().map(this::fromDTO).collect(Collectors.toList());
  }

  public Optional<Code> fromDTO(final Optional<CodeDTO> codeDTO) {
    return codeDTO.map(this::fromDTO);
  }
}
