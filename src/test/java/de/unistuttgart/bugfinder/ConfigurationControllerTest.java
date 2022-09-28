package de.unistuttgart.bugfinder;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.unistuttgart.bugfinder.code.Code;
import de.unistuttgart.bugfinder.code.CodeDTO;
import de.unistuttgart.bugfinder.code.word.Word;
import de.unistuttgart.bugfinder.code.word.WordDTO;
import de.unistuttgart.bugfinder.configuration.Configuration;
import de.unistuttgart.bugfinder.configuration.ConfigurationDTO;
import de.unistuttgart.bugfinder.configuration.ConfigurationMapper;
import de.unistuttgart.bugfinder.configuration.ConfigurationRepository;
import de.unistuttgart.bugfinder.configuration.vm.CodeVM;
import de.unistuttgart.bugfinder.configuration.vm.ConfigurationVM;
import de.unistuttgart.bugfinder.configuration.vm.WordVM;
import de.unistuttgart.bugfinder.solution.SolutionDTO;
import de.unistuttgart.bugfinder.solution.bug.ErrorType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ConfigurationControllerTest {

  private final String API_URL = "/configurations";

  @Autowired
  private MockMvc mvc;

  @Autowired
  private ConfigurationMapper configurationMapper;

  @Autowired
  private ConfigurationRepository configurationRepository;

  private ObjectMapper objectMapper;
  private Configuration initialConfig;
  private ConfigurationDTO initialConfigDTO;

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
    final Configuration configuration = new Configuration();
    configuration.setCodes(Set.of(code1));
    initialConfig = configurationRepository.save(configuration);
    initialConfigDTO = configurationMapper.toDTO(initialConfig);

    objectMapper = new ObjectMapper();
  }

  @BeforeAll
  @AfterEach
  public void deleteBasicData() {
        configurationRepository.deleteAll();
  }

  @Test
  void getConfigurations() throws Exception {
    final MvcResult result = mvc
            .perform(get(API_URL).contentType(MediaType.APPLICATION_JSON))
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
            .perform(get(API_URL + "/" + initialConfig.getId()).contentType(MediaType.APPLICATION_JSON))
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
            .perform(get(API_URL + "/" + UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON))
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
                    post(API_URL).content(objectMapper.writeValueAsString(newConfiguration)).contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isCreated());

    assertSame(2, configurationRepository.findAll().size());
  }

  @Test
  void deleteConfiguration() throws Exception {
    final MvcResult result = mvc
            .perform(delete(API_URL + "/" + initialConfig.getId()).contentType(MediaType.APPLICATION_JSON))
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
  void saveAndGetVM() throws Exception {
    final ConfigurationVM viewModel = new ConfigurationVM(
            List.of(
                    new CodeVM(
                            List.of(
                                    List.of(
                                            new WordVM("public", "pubvik", ErrorType.LEXICAL),
                                            new WordVM("static", null, null),
                                            new WordVM("void", null, null),
                                            new WordVM("main", null, null),
                                            new WordVM("{", null, null)
                                    ),
                                    List.of(
                                            new WordVM("<tab>", null, null),
                                            new WordVM("System.out.println", null, null),
                                            new WordVM("(\"Hello, world\")", null, null),
                                            new WordVM(";", " ", ErrorType.SYNTAX)
                                    ),
                                    List.of(new WordVM("}", null, null))
                            )
                    )
            )
    );

    final MvcResult result = mvc
            .perform(
                    post(API_URL + "/build")
                            .content(objectMapper.writeValueAsString(viewModel))
                            .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isCreated())
            .andReturn();

    ConfigurationDTO configuration = objectMapper.readValue(result.getResponse().getContentAsString(), ConfigurationDTO.class);
    List<String> expectedResult = List.of(
            "pubvik", " ", "static", " ", "void", " ", "main", " ", "{", "\n", "<tab>", " ", "System.out.println", " ", "(\"Hello, world\")", " ", " ", "\n", "}"
    );
    for (int i = 0; i < expectedResult.size(); i++) {
      assertEquals(expectedResult.get(i), configuration.getCodes().get(0).getWords().get(i).getWord(), "Word at index " + i + " is not equal. Expected: " + expectedResult.get(i) + ", actual: " + configuration.getCodes().get(0).getWords().get(i).getWord());
    }
    MvcResult solutionResponse = mvc
            .perform(get(API_URL + "/" + configuration.getId() + "/codes/" + configuration.getCodes().get(0).getId() + "/solutions").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

    SolutionDTO solution = objectMapper.readValue(solutionResponse.getResponse().getContentAsString(), SolutionDTO.class);
    assertEquals(2, solution.getBugs().size());

    MvcResult vmFromApiResult = mvc
            .perform(get(API_URL + "/vm/" + configuration.getId()).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

    ConfigurationVM vmFromApi = objectMapper.readValue(vmFromApiResult.getResponse().getContentAsString(), ConfigurationVM.class);
    assertEquals(viewModel, vmFromApi);
  }
}
