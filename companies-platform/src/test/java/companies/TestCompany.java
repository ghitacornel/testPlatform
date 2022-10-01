package companies;

import companies.controller.model.CompanyRegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestCompany extends AbstractTestSpringBootContext {

    private static final String ROOT = "/company";

    @Test
    public void testRegisterFindAllUnregisterFindAll() throws Exception {

        CompanyRegisterRequest request = new CompanyRegisterRequest();
        request.setName("name");
        request.setIndustry("industry");
        request.setUrl("url");

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

        // expect 1 model registered
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
            CompanyRegisterRequest request = new CompanyRegisterRequest();
            request.setName(null);
            request.setIndustry("industry");
            request.setUrl("url");
            String content = objectMapper.writeValueAsString(request);
            mvc.perform(post(ROOT).contentType(MediaType.APPLICATION_JSON).content(content))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string("{\"name\":\"must not be blank\"}"));
        }
        {
            CompanyRegisterRequest request = new CompanyRegisterRequest();
            request.setName("name");
            request.setIndustry(null);
            request.setUrl("url");
            String content = objectMapper.writeValueAsString(request);
            mvc.perform(post(ROOT).contentType(MediaType.APPLICATION_JSON).content(content))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string("{\"industry\":\"must not be blank\"}"));
        }
        // not all needed

    }

    @Test
    public void testUnregisterBadData() throws Exception {
        {
            mvc.perform(delete(ROOT + "/{name}", "not existing name"))
                    .andExpect(status().isNotFound())
                    .andExpect(content().string("Company with name not existing name not found"));
        }
        {
            mvc.perform(delete(ROOT + "/{name}", "     "))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string("{\"unregister.name\":\"must not be blank\"}"));
        }
    }
}
