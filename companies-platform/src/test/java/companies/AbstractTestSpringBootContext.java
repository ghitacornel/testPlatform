package companies;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class AbstractTestSpringBootContext {

    @Autowired
    protected MockMvc mvc;

    final protected Faker faker = Faker.instance();

    final protected ObjectMapper objectMapper = new ObjectMapper();

}
