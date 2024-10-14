package mini_autorizador.com.example.mini_autorizador.controller;

import mini_autorizador.com.example.mini_autorizador.exception.CardNotFoundException;
import mini_autorizador.com.example.mini_autorizador.exception.InsufficientBalanceException;
import mini_autorizador.com.example.mini_autorizador.exception.InvalidPasswordException;
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

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CardService cardService;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser(username = "user", password = "password")
    public void testAuthorizeTransactionSuccess() throws Exception {
        String transactionJson = "{\"numeroCartao\": \"1234567890123456\", \"senhaCartao\": \"senha123\", \"valor\": 100.00}";

        mockMvc.perform(post("/transacoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(transactionJson)
                .with(csrf())) // Inclui o token CSRF
                .andExpect(status().isCreated())
                .andExpect(content().string("OK"));
    }

    @Test
    @WithMockUser(username = "user", password = "password")
    public void testAuthorizeTransactionInsufficientBalance() throws Exception {
        String transactionJson = "{\"numeroCartao\": \"1234567890123456\", \"senhaCartao\": \"senha123\", \"valor\": 100.00}";

        doThrow(new InsufficientBalanceException("Saldo insuficiente")).when(cardService).authorizeTransaction(anyString(), anyString(), any(BigDecimal.class));

        mockMvc.perform(post("/transacoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(transactionJson)
                .with(csrf())) // Inclui o token CSRF
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("SALDO_INSUFICIENTE"));
    }

    @Test
    @WithMockUser(username = "user", password = "password")
    public void testAuthorizeTransactionInvalidPassword() throws Exception {
        String transactionJson = "{\"numeroCartao\": \"1234567890123456\", \"senhaCartao\": \"senha123\", \"valor\": 100.00}";

        doThrow(new InvalidPasswordException("Senha e/ou usuário inválido(s).")).when(cardService).authorizeTransaction(anyString(), anyString(), any(BigDecimal.class));

        mockMvc.perform(post("/transacoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(transactionJson)
                .with(csrf())) // Inclui o token CSRF
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("SENHA_INVALIDA"));
    }

    @Test
    @WithMockUser(username = "user", password = "password")
    public void testAuthorizeTransactionCardNotFound() throws Exception {
        String transactionJson = "{\"numeroCartao\": \"1234567890123456\", \"senhaCartao\": \"senha123\", \"valor\": 100.00}";

        doThrow(new CardNotFoundException("Cartão não encontrado.")).when(cardService).authorizeTransaction(anyString(), anyString(), any(BigDecimal.class));

        mockMvc.perform(post("/transacoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(transactionJson)
                .with(csrf())) // Inclui o token CSRF
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("CARTAO_INEXISTENTE"));
    }
}
