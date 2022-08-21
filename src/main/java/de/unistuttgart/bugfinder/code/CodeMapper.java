package de.unistuttgart.bugfinder.code;

import de.unistuttgart.bugfinder.code.word.WordMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@FieldDefaults(level = AccessLevel.PUBLIC)
public class CodeMapper {

    @Autowired
    private WordMapper wordMapper;

    public CodeDTO toDTO(final Code code) {
        return new CodeDTO(code.getId().toString(), wordMapper.toDTO(code.getWords()));
    }

    public List<CodeDTO> toDTO(final List<Code> codes) {
        return codes.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Optional<CodeDTO> toDTO(final Optional<Code> code) {
        return code.isEmpty() ? Optional.empty() : Optional.of(toDTO(code.get()));
    }

    public Code fromDTO(final CodeDTO codeDTO) {
        return new Code(UUID.fromString(codeDTO.getId()), wordMapper.fromDTO(codeDTO.getWords()));
    }

    public List<Code> fromDTO(final List<CodeDTO> codeDTOs) {
        return codeDTOs.stream().map(this::fromDTO).collect(Collectors.toList());
    }

    public Optional<Code> fromDTO(final Optional<CodeDTO> codeDTO) {
        return codeDTO.isEmpty() ? Optional.empty() : Optional.of(fromDTO(codeDTO.get()));
    }
}
