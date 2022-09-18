package de.unistuttgart.bugfinder.solution.bug;

/**
 * Defines the possible error types an error can have.
 */
public enum ErrorType {
  UNDEFINED,
  /**
   * Syntactically allowed, but makes no sense in this context.<br>
   * For example: {@code int i = "Hi";}
   */
  STATIC_SEMANTIC,
  /**
   * Cannot be compiled under any circumstance.<br>
   * For example: {@code if ;<>}
   */
  SYNTAX,
  /**
   * (Undetected) runtime errors after a successful compilation.<br>
   * For example: {@code String s; s.length();}
   */
  DYNAMIC_SEMANTIC,
  /**
   * Strings that can <strong>never</strong> occur outside of a string literal.<br>
   * For example: {@code class 4LeggedAnimal} in Java
   */
  LEXICAL,
  /**
   * Your logic is wrong. Everything compiles and runs, but it doesn't produce the result you wanted.<br>
   * For example: {@code /* increment i */ i--; }
   */
  PRAGMATIC,
}
