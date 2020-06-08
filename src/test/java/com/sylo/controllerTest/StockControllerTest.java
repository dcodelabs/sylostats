package com.sylo.controllerTest;

import com.sylo.config.Stocks;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author dhanavenkateshgopal on 26/5/20.
 * @project sylostats
 */

public class StockControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Stocks stocks;

    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/greeting")).andDo(print()).andExpect(status().isOk())
            .andExpect(content().string(containsString("Hello, World")));
    }

    public void shouldReturnDefaultstocks() throws Exception {
        MultiValueMap<String, String> vars = new LinkedMultiValueMap<>();
        vars.add("stockExchange","bse");
        vars.add("stockId","q");
        vars.add("stockInterval","q");
        vars.add("fromDate","01-05-2020");
        vars.add("toDate","10-05-2020");

//        when(service.greet()).thenReturn("Hello, Mock");
    this.mockMvc
        .perform(get("/api/equity/history").params(vars))
        .andDo(print())
        .andExpect(status().isAccepted())
        .andExpect(
            content()
                .json(
                    "[\n"
                        + "    {\n"
                        + "        \"exchange\": \"bse\",\n"
                        + "        \"stockId\": \"q\",\n"
                        + "        \"interval\": \"q\",\n"
                        + "        \"year\": 2020,\n"
                        + "        \"month\": 4,\n"
                        + "        \"open\": 4482.0,\n"
                        + "        \"high\": 4615.0,\n"
                        + "        \"low\": 4482.0,\n"
                        + "        \"close\": 4571.14990234375\n"
                        + "    }\n"
                        + "]"));
    }
}
