package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ComponyControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ComponyController componyController;
    @BeforeEach
    public void setup() {
        componyController.clear();
    }
    @Test
    void should_return_created_compony_when_post_() throws Exception {
        //given
        String requestBody = """
                {
                "id": 1,
                "name": "compony1"
                }
                """;
        MockHttpServletRequestBuilder request = post("/componies").contentType(MediaType.APPLICATION_JSON).content(requestBody);
        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("compony1"));
    }
    @Test
    void should_return_compony_when_get_compony_by_id() throws Exception {
        Compony compony = new Compony(null, "compony1");
        Compony expect = componyController.create(compony);
        MockHttpServletRequestBuilder request = get("/componies/" + expect.id()).contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expect.id()))
                .andExpect(jsonPath("$.name").value(expect.name()));
    }
    @Test
    void  should_return_componies_when_get_list() throws Exception {
        setup();
        componyController.create(new Compony(null, "John Smith"));
        componyController.create(new Compony(null, "Lily"));
        componyController.create(new Compony(null, "Sam"));
        MockHttpServletRequestBuilder request = get("/componies").contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }
    @Test
    void should_return_page_1_size_5_when_get_componies_page_1_size_5() throws Exception {
        setup();
        for (int i = 1; i <= 12; i++) {
            componyController.create(new Compony(null, "Employee" + i));
        }
        MockHttpServletRequestBuilder request = get("/componies?page=1&size=5").contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(5));
    }
}
