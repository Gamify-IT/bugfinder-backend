package de.unistuttgart.bugfinder;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.unistuttgart.bugfinder.code.Code;
import de.unistuttgart.bugfinder.code.CodeDTO;
import de.unistuttgart.bugfinder.code.CodeMapper;
import de.unistuttgart.bugfinder.code.word.Word;
import de.unistuttgart.bugfinder.code.word.WordDTO;
import de.unistuttgart.bugfinder.configuration.Configuration;
import de.unistuttgart.bugfinder.configuration.ConfigurationDTO;
import de.unistuttgart.bugfinder.configuration.ConfigurationMapper;
import de.unistuttgart.bugfinder.configuration.ConfigurationRepository;
import de.unistuttgart.bugfinder.solution.Solution;
import de.unistuttgart.bugfinder.solution.SolutionDTO;
import de.unistuttgart.bugfinder.solution.SolutionMapper;
import de.unistuttgart.bugfinder.solution.SolutionRepository;
import de.unistuttgart.bugfinder.solution.bug.Bug;
import de.unistuttgart.bugfinder.solution.bug.ErrorType;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.apiguardian.api.API;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CodeControllerTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private ConfigurationMapper configurationMapper;

  @Autowired
  private SolutionMapper solutionMapper;

  @Autowired
  private CodeMapper codeMapper;

  @Autowired
  private ConfigurationRepository configurationRepository;

  @Autowired
  private SolutionRepository solutionRepository;

  private String API_URL;
  private ObjectMapper objectMapper;
  private Configuration initialConfig;
  private ConfigurationDTO initialConfigDTO;
  private Solution initialSolution;
  private SolutionDTO initialSolutionDTO;
  private Code initialCode;
  private CodeDTO initialCodeDTO;

  @BeforeEach
  public void createBasicData() {
    final Code code1 = new Code();
    final Word bugWord = new Word("prublic");
    code1.setWords(
      Arrays.asList(
        bugWord,
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
    initialCode = initialConfig.getCodes().stream().findFirst().get();
    initialCodeDTO = codeMapper.toDTO(initialCode);

    final Solution solution = new Solution();
    final Bug bug1 = new Bug();
    bug1.setWord(bugWord);
    bug1.setErrorType(ErrorType.LEXICAL);
    solution.setBugs(Set.of(bug1));
    solution.setCode(initialCode);
    initialSolution = solutionRepository.save(solution);
    initialSolutionDTO = solutionMapper.toDTO(initialSolution);

    objectMapper = new ObjectMapper();
    API_URL = String.format("/configurations/%s/codes", initialConfig.getId());
  }

  @BeforeAll
  @AfterEach
  public void deleteBasicData() {
    solutionRepository.deleteAll();
    configurationRepository.deleteAll();
  }

  @Test
  void getCodesOfConfiguration() throws Exception {
    final MvcResult result = mvc
      .perform(get(API_URL).contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andReturn();

    final List<CodeDTO> codes = Arrays.asList(
      objectMapper.readValue(result.getResponse().getContentAsString(), CodeDTO[].class)
    );

    assertSame(1, codes.size());
    assertEquals(initialCodeDTO, codes.get(0));
  }

  @Test
  void getSolutionOfCode() throws Exception {
    final MvcResult result = mvc
      .perform(get(API_URL + "/" + initialCode.getId() + "/solutions").contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andReturn();

    final SolutionDTO solution = objectMapper.readValue(result.getResponse().getContentAsString(), SolutionDTO.class);

    assertEquals(initialSolutionDTO, solution);
  }
}
