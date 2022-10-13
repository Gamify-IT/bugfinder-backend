package de.unistuttgart.bugfinder.configuration;

import de.unistuttgart.bugfinder.configuration.code.CodeMapper;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Mapper for {@code Configuration}.
 */
@Component
@FieldDefaults(level = AccessLevel.PUBLIC)
public class ConfigurationMapper {

  @Autowired
  private CodeMapper codeMapper;

  /**
   * Map a {@code Configuration} to a {@code ConfigurationDTO}.
   *
   * @param configuration the configuration to map
   * @return the mapped configuration
   */
  public ConfigurationDTO toDTO(final Configuration configuration) {
    return new ConfigurationDTO(configuration.getId(), codeMapper.toDTO(configuration.getCodes()));
  }

  /**
   * Map a list of {@code Configuration}s to a list of {@code ConfigurationDTO}s.
   *
   * @param configurations the configurations to map
   * @return the mapped configurations
   */
  public List<ConfigurationDTO> toDTO(final List<Configuration> configurations) {
    return configurations.parallelStream().map(this::toDTO).collect(Collectors.toList());
  }

  /**
   * Map an optional {@code Configuration} to an optional {@code ConfigurationDTO}.
   *
   * @param configuration the configuration to map
   * @return the mapped configuration
   */
  public Optional<ConfigurationDTO> toDTO(final Optional<Configuration> configuration) {
    return configuration.map(this::toDTO);
  }

  /**
   * Map a {@code ConfigurationDTO} to a {@code Configuration}.
   *
   * @param configurationDTO the configuration to map
   * @return the mapped configuration
   */
  public Configuration fromDTO(final ConfigurationDTO configurationDTO) {
    return new Configuration(configurationDTO.getId(), new HashSet<>(codeMapper.fromDTO(configurationDTO.getCodes())));
  }

  /**
   * Map a list of {@code ConfigurationDTO}s to a list of {@code Configuration}s.
   *
   * @param configurationDTOs the configurations to map
   * @return the mapped configurations
   */
  public List<Configuration> fromDTO(final List<ConfigurationDTO> configurationDTOs) {
    return configurationDTOs.parallelStream().map(this::fromDTO).collect(Collectors.toList());
  }

  /**
   * Map an optional {@code ConfigurationDTO} to an optional {@code Configuration}.
   *
   * @param configurationDTO the configuration to map
   * @return the mapped configuration
   */
  public Optional<Configuration> fromDTO(final Optional<ConfigurationDTO> configurationDTO) {
    return configurationDTO.map(this::fromDTO);
  }
}
