package mini_autorizador.com.example.mini_autorizador.repository;

import mini_autorizador.com.example.mini_autorizador.domain.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

@DataJpaTest
public class CardRepositoryTest {

    @Autowired
    private CardRepository cardRepository;

    @BeforeEach
    public void setUp() {
        // Limpa o repositório antes de cada teste (opcional)
        cardRepository.deleteAll();
    }

    @Test
    public void testFindByNumeroCartao_ShouldReturnCard_WhenCardExists() {
        // Arrange
        Card card = new Card();
        card.setNumeroCartao("1234567890123456"); // Defina um número de cartão válido
        cardRepository.save(card);

        // Act
        Optional<Card> foundCard = cardRepository.findByNumeroCartao("1234567890123456");

        // Assert
        assertThat(foundCard).isPresent(); // Verifica se o cartão foi encontrado
        assertThat(foundCard.get().getNumeroCartao()).isEqualTo("1234567890123456");
    }

    @Test
    public void testFindByNumeroCartao_ShouldReturnEmpty_WhenCardDoesNotExist() {
        // Act
        Optional<Card> foundCard = cardRepository.findByNumeroCartao("0000000000000000");

        // Assert
        assertThat(foundCard).isNotPresent(); // Verifica que o cartão não foi encontrado
    }

    @Test
    public void testExistsByNumeroCartao_ShouldReturnTrue_WhenCardExists() {
        // Arrange
        Card card = new Card();
        card.setNumeroCartao("1234567890123456");
        cardRepository.save(card);

        // Act
        boolean exists = cardRepository.existsByNumeroCartao("1234567890123456");

        // Assert
        assertThat(exists).isTrue(); // Verifica que o cartão existe
    }

    @Test
    public void testExistsByNumeroCartao_ShouldReturnFalse_WhenCardDoesNotExist() {
        // Act
        boolean exists = cardRepository.existsByNumeroCartao("0000000000000000");

        // Assert
        assertThat(exists).isFalse(); // Verifica que o cartão não existe
    }
}