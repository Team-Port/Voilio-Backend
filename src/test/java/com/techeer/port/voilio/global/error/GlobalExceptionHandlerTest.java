package com.techeer.port.voilio.global.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techeer.port.voilio.global.error.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import static com.techeer.port.voilio.global.error.ErrorCode.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(
    classes = {GlobalExceptionHandlerTest.TestController.class, GlobalExceptionHandler.class})
@WebMvcTest(controllers = GlobalExceptionHandlerTest.TestController.class)
class GlobalExceptionHandlerTest {
  private MockMvc mockMvc;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp(WebApplicationContext webApplicationContext) {
    mockMvc =
        MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .build();

    objectMapper = new ObjectMapper();
  }

  @DisplayName("잘못된 input 예외 handler test")
  @Test
  void handleMethodArgumentNotValidException() throws Exception {
    MockRequest mockRequest = new MockRequest(0);

    mockMvc
        .perform(
            post("/exception")
                .content(objectMapper.writeValueAsString(mockRequest))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(409))
        .andExpect(jsonPath("$.businessCode").value(INPUT_INVALID_VALUE.getCode()))
        .andDo(print());
  }

  @Test
  @DisplayName("business 예외 handler test")
  void handleBusinessException() throws Exception {
    mockMvc
        .perform(get("/exception/1"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.businessCode").value(USER_NOT_FOUND_ERROR.getCode()))
        .andDo(print());
  }

  private static class MockRequest {
    @Positive private int ping;

    public MockRequest() {}

    public MockRequest(int ping) {
      this.ping = ping;
    }

    public int getPing() {
      return ping;
    }
  }

  @RestController
  @RequestMapping("/exception")
  static class TestController {
    @GetMapping("/{id}")
    public String executeBusinessException(@PathVariable Long id) {
      throw new BusinessException(USER_NOT_FOUND_ERROR);
    }

    @PostMapping
    public String executeException(@RequestBody @Valid MockRequest mockRequest) {
      throw new RuntimeException();
    }
  }
}
