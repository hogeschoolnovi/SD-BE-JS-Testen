package nl.novi.mockitoexample.integration;

import nl.novi.mockitoexample.controller.PersonController;
import nl.novi.mockitoexample.service.PersonService;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
class PersonControllerIntegrationTest {

    // Deze heb je nodig voor de uitdagende opdracht.
    @MockBean
    private PersonService personService;

    @Autowired
    PersonController personController;

    @Autowired
    private MockMvc mockMvc;

    /**
     * Tests whether or not Dependency Injection works
     */
    @Test
    void contextLoads() {
        Assertions.assertNotNull(personController);
    }

    @Test
    void whenRequestToGetAllUsers_thenCorrectResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/user")
        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void whenPostRequestToAddInvalidUser_thenBadRequestResponse() throws Exception {
        // We maken hier de JSON na:
        String user = "{" +
                "    \"username\":\"nick\"," +
                "    \"email\":\"nick@nick.nl\"," +
                "    \"password\":\"\"," +
                "    \"repeatedPassword\":\"nicknick\"" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/user/register")
        .content(user)
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.password", Is.is("Password is mandatory")))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }



}
