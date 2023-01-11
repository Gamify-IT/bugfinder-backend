package de.unistuttgart.bugfinder.configuration;

import de.unistuttgart.bugfinder.code.CodeMapper;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
    return new ConfigurationDTO(configuration.getId(), codeMapper.toDTO(configuration.getCodes()));
  }

  public List<ConfigurationDTO> toDTO(final List<Configuration> codes) {
    return codes.parallelStream().map(this::toDTO).toList();
  }

  public Optional<ConfigurationDTO> toDTO(final Optional<Configuration> configuration) {
    return configuration.map(this::toDTO);
  }

  public Configuration fromDTO(final ConfigurationDTO configurationDTO) {
    return new Configuration(configurationDTO.getId(), new HashSet<>(codeMapper.fromDTO(configurationDTO.getCodes())));
  }

  public List<Configuration> fromDTO(final List<ConfigurationDTO> codeDTOs) {
    return codeDTOs.parallelStream().map(this::fromDTO).toList();
  }

  public Optional<Configuration> fromDTO(final Optional<ConfigurationDTO> configurationDTO) {
    return configurationDTO.map(this::fromDTO);
  }
}
