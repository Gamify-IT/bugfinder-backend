package de.unistuttgart.bugfinder.configuration;

import de.unistuttgart.bugfinder.code.CodeDTO;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConfigurationDTO {

  @Nullable
  UUID id;

  List<CodeDTO> codes;
}
