package clients;

import clients.controller.model.request.ClientRegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestClient extends AbstractTestSpringBootContext {

    private static final String ROOT = "/client";

    @Test
    public void testRegisterFindAllUnregisterFindAll() throws Exception {

        ClientRegisterRequest request = new ClientRegisterRequest();
        request.setName("name");
        request.setCardType("cardType");

        // expect nothing registered
        {
            mvc.perform(get(ROOT).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(content().json("[]"));
        }

        // try to register
        {
            String content = objectMapper.writeValueAsString(request);
            mvc.perform(post(ROOT).contentType(MediaType.APPLICATION_JSON).content(content))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(request)));
        }

        // expect 1 json registered
        {
            mvc.perform(get(ROOT).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(content().json("[" + objectMapper.writeValueAsString(request) + "]"));
        }

        // try to unregister
        {
            mvc.perform(delete(ROOT + "/{name}", request.getName()))
                    .andExpect(status().isOk())
                    .andExpect(content().string(""));
        }

        // expect nothing registered
        {
            mvc.perform(get(ROOT).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(content().json("[]"));
        }

    }

    @Test
    public void testRegisterBadData() throws Exception {

        {
            ClientRegisterRequest request = new ClientRegisterRequest();
            request.setName(null);
            request.setCardType("cardType");
            String content = objectMapper.writeValueAsString(request);
            mvc.perform(post(ROOT).contentType(MediaType.APPLICATION_JSON).content(content))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string("{\"name\":\"must not be blank\"}"));
        }
        {
            ClientRegisterRequest request = new ClientRegisterRequest();
            request.setName(" ");
            request.setCardType("cardType");
            String content = objectMapper.writeValueAsString(request);
            mvc.perform(post(ROOT).contentType(MediaType.APPLICATION_JSON).content(content))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string("{\"name\":\"must not be blank\"}"));
        }
        // not all needed

    }

    @Test
    public void testUnregisterBadData() throws Exception {
        {
            String content = "xxx";
            mvc.perform(delete(ROOT + "/{name}", content))
                    .andExpect(status().isNotFound())
                    .andExpect(content().string("Client with user name xxx not found"));
        }
        {
            String content = "  ";
            mvc.perform(delete(ROOT + "/{name}", content))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string("{\"unregister.name\":\"must not be blank\"}"));
        }
    }
}
