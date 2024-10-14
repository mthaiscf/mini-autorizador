package mini_autorizador.com.example.mini_autorizador.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionTest {

    @Test
    public void testTransactionConstructorAndGetters() {
        // Dados de teste
        String numeroCartao = "1234567890123456";
        String senhaCartao = "senha123";
        BigDecimal valor = BigDecimal.valueOf(500.00);

        // Criando uma instância de Transaction com o construtor parametrizado
        Transaction transaction = new Transaction(numeroCartao, senhaCartao, valor);

        // Verificações
        assertEquals(numeroCartao, transaction.getNumeroCartao());
        assertEquals(senhaCartao, transaction.getSenhaCartao());
        assertEquals(valor, transaction.getValor());
    }

    @Test
    public void testTransactionSetters() {
        // Criando uma instância de Transaction com o construtor padrão
        Transaction transaction = new Transaction();

        // Dados de teste
        String numeroCartao = "1234567890123456";
        String senhaCartao = "senha123";
        BigDecimal valor = BigDecimal.valueOf(500.00);

        // Usando setters para modificar os atributos
        transaction.setNumeroCartao(numeroCartao);
        transaction.setSenhaCartao(senhaCartao);
        transaction.setValor(valor);

        // Verificações
        assertEquals(numeroCartao, transaction.getNumeroCartao());
        assertEquals(senhaCartao, transaction.getSenhaCartao());
        assertEquals(valor, transaction.getValor());
    }
}