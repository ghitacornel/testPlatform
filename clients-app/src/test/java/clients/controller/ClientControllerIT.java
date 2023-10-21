package clients.controller;

import clients.controller.model.request.ClientRegisterRequest;
import clients.controller.model.response.ClientDetailsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import commons.model.IdResponse;
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
public class ClientControllerIT {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    void testCRUD() {

        // GET
        mockMvc.perform(get("/client"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"));
        mockMvc.perform(get("/client/count"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("0"));

        // POST
        ClientRegisterRequest clientRegisterRequest = ClientRegisterRequest.builder()
                .name("name")
                .country("country")
                .cardType("cardType")
                .build();
        MvcResult mvcResult = mockMvc.perform(post("/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientRegisterRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        IdResponse idResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), IdResponse.class);
        Assertions.assertNotNull(idResponse);
        Assertions.assertNotNull(idResponse.getId());

        // GET
        mockMvc.perform(get("/client/count"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("1"));

        ClientDetailsResponse clientDetailsResponse = ClientDetailsResponse.builder()
                .id(idResponse.getId())
                .cardType(clientRegisterRequest.getCardType())
                .country(clientRegisterRequest.getCountry())
                .name(clientRegisterRequest.getName())
                .build();
        mockMvc.perform(get("/client/{id}", idResponse.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(clientDetailsResponse)));
        mockMvc.perform(get("/client"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.singletonList(clientDetailsResponse))));

        // DELETE
        mockMvc.perform(delete("/client/" + idResponse.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(""));

        // GET
        mockMvc.perform(get("/client"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"));
        mockMvc.perform(get("/client/count"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("0"));
    }

}
