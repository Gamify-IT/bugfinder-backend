package de.unistuttgart.bugfinder.configuration;

import de.unistuttgart.bugfinder.code.CodeMapper;
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
public class ConfigurationMapper {

  @Autowired
  private CodeMapper codeMapper;

  public ConfigurationDTO toDTO(final Configuration configuration) {
    return new ConfigurationDTO(configuration.getId().toString(), codeMapper.toDTO(configuration.getCodes()));
  }

  public List<ConfigurationDTO> toDTO(final List<Configuration> codes) {
    return codes.stream().map(this::toDTO).collect(Collectors.toList());
  }

  public Optional<ConfigurationDTO> toDTO(final Optional<Configuration> configuration) {
    return configuration.isEmpty() ? Optional.empty() : Optional.of(toDTO(configuration.get()));
  }

  public Configuration fromDTO(final ConfigurationDTO configurationDTO) {
    return new Configuration(
      UUID.fromString(configurationDTO.getId()),
      codeMapper.fromDTO(configurationDTO.getCodes())
    );
  }

  public List<Configuration> fromDTO(final List<ConfigurationDTO> codeDTOs) {
    return codeDTOs.stream().map(this::fromDTO).collect(Collectors.toList());
  }

  public Optional<Configuration> fromDTO(final Optional<ConfigurationDTO> configurationDTO) {
    return configurationDTO.isEmpty() ? Optional.empty() : Optional.of(fromDTO(configurationDTO.get()));
  }
}
