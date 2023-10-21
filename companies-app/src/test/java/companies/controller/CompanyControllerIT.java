package companies.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import commons.model.IdResponse;
import companies.controller.model.request.CompanyRegisterRequest;
import companies.controller.model.response.CompanyDetailsResponse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CompanyControllerIT {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    void testCRUD() {

        // GET
        mockMvc.perform(get("/company"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"));
        mockMvc.perform(get("/company/count"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("0"));

        // POST
        CompanyRegisterRequest request = CompanyRegisterRequest.builder()
                .name("name")
                .url("url")
                .industry("industry")
                .country("country")
                .build();
        MvcResult mvcResult = mockMvc.perform(post("/company")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        IdResponse idResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), IdResponse.class);
        Assertions.assertNotNull(idResponse);
        Assertions.assertNotNull(idResponse.getId());

        // GET
        mockMvc.perform(get("/company/count"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("1"));

        CompanyDetailsResponse response = CompanyDetailsResponse.builder()
                .id(idResponse.getId())
                .name(request.getName())
                .url(request.getUrl())
                .industry(request.getIndustry())
                .country(request.getCountry())
                .build();
        mockMvc.perform(get("/company/{id}", idResponse.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
        mockMvc.perform(get("/company"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.singletonList(response))));

        // DELETE
        mockMvc.perform(delete("/company/" + idResponse.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(""));

        // GET
        mockMvc.perform(get("/company"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"));
        mockMvc.perform(get("/company/count"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("0"));
    }

}
