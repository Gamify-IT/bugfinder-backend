package de.unistuttgart.bugfinder.configuration;

import de.unistuttgart.bugfinder.code.CodeDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConfigurationDTO {

    String id;
    List<CodeDTO> codes;
}
