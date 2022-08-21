package de.unistuttgart.bugfinder.solution;

import de.unistuttgart.bugfinder.solution.bug.Bug;
import java.util.List;
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
public class Solution {

  @Id
  @GeneratedValue(generator = "uuid")
  UUID id;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  List<Bug> bugs;

  public Solution(final List<Bug> bugs) {
    this.bugs = bugs;
  }

  public void addBug(final Bug bug) {
    this.bugs.add(bug);
  }

  public void removeBug(final Bug bug) {
    this.bugs.remove(bug);
  }
}
