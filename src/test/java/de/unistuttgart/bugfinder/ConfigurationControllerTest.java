package de.unistuttgart.bugfinder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.unistuttgart.bugfinder.code.Code;
import de.unistuttgart.bugfinder.code.CodeDTO;
import de.unistuttgart.bugfinder.code.word.Word;
import de.unistuttgart.bugfinder.code.word.WordDTO;
import de.unistuttgart.bugfinder.configuration.Configuration;
import de.unistuttgart.bugfinder.configuration.ConfigurationDTO;
import de.unistuttgart.bugfinder.configuration.ConfigurationMapper;
import de.unistuttgart.bugfinder.configuration.ConfigurationRepository;
import de.unistuttgart.bugfinder.solution.Solution;
import de.unistuttgart.bugfinder.solution.SolutionRepository;
import de.unistuttgart.bugfinder.solution.bug.Bug;
import de.unistuttgart.bugfinder.solution.bug.ErrorType;
import de.unistuttgart.gamifyit.authentificationvalidator.JWTValidatorService;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.servlet.http.Cookie;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ConfigurationControllerTest {

  private final String API_URL = "/configurations";

  @MockBean
  JWTValidatorService jwtValidatorService;

  final Cookie cookie = new Cookie("access_token", "testToken");

  @Autowired
  private MockMvc mvc;

  @Autowired
  private ConfigurationMapper configurationMapper;

  @Autowired
  private ConfigurationRepository configurationRepository;

  @Autowired
  private SolutionRepository solutionRepository;

  private ObjectMapper objectMapper;
  private Configuration initialConfig;
  private ConfigurationDTO initialConfigDTO;

  private Solution initialSolution;

  @BeforeEach
  public void createBasicData() {
    final Code code1 = new Code();
    code1.setWords(
      Arrays.asList(
        new Word("public"),
        new Word(" "),
        new Word("void"),
        new Word(" "),
        new Word("sayHello()"),
        new Word(" "),
        new Word("{"),
        new Word("\n"),
        new Word("}")
      )
    );
    final Bug bug = new Bug(code1.getWords().get(1), ErrorType.LEXICAL, "Test");
    final Solution solution = new Solution(null, Set.of(bug), code1);

    final Configuration configuration = new Configuration();
    configuration.setCodes(Set.of(code1));
    initialConfig = configurationRepository.save(configuration);
    initialConfigDTO = configurationMapper.toDTO(initialConfig);

    initialSolution = solutionRepository.save(solution);
    objectMapper = new ObjectMapper();

    doNothing().when(jwtValidatorService).validateTokenOrThrow("testToken");
    when(jwtValidatorService.extractUserId("testToken")).thenReturn("testUser");
  }

  @BeforeAll
  @AfterEach
  public void deleteBasicData() {
    solutionRepository.deleteAll();
    configurationRepository.deleteAll();
  }

  @Test
  void getConfigurations() throws Exception {
    final MvcResult result = mvc
      .perform(get(API_URL).contentType(MediaType.APPLICATION_JSON).cookie(cookie))
      .andExpect(status().isOk())
      .andReturn();

    final List<ConfigurationDTO> configurations = Arrays.asList(
      objectMapper.readValue(result.getResponse().getContentAsString(), ConfigurationDTO[].class)
    );

    assertSame(1, configurations.size());
    assertEquals(initialConfigDTO, configurations.get(0));
  }

  @Test
  void getConfiguration() throws Exception {
    final MvcResult result = mvc
      .perform(get(API_URL + "/" + initialConfig.getId()).cookie(cookie).contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andReturn();

    final ConfigurationDTO configuration = objectMapper.readValue(
      result.getResponse().getContentAsString(),
      ConfigurationDTO.class
    );

    assertEquals(initialConfigDTO, configuration);
  }

  @Test
  void getConfiguration_DoesNotExist_ThrowsNotFound() throws Exception {
    mvc
      .perform(get(API_URL + "/" + UUID.randomUUID()).cookie(cookie).contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isNotFound());
  }

  @Test
  void createConfiguration() throws Exception {
    CodeDTO newCode = new CodeDTO();
    newCode.setWords(
      Arrays.asList(
        new WordDTO("public"),
        new WordDTO(" "),
        new WordDTO("void"),
        new WordDTO(" "),
        new WordDTO("sayGoodbye()"),
        new WordDTO(" "),
        new WordDTO("{"),
        new WordDTO("\n"),
        new WordDTO("}")
      )
    );
    final ConfigurationDTO newConfiguration = new ConfigurationDTO();
    newConfiguration.setCodes(List.of(newCode));
    mvc
      .perform(
        post(API_URL)
          .content(objectMapper.writeValueAsString(newConfiguration))
          .cookie(cookie)
          .contentType(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isCreated());

    assertSame(2, configurationRepository.findAll().size());
  }

  @Test
  void deleteConfiguration() throws Exception {
    final MvcResult result = mvc
      .perform(delete(API_URL + "/" + initialConfig.getId()).cookie(cookie).contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andReturn();

    final ConfigurationDTO configuration = objectMapper.readValue(
      result.getResponse().getContentAsString(),
      ConfigurationDTO.class
    );

    assertEquals(initialConfigDTO, configuration);
    assertTrue(configurationRepository.findById(initialConfig.getId()).isEmpty());
  }

  @Test
  void cloneConfiguration() throws Exception {
    final MvcResult result = mvc
      .perform(
        post(API_URL + "/" + initialConfig.getId() + "/clone").cookie(cookie).contentType(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andReturn();

    final UUID cloneId = objectMapper.readValue(result.getResponse().getContentAsString(), UUID.class);

    Configuration cloneConfiguration = configurationRepository.findById(cloneId).get();

    assertEquals(initialConfig.getCodes().size(), cloneConfiguration.getCodes().size());

    initialConfig
      .getCodes()
      .forEach(code -> {
        assertTrue(
          cloneConfiguration.getCodes().stream().anyMatch(code1 -> code1.getWords().size() == code.getWords().size())
        );
        assertTrue(cloneConfiguration.getCodes().stream().noneMatch(code1 -> code1.getId() == code.getId()));
      });
  }
}
