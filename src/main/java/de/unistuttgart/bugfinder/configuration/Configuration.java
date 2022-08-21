package de.unistuttgart.bugfinder.configuration;

import de.unistuttgart.bugfinder.code.Code;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Configuration {

  @Id
  @GeneratedValue(generator = "uuid")
  UUID id;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  List<Code> codes;

  public Configuration(final List<Code> codes) {
    this.codes = codes;
  }

  public void addCode(final Code code) {
    this.codes.add(code);
  }

  public void removeCode(final Code code) {
    this.codes.remove(code);
  }
}