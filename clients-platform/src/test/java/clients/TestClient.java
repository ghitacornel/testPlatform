package clients;

import clients.controllers.models.ClientRegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestClient extends AbstractTestSpringBootContext {

    private static final String ROOT = "/client";

    @Test
    public void testRegisterFindAllUnregisterFindAll() throws Exception {

        ClientRegisterRequest request = new ClientRegisterRequest();
        request.setName(faker.name().username());
        request.setCardType(faker.business().creditCardType());

        // expect nothing registered
        {
            mvc.perform(get(ROOT + "/all")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(content().json("[]"));
        }

        // try to register
        {
            String content = objectMapper.writeValueAsString(request);
            mvc.perform(post(ROOT + "/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(content))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(request)));
        }

        // expect 1 json registered
        {
            mvc.perform(get(ROOT + "/all")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(content().json("[" + objectMapper.writeValueAsString(request) + "]"));
        }

        // try to unregister
        {
            mvc.perform(post(ROOT + "/unregister")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(request.getName()))
                    .andExpect(status().isOk())
                    .andExpect(content().string(""));
        }

        // expect nothing registered
        {
            mvc.perform(get(ROOT + "/all")
                            .contentType(MediaType.APPLICATION_JSON))
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
            request.setCardType(faker.business().creditCardType());
            String content = objectMapper.writeValueAsString(request);
            mvc.perform(post(ROOT + "/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(content))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string("{\"name\":\"must not be blank\"}"));
        }
        {
            ClientRegisterRequest request = new ClientRegisterRequest();
            request.setName(" ");
            request.setCardType(faker.business().creditCardType());
            String content = objectMapper.writeValueAsString(request);
            mvc.perform(post(ROOT + "/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(content))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string("{\"name\":\"must not be blank\"}"));
        }
        // not all needed

    }

    @Test
    public void testUnregisterBadData() throws Exception {
        {
            String content = "xxx";
            mvc.perform(post(ROOT + "/unregister")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(content))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string("user name not found"));
        }
        {
            String content = "  ";
            mvc.perform(post(ROOT + "/unregister")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(content))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string("{\"unregister.name\":\"must not be blank\"}"));
        }
    }
}
