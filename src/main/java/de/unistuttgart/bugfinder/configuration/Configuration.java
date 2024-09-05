package de.unistuttgart.bugfinder.configuration;

import de.unistuttgart.bugfinder.code.Code;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * A configuration contains a list of codes.
 */
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
  Set<Code> codes;
  /**
   * The volume level that is setted by the player.
   */
  Integer volumeLevel;

  public Configuration(UUID id, Set<Code> codes) {
    this.id = id;
    this.codes = codes;
  }

  public Configuration(final Set<Code> codes) {
    this.codes = codes;
  }

  public void addCode(final Code code) {
    this.codes.add(code);
  }

  public void removeCode(final Code code) {
    this.codes.remove(code);
  }
}
