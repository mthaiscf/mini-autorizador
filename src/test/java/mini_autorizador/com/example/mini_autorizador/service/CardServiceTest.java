package mini_autorizador.com.example.mini_autorizador.service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import mini_autorizador.com.example.mini_autorizador.domain.Card;
import mini_autorizador.com.example.mini_autorizador.exception.*;
import mini_autorizador.com.example.mini_autorizador.repository.CardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

public class CardServiceTest {

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private CardService cardService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCardSuccessfully() {
        String numeroCartao = "1234567890123456";
        String senha = "senha123";
        
        when(cardRepository.existsByNumeroCartao(numeroCartao)).thenReturn(false);
        when(cardRepository.save(any(Card.class))).thenReturn(new Card(numeroCartao, senha, BigDecimal.valueOf(500.00)));

        Card card = cardService.createCard(numeroCartao, senha);
        
        assertNotNull(card);
        assertEquals(numeroCartao, card.getNumeroCartao());
        assertEquals(senha, card.getSenha());
    }

    @Test
    public void testAuthorizeTransactionSuccess() throws InsufficientBalanceException, InvalidPasswordException, CardNotFoundException {
        String numeroCartao = "1234567890123456";
        String senha = "senha123";
        Optional<Card> card = Optional.of(new Card(numeroCartao, senha, BigDecimal.valueOf(500.00)));

        when(cardRepository.findByNumeroCartao(numeroCartao)).thenReturn(card);

        cardService.authorizeTransaction(numeroCartao, senha, BigDecimal.valueOf(100.00));
        assertEquals(BigDecimal.valueOf(400.00), card.get().getSaldo());
    }

    @Test
    public void testAuthorizeTransactionInsufficientBalance() {
        String numeroCartao = "1234567890123456";
        String senha = "senha123";
        Optional<Card> card = Optional.of(new Card(numeroCartao, senha, BigDecimal.valueOf(500.00)));

        when(cardRepository.findByNumeroCartao(numeroCartao)).thenReturn(card);

        assertThrows(InsufficientBalanceException.class, () -> {
            cardService.authorizeTransaction(numeroCartao, senha, BigDecimal.valueOf(600.00));
        });
    }
}
