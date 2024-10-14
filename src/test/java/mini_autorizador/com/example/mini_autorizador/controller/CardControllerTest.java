package mini_autorizador.com.example.mini_autorizador.controller;

import mini_autorizador.com.example.mini_autorizador.domain.Card;
import mini_autorizador.com.example.mini_autorizador.service.CardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.math.BigDecimal;

@WebMvcTest(CardController.class)
public class CardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CardService cardService;

    @InjectMocks
    private CardController cardController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser(username = "username", password = "password")
    public void testCreateCard() throws Exception {
        String cardJson = "{\"numeroCartao\": \"1234567890123456\", \"senha\": \"senha123\"}";

        when(cardService.createCard(anyString(), anyString()))
            .thenReturn(new Card("1234567890123456", "senha123", BigDecimal.valueOf(500.0)));

        mockMvc.perform(post("/cartoes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(cardJson)
            .with(csrf()))
            .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "username", password = "password")
    public void testGetSaldo() throws Exception {
        when(cardService.getCardBalance("1234567890123456"))
                .thenReturn(BigDecimal.valueOf(500.0));

        mockMvc.perform(get("/cartoes/1234567890123456"))
                .andExpect(status().isOk())
                .andExpect(content().string("500.0"));
    }
}