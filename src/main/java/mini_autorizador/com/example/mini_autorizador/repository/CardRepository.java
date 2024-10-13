package mini_autorizador.com.example.mini_autorizador.repository;

import mini_autorizador.com.example.mini_autorizador.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, String> {
    Optional<Card> findByNumeroCartao(String numeroCartao);
    boolean existsByNumeroCartao(String numeroCartao);
}