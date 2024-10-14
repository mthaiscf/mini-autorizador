package mini_autorizador.com.example.mini_autorizador.domain;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;


public class CardTest {

    @Test
    public void testCreateCardWithInitialBalance() {
        Card card = new Card("1234567890123456", "senha123", BigDecimal.valueOf(500.00));
        assertEquals("1234567890123456", card.getNumeroCartao());
        assertEquals("senha123", card.getSenha());
        assertEquals(BigDecimal.valueOf(500.00), card.getSaldo());
    }

    @Test
    public void testDebitCardBalance() {
        Card card = new Card("1234567890123456", "senha123", BigDecimal.valueOf(500.00));
        card.debitarSaldo(BigDecimal.valueOf(100.00));
        assertEquals(BigDecimal.valueOf(400.00), card.getSaldo());
    }
}