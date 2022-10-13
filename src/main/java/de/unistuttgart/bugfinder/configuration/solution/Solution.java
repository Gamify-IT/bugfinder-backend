package de.unistuttgart.bugfinder.configuration.solution;

import de.unistuttgart.bugfinder.configuration.code.Code;
import de.unistuttgart.bugfinder.configuration.solution.bug.Bug;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * Contains a list of bugs which represents the solution.
 * <p>
 * A solution is linked to a specific code.
 * </p>
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Solution {

  @Id
  @GeneratedValue(generator = "uuid")
  UUID id;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  Set<Bug> bugs;

  @OneToOne
  Code code;

  public Solution(final Set<Bug> bugs) {
    this.bugs = bugs;
  }
}
